package com.example.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
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
import android.os.Handler;

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
        final TextView tvResult1 = (TextView) rowView.findViewById(R.id.cal_tv_1result);
        final TextView tvResult2 = (TextView) rowView.findViewById(R.id.cal_tv_2result);
        Button btnResult3 = (Button) rowView.findViewById(R.id.cal_btn_3result);
        Button btnResult4 = (Button) rowView.findViewById(R.id.cal_btn_4result);
        Button btnResult5 = (Button) rowView.findViewById(R.id.cal_btn_5result);
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

        tvResult1.setText(tempObj.getResults()[0]);
        tvResult2.setText(tempObj.getResults()[1]);
        //// LOCK

        btnProb1.setText(tempObj.getProb()[0]);
        btnProb2.setText(tempObj.getProb()[1]);
        btnProb3.setText(tempObj.getProb()[2]);
        btnProb4.setText(tempObj.getProb()[3]);
        btnProb5.setText(tempObj.getProb()[4]);

        final AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setMessage(Html.fromHtml("<strong><font color=\"#ff0000\"> " + "Html 표현여부 "
                + "</font></strong><br>HTML 이 제대로 표현되는지 본다."));

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

        if(isSelected.get(1).booleanValue() == true ) {
            tvResult1.setBackgroundColor(Color.BLUE);
            tvResult1.setTextColor(Color.WHITE);
            tvResult1.invalidate();
        }
        tvResult1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected.get(1).booleanValue() == false) {
                    tvResult1.setBackgroundColor(Color.BLUE);
                    tvResult1.setTextColor(Color.WHITE);
                    tvResult1.invalidate();
                    if(isSelected.get(2)==true) {
                        isSelected.set(2, false);
                        tvResult2.setBackgroundColor(Color.parseColor("#DCDCDC")); tvResult2.setTextColor(Color.BLACK); tvResult2.invalidate();
                    } else if (isSelected.get(3)==true) {
                        isSelected.set(2,false);
                    } else if (isSelected.get(4)==true) {
                        isSelected.set(2,false);
                    } else if (isSelected.get(5)==true) {
                        isSelected.set(2,false);
                    }
                    isSelected.set(1, true);
                    tempObj.setIsSelected(isSelected);
                } else {
                    tvResult1.setBackgroundColor(Color.parseColor("#DCDCDC"));
                    tvResult1.setTextColor(Color.BLACK);
                    tvResult1.invalidate();
                    isSelected.set(1, false);
                    tempObj.setIsSelected(isSelected);
                }
            }
        });

        if(isSelected.get(2).booleanValue() == true ) {
            tvResult2.setBackgroundColor(Color.BLUE);
            tvResult2.setTextColor(Color.WHITE);
            tvResult2.invalidate();
        }
        tvResult2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected.get(2).booleanValue() == false) {
                    tvResult2.setBackgroundColor(Color.BLUE);
                    tvResult2.setTextColor(Color.WHITE);
                    tvResult2.invalidate();

                    if(isSelected.get(1)==true) {
                        isSelected.set(1,false);
                        tvResult1.setBackgroundColor(Color.parseColor("#DCDCDC")); tvResult1.setTextColor(Color.BLACK); tvResult1.invalidate();
                    } else if (isSelected.get(3)==true) {

                    } else if (isSelected.get(4)==true) {

                    } else if (isSelected.get(5)==true) {

                    }
                    isSelected.set(2,true);
                    tempObj.setIsSelected(isSelected);
                } else {
                    tvResult2.setBackgroundColor(Color.parseColor("#DCDCDC"));
                    tvResult2.setTextColor(Color.BLACK);
                    tvResult2.invalidate();
                    isSelected.set(2,false);
                    tempObj.setIsSelected(isSelected);
                }
            }
        });

        btnResult3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.show();
            }
        });

        btnResult4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.show();
            }
        });

        btnResult5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.show();
            }
        });

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
                /*
                if(listview.getChildAt(index2)!=null) {
                    listview.getChildAt(index2).findViewById(R.id.btn_wish_plus).setBackground(getContext().getResources().getDrawable(R.drawable.wish_plus));
                }
                */
                //listview.notifyAll();
                Toast.makeText(getContext(), "Match is removed from Calculator", Toast.LENGTH_SHORT).show();
            }
        });
        return rowView;
    }
}