package com.example.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junyeop_imaciislab.moneyball.R;
import com.example.junyeop_imaciislab.moneyball.common.view.CalculatorItemWrapper;
import com.example.junyeop_imaciislab.moneyball.common.view.MatchupPrediction;

import java.util.ArrayList;

/**
 * Created by junyeop_imaciislab on 2015. 6. 11..
 */
public class CalculatorAdapter extends ArrayAdapter<MatchupPrediction> {
    private final Activity context;
    private ArrayList<MatchupPrediction> matchupPredictionsLists;

    public CalculatorAdapter(Activity context, ArrayList<MatchupPrediction> matchupPredictionsLists) {
        super(context, R.layout.score_prediction_item, matchupPredictionsLists);
        this.context = context;
        this.matchupPredictionsLists = matchupPredictionsLists;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.calculator_item, null, true);

        TextView tvStadium = (TextView) rowView.findViewById(R.id.cal_tv_stadium);
        TextView tvTime = (TextView) rowView.findViewById(R.id.cal_tv_match_time);
        ImageView imgTeam1 = (ImageView) rowView.findViewById(R.id.cal_team1_logo);
        ImageView imgTeam2 = (ImageView) rowView.findViewById(R.id.cal_team2_logo);
        final ArrayList<View> btnResults = new ArrayList<>();
        Button btnProb1 = (Button) rowView.findViewById(R.id.cal_btn_1prob);
        Button btnProb2 = (Button) rowView.findViewById(R.id.cal_btn_2prob);
        Button btnProb3 = (Button) rowView.findViewById(R.id.cal_btn_3prob);
        Button btnProb4 = (Button) rowView.findViewById(R.id.cal_btn_4prob);
        Button btnProb5 = (Button) rowView.findViewById(R.id.cal_btn_5prob);
        Button DelBt = (Button) rowView.findViewById(R.id.cal_btn_wish_delete);

        final MatchupPrediction tempObj = matchupPredictionsLists.get(position);
        tvStadium.setText(tempObj.getStadium());
        tvTime.setText(tempObj.getTime());
        final ArrayList<Boolean> isSelected = tempObj.getIsSelected();

        btnResults.add(rowView.findViewById(R.id.cal_tv_1result));
        btnResults.add(rowView.findViewById(R.id.cal_tv_1result));
        btnResults.add(rowView.findViewById(R.id.cal_tv_2result));
        btnResults.add(rowView.findViewById(R.id.cal_btn_3result));
        btnResults.add(rowView.findViewById(R.id.cal_btn_4result));
        btnResults.add(rowView.findViewById(R.id.cal_btn_5result));

        switch (tempObj.getTeam1()) {
            case "Samsung":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "Lotte":
                imgTeam1.setImageResource(R.drawable.lotte_logo);
                break;
            case "NC":
                imgTeam1.setImageResource(R.drawable.nc_logo);
                break;
            case "KIA":
                imgTeam1.setImageResource(R.drawable.kia_logo);
                break;
            case "Hanwha":
                imgTeam1.setImageResource(R.drawable.hanwha_logo);
                break;
            case "LG":
                imgTeam1.setImageResource(R.drawable.lg_logo);
                break;
            case "Doosan":
                imgTeam1.setImageResource(R.drawable.doosan_logo);
                break;
            case "Nexen":
                imgTeam1.setImageResource(R.drawable.nexen_logo);
                break;
            case "SK":
                imgTeam1.setImageResource(R.drawable.sk_logo);
                break;
            case "KT":
                imgTeam1.setImageResource(R.drawable.kt_logo);
                break;
        }
        switch (tempObj.getTeam2()) {
            case "Samsung":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "Lotte":
                imgTeam2.setImageResource(R.drawable.lotte_logo);
                break;
            case "NC":
                imgTeam2.setImageResource(R.drawable.nc_logo);
                break;
            case "KIA":
                imgTeam2.setImageResource(R.drawable.kia_logo);
                break;
            case "Hanwha":
                imgTeam2.setImageResource(R.drawable.hanwha_logo);
                break;
            case "LG":
                imgTeam2.setImageResource(R.drawable.lg_logo);
                break;
            case "Doosan":
                imgTeam2.setImageResource(R.drawable.doosan_logo);
                break;
            case "Nexen":
                imgTeam2.setImageResource(R.drawable.nexen_logo);
                break;
            case "SK":
                imgTeam2.setImageResource(R.drawable.sk_logo);
                break;
            case "KT":
                imgTeam2.setImageResource(R.drawable.kt_logo);
                break;
        }

