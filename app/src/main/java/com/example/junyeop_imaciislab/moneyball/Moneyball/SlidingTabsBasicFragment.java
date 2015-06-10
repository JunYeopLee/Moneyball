/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.moneyball.R;
import com.example.junyeop_imaciislab.moneyball.common.adapter.PredictionAdapter;
import com.example.junyeop_imaciislab.moneyball.common.adapter.SettingsListAdapter;
import com.example.junyeop_imaciislab.moneyball.common.view.MatchupPrediction;
import com.example.junyeop_imaciislab.moneyball.common.view.SlidingTabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A basic sample which shows how to use {@link com.example.junyeop_imaciislab.moneyball.common.view.SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */

    private SharedPreferences sharedPreferences;
    /**
    *  SharedPreferences for auto login
    *
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab_title, R.id.tabtext, R.id.tabimage);

        mSlidingTabLayout.setSelectedIndicatorColors(Color.BLACK);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)


        sharedPreferences = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", null);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String strCurDate = "Today " + CurDateFormat.format(date) + "\tUser : " + username;
        TextView textView = (TextView)view.findViewById(R.id.today);
        textView.setText(strCurDate);
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 4;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return "ITEMM " + (position + 1);
        }
        // END_INCLUDE (pageradapter_getpagetitle)


        public int getImageId(int position) {
            switch(position) {
                case 0:
                    return R.drawable.lightball;
                case 1:
                    return R.drawable.calulator;
                case 2:
                    return R.drawable.coins;
                default:
                    return R.drawable.setting;
            }
        }
        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
            switch(position) {
                case 0:
                    // Inflate a new layout from our resources
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_score_prediction, container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);


                    ListView listPrediction;
                    ArrayList<MatchupPrediction> matchupPrediction = new ArrayList<MatchupPrediction>();
                    MatchupPrediction tmpPrediction = new MatchupPrediction();
                    tmpPrediction.setStadium("Dodger Stadium");
                    tmpPrediction.setTime("19:05");
                    tmpPrediction.setTeam1("Samsung");
                    tmpPrediction.setTeam2("Lotte");
                    String [] tmpResults = {"10 : 3", "7 : 2", "1 : 3" ,"5 : 7" , "10 : 11"};
                    tmpPrediction.setResults(tmpResults);
                    String [] tmpProbs = {"10%", "15%", "20%" ,"25%" ,"30%"};
                    tmpPrediction.setProb(tmpProbs);
                    matchupPrediction.add(tmpPrediction);

                    tmpPrediction = new MatchupPrediction();
                    tmpPrediction.setStadium("Dodger Stadium");
                    tmpPrediction.setTime("19:05");
                    tmpPrediction.setTeam1("Nexen");
                    tmpPrediction.setTeam2("KIA");
                    tmpPrediction.setResults(tmpResults);
                    tmpPrediction.setProb(tmpProbs);
                    matchupPrediction.add(tmpPrediction);

                    tmpPrediction = new MatchupPrediction();
                    tmpPrediction.setStadium("Dodger Stadium");
                    tmpPrediction.setTime("19:05");
                    tmpPrediction.setResults(tmpResults);
                    tmpPrediction.setProb(tmpProbs);
                    tmpPrediction.setTeam1("Hanwha");
                    tmpPrediction.setTeam2("NC");
                    matchupPrediction.add(tmpPrediction);

                    tmpPrediction = new MatchupPrediction();
                    tmpPrediction.setStadium("Dodger Stadium");
                    tmpPrediction.setTime("19:05");
                    tmpPrediction.setResults(tmpResults);
                    tmpPrediction.setProb(tmpProbs);
                    tmpPrediction.setTeam1("LG");
                    tmpPrediction.setTeam2("SK");
                    matchupPrediction.add(tmpPrediction);

                    tmpPrediction = new MatchupPrediction();
                    tmpPrediction.setStadium("Dodger Stadium");
                    tmpPrediction.setTime("19:05");
                    tmpPrediction.setResults(tmpResults);
                    tmpPrediction.setProb(tmpProbs);
                    tmpPrediction.setTeam1("Doosan");
                    tmpPrediction.setTeam2("KT");
                    matchupPrediction.add(tmpPrediction);

                    for( int i = 0 ; i < matchupPrediction.size() ; i++ )
                        Log.d("matchup lllog",matchupPrediction.get(i).getTeam1());

                    listPrediction = (ListView)view.findViewById(R.id.prediction_list);
                    PredictionAdapter predictionAdapter = new PredictionAdapter(getActivity(),matchupPrediction);
                    listPrediction.setAdapter(predictionAdapter);

                    Log.i("init", "instantiateItem() [position: " + position + "]");
                    return view;

                case 1:
                    // Inflate a new layout from our resources
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_betting_calculator, container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);

                    // Retrieve a TextView from the inflated View, and update it's text
                    //TextView title = (TextView) view.findViewById(R.id.item_title);
                    //title.setText(String.valueOf(position + 1));

                    //Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");
                    // Return the View

                    Log.i("init", "instantiateItem() [position: " + position + "]");
                    return view;
                case 2:
                    // Inflate a new layout from our resources
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_betting_moneyball, container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);

                    // Retrieve a TextView from the inflated View, and update it's text
                    //TextView title = (TextView) view.findViewById(R.id.item_title);
                    //title.setText(String.valueOf(position + 1));

                    //Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

                    Log.i("init", "instantiateItem() [position: " + position + "]");
                    // Return the View
                    return view;

                default:
                    // Inflate a new layout from our resources
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_settings, container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);

                    ListView listSettings;
                    ArrayList<String> settingsList = new ArrayList<String>();

                    settingsList.add("Settings1");
                    settingsList.add("Settings2");
                    settingsList.add("Settings3");
                    settingsList.add("Settings4");
                    settingsList.add("Logout");

                    listSettings = (ListView)view.findViewById(R.id.settings_list);
                    SettingsListAdapter settingsListAdapter = new SettingsListAdapter(getActivity(),settingsList);
                    listSettings.setAdapter(settingsListAdapter);
                    // Retrieve a TextView from the inflated View, and update it's text
                    //TextView title = (TextView) view.findViewById(R.id.item_title);
                    //title.setText(String.valueOf(position + 1));

                    //Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");
                    // Return the View

                    Log.i("init", "instantiateItem() [position: " + position + "]");
                    return view;
            }

        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            //Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

    }
}
