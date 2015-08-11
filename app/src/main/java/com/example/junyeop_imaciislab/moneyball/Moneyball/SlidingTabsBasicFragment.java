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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junyeop_imaciislab.moneyball.R;
import com.example.junyeop_imaciislab.moneyball.common.adapter.BettingAdapter;
import com.example.junyeop_imaciislab.moneyball.common.adapter.CalculatorAdapter;
import com.example.junyeop_imaciislab.moneyball.common.adapter.PredictionAdapter;
import com.example.junyeop_imaciislab.moneyball.common.adapter.SettingsListAdapter;
import com.example.junyeop_imaciislab.moneyball.common.view.CalculatorItemWrapper;
import com.example.junyeop_imaciislab.moneyball.common.view.MatchupPrediction;
import com.example.junyeop_imaciislab.moneyball.common.view.SlidingTabLayout;
import com.facebook.login.LoginManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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

        mSlidingTabLayout.setSelectedIndicatorColors(Color.TRANSPARENT);
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

        Button BuyMoneyball = (Button)view.findViewById(R.id.buy_moneyball_btn);
        BuyMoneyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BuyingMoneyballActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });
        TextView moneyballNow = (TextView)view.findViewById(R.id.moneyball_now);
        moneyballNow.setText(moneyToString(sharedPreferences.getInt("money", 0)));
    }

    @Override
    public void onDestroy() {
        ((SamplePagerAdapter)mViewPager.getAdapter()).getMatchupPrediction().clear();
        super.onDestroy();
    }


    private String moneyToString(int money) {
        StringBuilder sb = new StringBuilder(String.valueOf(money));
        final int limit = 99999;
        if(money>limit) {
            sb = new StringBuilder("99,999+");
        } else {
            for( int index = sb.length()-3 ; index > 0 ; index-=3 ) {
                sb.insert(index,",");
            }
        }
        return sb.toString();
    }


    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {
        ArrayList<MatchupPrediction> matchupPrediction = new ArrayList<MatchupPrediction>();

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

        public void setMatchupPrediction(ArrayList<MatchupPrediction> matchupPrediction) {
            this.matchupPrediction = matchupPrediction;
        }

        public ArrayList<MatchupPrediction> getMatchupPrediction() {
            return matchupPrediction;
        }

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
            ArrayList<MatchupPrediction> calculatorItem;
            switch(position) {
                case 0:
                    // Inflate a new layout from our resources
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_score_prediction, container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);
                    ListView listPrediction;
                    MatchupPrediction tmpPrediction = new MatchupPrediction();
                    if(matchupPrediction.size()<5) {
                        SharedPreferences sharedPreferences;
                        sharedPreferences = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
                        int userNum = sharedPreferences.getInt("userNum", -1);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyyMMdd");

                        String Today = CurDateFormat.format(date);
                        Today = "20150731"; /// FOR TEST CODE
                        String query;
                        query = getString(R.string.prediction_match_query) + "userNum=" + String.valueOf(userNum) + "&today=" + Today;
                        try {
                            HttpResponse response = new GetPredictionListTask().execute(query).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    listPrediction = (ListView) view.findViewById(R.id.prediction_list);
                    PredictionAdapter predictionAdapter = new PredictionAdapter(getActivity(), matchupPrediction);
                    listPrediction.setAdapter(predictionAdapter);
                    Log.i("init", "instantiateItem() [position: " + position + "]");
                    return view;

                case 1:
                    // Inflate a new layout from our resources
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_betting_calculator, container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);

                    CalculatorItemWrapper calculatorItemWrapper = new CalculatorItemWrapper();
                    calculatorItem = calculatorItemWrapper.getCalculatorItem();
                    calculatorItemWrapper.setCalculatorItem(calculatorItem);
                    ListView calculatorList;
                    calculatorList = (ListView)view.findViewById(R.id.calculator_list);
                    CalculatorAdapter calculatorAdapter = new CalculatorAdapter(getActivity(),calculatorItem);
                    calculatorList.setAdapter(calculatorAdapter);

                    final EditText bettingMoneyEdit = (EditText)view.findViewById(R.id.edit_money);
                    bettingMoneyEdit.addTextChangedListener(new TextWatcher() {
                        String strMoney = "";
                        DecimalFormat df = new DecimalFormat("###,###.####");

                        @Override
                        public void afterTextChanged(Editable s) {
                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.toString().length() > 10) {
                                Toast.makeText(getActivity(), "Over Limit", Toast.LENGTH_SHORT);
                                bettingMoneyEdit.setText(strMoney);    // 결과 텍스트 셋팅.
                                bettingMoneyEdit.setSelection(strMoney.length());     // 커서를 제일 끝으로 보냄.
                                return;
                            }
                            if (!s.toString().equals(strMoney)) {     // StackOverflow를 막기위해,
                                if (s.toString().length() != 0)
                                    strMoney = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, string에 저장.
                                else
                                    strMoney = "";
                                bettingMoneyEdit.setText(strMoney);    // 결과 텍스트 셋팅.
                                bettingMoneyEdit.setSelection(strMoney.length());     // 커서를 제일 끝으로 보냄.
                            }
                        }
                    });

                    String[] spinnerOption = new String[3];
                    spinnerOption[0] = "최대 확률";
                    spinnerOption[1] = "최대 수익";
                    spinnerOption[2] = "사용자 지정";
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_item,spinnerOption);
                    Spinner spinner = (Spinner)view.findViewById(R.id.betting_spinner);
                    spinner.setAdapter(spinnerAdapter);

                    Log.i("init", "instantiateItem() [position: " + position + "]");
                    return view;
                case 2:
                    // Inflate a new layout from our resources
                    view = getActivity().getLayoutInflater().inflate(R.layout.activity_betting_moneyball, container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);
                    ListView bettingList;
                    MatchupPrediction tmpPrediction2 = new MatchupPrediction();
                    if(matchupPrediction.size()<5) {
                        SharedPreferences sharedPreferences;
                        sharedPreferences = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
                        int userNum = sharedPreferences.getInt("userNum", -1);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyyMMdd");

                        String Today = CurDateFormat.format(date);
                        Today = "20150731"; /// FOR TEST CODE
                        String query;
                        query = getString(R.string.prediction_match_query) + "userNum=" + String.valueOf(userNum) + "&today=" + Today;
                        try {
                            HttpResponse response = new GetPredictionListTask().execute(query).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    bettingList = (ListView)view.findViewById(R.id.betting_list);
                    BettingAdapter bettingAdapter = new BettingAdapter(getActivity(),matchupPrediction);
                    bettingList.setAdapter(bettingAdapter);
                    // Retrieve a TextView from the inflated View, and update it's text
                    //TextView title = (TextView) view.findViewById(R.id.item_title);
                    //title.setText(String.valueOf(position + 1));

                    //Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

                    Log.i("init", "instantiateItem() [position: " + position + "]");
                    // Return the View
                    return view;

                case 3:
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

                    Button button = (Button)view.findViewById(R.id.logout_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sharedPreferences = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("username");
                            editor.remove("password");
                            editor.remove("userNum");
                            if(sharedPreferences.getBoolean("isfacebook",false)) {
                                LoginManager.getInstance().logOut();
                                editor.remove("isfacebook");
                            } else if(sharedPreferences.getBoolean("isgoogle",false)){
                                editor.remove("isgoogle");
                            } else if(sharedPreferences.getBoolean("istwitter",false)){
                                editor.remove("istwitter");
                            }
                            editor.commit();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            getActivity().startActivity(intent);
                            ((Activity)getActivity()).finish();
                        }
                    });
                    Log.i("instantiateItem", "instantiateItem() [position: " + position + "]");
                    return view;
                default:
                    return null;
            }

        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i("instantiateItem", "destroyItem() [position: " + position + "]");
        }

    }

    private class GetPredictionListTask extends AsyncTask<String, Void, HttpResponse> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected HttpResponse doInBackground(String... urls) {
            HttpResponse response = null;
            HttpClient client = getHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
            HttpGet httpGet = new HttpGet(urls[0]);
            try {
                response = client.execute(httpGet);
                try{
                    StatusLine statusLine = response.getStatusLine();
                    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        response.getEntity().writeTo(out);
                        String responseString = out.toString();
                        out.close();
                        JSONTokener tokener = new JSONTokener(responseString);
                        JSONObject finalResult = (JSONObject)tokener.nextValue();
                        if(finalResult.getBoolean("success")==true) {
                            ArrayList<MatchupPrediction> tmp = new ArrayList<>();
                            JSONObject dataObject = (JSONObject)finalResult.get("data");
                            JSONArray matchVOList = (JSONArray)dataObject.get("matchVOList");
                            JSONObject matchObject;
                            MatchupPrediction matchupPrediction;
                            for( int i = 0 ; i  < matchVOList.length() ; i++ ) {
                                matchObject = (JSONObject)matchVOList.get(i);
                                matchupPrediction = new MatchupPrediction();
                                matchupPrediction.setMatchNum(matchObject.getInt("matchId"));
                                matchupPrediction.setStadium(matchObject.getString("stadiumName"));
                                matchupPrediction.setTime(matchObject.getString("stadiumTime"));
                                matchupPrediction.setTeam1(matchObject.getString("team1Name"));
                                matchupPrediction.setTeam2(matchObject.getString("team2Name"));
                                String[] tmpResults = new String[5];
                                String[] tmpProb = new String[5];
                                String name;
                                for( int j = 1 ; j <= 5 ; j++ ) {
                                    name = "predictScore" + String.valueOf(j);
                                    tmpResults[j-1] = matchObject.getString(name);
                                    name = "predictPercent" + String.valueOf(j);
                                    tmpProb[j-1] = matchObject.getString(name) + "%";
                                }
                                matchupPrediction.setResults(tmpResults);
                                matchupPrediction.setProb(tmpProb);
                                matchupPrediction.setRate1(matchObject.getString("rateTeam1"));
                                matchupPrediction.setRate2(matchObject.getString("rateTeam2"));
                                tmp.add(matchupPrediction);
                            }
                            ((SamplePagerAdapter)mViewPager.getAdapter()).setMatchupPrediction(tmp);
                        } else {
                            // CONNECTION FAIL
                            Toast.makeText(getActivity(),"CONNECTION FAILED",Toast.LENGTH_SHORT);
                            Log.d("pred connect failed",finalResult.getString("errorMessage"));
                        }

                    } else{
                        //Closes the connection.
                        response.getEntity().getContent().close();
                        throw new IOException(statusLine.getReasonPhrase());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            catch(ClientProtocolException e){
                e.printStackTrace();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            // Progreebar hide
        }

        /**
         *
         * For HTTPS protocol
         *
         * */
        private HttpClient getHttpClient() {
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);

                SSLSocketFactory sf = new SFSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

                return new DefaultHttpClient(ccm, params);
            } catch (Exception e) {
                return new DefaultHttpClient();
            }
        }
    }
}