        btnProb1.setText(tempObj.getProb()[0]);
        btnProb2.setText(tempObj.getProb()[1]);
        btnProb3.setText(tempObj.getProb()[2]);
        btnProb4.setText(tempObj.getProb()[3]);
        btnProb5.setText(tempObj.getProb()[4]);

        final AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setMessage(Html.fromHtml("Moneyball 500원이 차감됩니다.<br/> 구매하시겠습니까?"));

        ab.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Unlock Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        ab.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Unlock Completed", Toast.LENGTH_SHORT).show();
            }
        });


        for( int i = 1 ; i <= 5 ; i++ ) {
            final TextView tmpTextView = ((TextView)btnResults.get(i));
            final int index = i;
            if(isSelected.get(i).booleanValue() == true ) {
                tmpTextView.setBackgroundColor(Color.BLUE);
                tmpTextView.setTextColor(Color.WHITE);
                tmpTextView.invalidate();
            }
            if(tempObj.getResults()[i-1].compareTo("0")==0) { // Button
                tmpTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ab.show();
                    }
                });
            } else { // TextView
                tmpTextView.setText(tempObj.getResults()[i-1]);
                tmpTextView.setBackgroundColor(Color.parseColor("#DCDCDC"));
                tmpTextView.setTextColor(Color.BLACK);
                tmpTextView.setTypeface(Typeface.DEFAULT_BOLD);
                tmpTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                tmpTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isSelected.get(index).booleanValue() == false) {
                            tmpTextView.setBackgroundColor(Color.BLUE);
                            tmpTextView.setTextColor(Color.WHITE);
                            tmpTextView.invalidate();
                            for (int j = 1; j <= 5; j++) {
                                if (isSelected.get(j) == true) {
                                    isSelected.set(j, false);
                                    ((TextView) btnResults.get(j)).setBackgroundColor(Color.parseColor("#DCDCDC"));
                                    ((TextView) btnResults.get(j)).setTextColor(Color.BLACK);
                                    ((TextView) btnResults.get(j)).invalidate();
                                    break;
                                }
                            }
                            isSelected.set(index, true);
                            tempObj.setIsSelected(isSelected);
                        } else {
                            tmpTextView.setBackgroundColor(Color.parseColor("#DCDCDC"));
                            tmpTextView.setTextColor(Color.BLACK);
                            tmpTextView.invalidate();
                            isSelected.set(index, false);
                            tempObj.setIsSelected(isSelected);
                        }
                    }
                });
            }
        }

        DelBt.setOnClickListener(new View.OnClickListener() {
            boolean isclicked = false;
            @Override
            public void onClick(View v) {
                ////
                if(isclicked) return;
                isclicked = true;
                final CalculatorItemWrapper calculatorItemWrapper = new CalculatorItemWrapper();
                final ArrayList<MatchupPrediction> calculatorItemArrayList;
                calculatorItemArrayList = calculatorItemWrapper.getCalculatorItem();
                //final int index = calculatorItemArrayList.indexOf(matchupPredictionsLists.get(position));
                final ListView calculatorList;
                calculatorList = (ListView)((Activity)getContext()).findViewById(R.id.calculator_list);
                int index = calculatorList.indexOfChild(rowView);
                Animation anim = AnimationUtils.loadAnimation(getContext(),  android.R.anim.slide_out_right);
                anim.setDuration(300);
                calculatorList.getChildAt(index).startAnimation(anim);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        calculatorItemArrayList.remove(matchupPredictionsLists.get(position));
                        calculatorItemWrapper.setCalculatorItem(calculatorItemArrayList);
                        CalculatorAdapter calculatorAdapter = new CalculatorAdapter((Activity) getContext(), calculatorItemArrayList);
                        calculatorAdapter.notifyDataSetChanged();
                        calculatorList.setAdapter(calculatorAdapter);
                    }

                }, anim.getDuration());

                ListView listview = (ListView)((Activity)getContext()).findViewById(R.id.prediction_list);
                int index2 = ((PredictionAdapter)listview.getAdapter()).getMatchupPredictionsLists().indexOf(matchupPredictionsLists.get(position));
                for( int i = 0 ; i < listview.getChildCount() ; i++ ) {
                    if( ((TextView)listview.getChildAt(i).findViewById(R.id.item_index)).getText().toString() == String.valueOf(index2) ) {
                        listview.getChildAt(i).findViewById(R.id.btn_wish_plus).setBackground(getContext().getResources().getDrawable(R.drawable.wish_plus));
                        break;
                    }
                }
                Toast.makeText(getContext(), "Match is removed from Calculator", Toast.LENGTH_SHORT).show();
            }
        });
        return rowView;
    }
}