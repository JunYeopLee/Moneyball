package com.example.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.junyeop_imaciislab.moneyball.R;
import com.example.junyeop_imaciislab.moneyball.common.view.MatchupPrediction;

import java.util.ArrayList;

/**
 * Created by junyeop_imaciislab on 2015. 6. 18..
 */
public class BettingAdapter extends ArrayAdapter<MatchupPrediction> {
    private final Activity context;
    private ArrayList<MatchupPrediction> matchupPredictionsLists;

    public BettingAdapter(Activity context, ArrayList<MatchupPrediction> matchupPredictionsLists) {
        super(context, R.layout.score_prediction_item, matchupPredictionsLists);
        this.context = context;
        this.matchupPredictionsLists = matchupPredictionsLists;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.betting_moneyball_item, null, true);
        ImageView imgTeam1 = (ImageView) rowView.findViewById(R.id.bet_team1_logo);
        ImageView imgTeam2 = (ImageView) rowView.findViewById(R.id.bet_team2_logo);
        final TextView rateTeam1 = (TextView) rowView.findViewById(R.id.bet_rate1);
        final TextView rateTeam2 = (TextView) rowView.findViewById(R.id.bet_rate2);
        final MatchupPrediction tempObj = matchupPredictionsLists.get(position);
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
        final LinearLayout linearLayout = (LinearLayout)rowView.findViewById(R.id.bet_match_info);
        rateTeam1.setText(tempObj.getRate1());
        rateTeam2.setText(tempObj.getRate2());
        if(tempObj.getRate1_selected().booleanValue()==true) {
            rateTeam1.setBackgroundColor(R.drawable.com_rounded_corner_bettingmoneyball_leftclicked); rateTeam1.invalidate();
        } else if(tempObj.getRate2_selected()==true) {
            rateTeam2.setBackgroundColor(R.drawable.com_rounded_corner_bettingmoneyball_rightclicked); rateTeam2.invalidate();
        }
        rateTeam1.setOnClickListener(new View.OnClickListener() {
            boolean isclicked = false;
            @Override
            public void onClick(View v) {
                if(isclicked) return;
                isclicked = true;
                if(tempObj.getRate1_selected()==false) {
                    tempObj.setRate1_selected(true);
                    rateTeam1.setBackgroundResource(R.drawable.com_rounded_corner_bettingmoneyball_leftclicked); rateTeam1.setTextColor(Color.WHITE); rateTeam1.invalidate();
                    if(tempObj.getRate2_selected()==true) {
                        tempObj.setRate2_selected(false);
                        rateTeam2.setBackgroundResource(0); rateTeam2.setTextColor(Color.BLACK); rateTeam2.invalidate();
                    }
                } else {
                    tempObj.setRate1_selected(false);
                    rateTeam1.setBackgroundResource(0); rateTeam1.setTextColor(Color.BLACK); rateTeam1.invalidate();
                }
                isclicked = false;
            }
        });
        rateTeam2.setOnClickListener(new View.OnClickListener() {
            boolean isclicked = false;
            @Override
            public void onClick(View v) {
                if(isclicked) return;
                isclicked = true;
                if(tempObj.getRate2_selected()==false) {
                    tempObj.setRate2_selected(true);
                    rateTeam2.setBackgroundResource(R.drawable.com_rounded_corner_bettingmoneyball_rightclicked); rateTeam2.setTextColor(Color.WHITE); rateTeam2.invalidate();
                    if(tempObj.getRate1_selected()==true) {
                        tempObj.setRate1_selected(false);
                        rateTeam1.setBackgroundResource(0); rateTeam1.setTextColor(Color.BLACK); rateTeam1.invalidate();
                    }
                } else {
                    tempObj.setRate2_selected(false);
                    rateTeam2.setBackgroundResource(0); rateTeam2.setTextColor(Color.BLACK); rateTeam2.invalidate();
                }
                isclicked = false;
            }
        });
        return rowView;
    }
}