package com.example.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.moneyball.R;
import com.example.junyeop_imaciislab.moneyball.common.view.MatchupPrediction;

import java.util.ArrayList;

/**
 * Created by junyeop_imaciislab on 2015. 5. 22..
 */
public class PredictionAdapter extends ArrayAdapter<MatchupPrediction> {
    private final Activity context;
    private ArrayList<MatchupPrediction> matchupPredictionsLists;

    public PredictionAdapter(Activity context, ArrayList<MatchupPrediction> matchupPredictionsLists) {
        super(context, R.layout.score_prediction_item, matchupPredictionsLists);
        this.context = context;
        this.matchupPredictionsLists = matchupPredictionsLists;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.score_prediction_item, null, true);

        TextView tvStadium = (TextView) rowView.findViewById(R.id.tv_stadium);
        TextView tvTime = (TextView) rowView.findViewById(R.id.tv_match_time);
        ImageView imgTeam1 = (ImageView) rowView.findViewById(R.id.team1_logo);
        ImageView imgTeam2 = (ImageView) rowView.findViewById(R.id.team2_logo);
        TextView tvResult1 = (TextView) rowView.findViewById(R.id.tv_1result);
        TextView tvResult2 = (TextView) rowView.findViewById(R.id.tv_2result);
        Button btnResult3 = (Button) rowView.findViewById(R.id.btn_3result);
        Button btnResult4 = (Button) rowView.findViewById(R.id.btn_4result);
        Button btnResult5 = (Button) rowView.findViewById(R.id.btn_5result);
        Button btnProb1 = (Button) rowView.findViewById(R.id.btn_1prob);
        Button btnProb2 = (Button) rowView.findViewById(R.id.btn_2prob);
        Button btnProb3 = (Button) rowView.findViewById(R.id.btn_3prob);
        Button btnProb4 = (Button) rowView.findViewById(R.id.btn_4prob);
        Button btnProb5 = (Button) rowView.findViewById(R.id.btn_5prob);


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
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "KIA":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "Hanhwa":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "LG":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "Doosan":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "Nexen":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "SK":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "KT":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
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
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "KIA":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "Hanhwa":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "LG":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "Doosan":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "Nexen":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "SK":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "KT":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
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

        return rowView;

    }


}