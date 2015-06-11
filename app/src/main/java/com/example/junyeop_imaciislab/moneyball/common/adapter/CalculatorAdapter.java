package com.example.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junyeop_imaciislab.moneyball.R;
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
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.calculator_item, null, true);

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
        final ArrayList<Boolean> isSelected = new ArrayList<Boolean>();
        for( int i = 0 ; i < 6 ; i++ ) isSelected.add(false);

        MatchupPrediction tempObj = matchupPredictionsLists.get(position);
        tvStadium.setText(tempObj.getStadium());
        tvTime.setText(tempObj.getTime());

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

        tvResult1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected.get(1).booleanValue() == false) {
                    tvResult1.setBackgroundColor(Color.BLUE);
                    tvResult1.setTextColor(Color.WHITE);
                    tvResult1.invalidate();
                    isSelected.set(1, true);
                } else {
                    tvResult1.setBackgroundColor(Color.parseColor("#DCDCDC"));
                    tvResult1.setTextColor(Color.BLACK);
                    tvResult1.invalidate();
                    isSelected.set(1, false);
                }
            }
        });

        tvResult2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected.get(2).booleanValue() == false) {
                    tvResult2.setBackgroundColor(Color.BLUE);
                    tvResult2.setTextColor(Color.WHITE);
                    tvResult2.invalidate();
                    isSelected.set(2,true);
                } else {
                    tvResult2.setBackgroundColor(Color.parseColor("#DCDCDC"));
                    tvResult2.setTextColor(Color.BLACK);
                    tvResult2.invalidate();
                    isSelected.set(2,false);
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
            @Override
            public void onClick(View v) {
                ////
                Toast.makeText(getContext(), "Match is added to Calculator", Toast.LENGTH_SHORT).show();

            }
        });
        return rowView;
    }
}