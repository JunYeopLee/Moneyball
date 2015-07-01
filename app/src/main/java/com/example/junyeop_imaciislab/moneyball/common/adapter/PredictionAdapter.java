package com.example.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by junyeop_imaciislab on 2015. 5. 22..
 */
public class PredictionAdapter extends ArrayAdapter<MatchupPrediction> {
    private final Activity context;

    public ArrayList<MatchupPrediction> getMatchupPredictionsLists() {
        return matchupPredictionsLists;
    }

    private ArrayList<MatchupPrediction> matchupPredictionsLists;

    public PredictionAdapter(Activity context, ArrayList<MatchupPrediction> matchupPredictionsLists) {
        super(context, R.layout.score_prediction_item, matchupPredictionsLists);
        this.context = context;
        this.matchupPredictionsLists = matchupPredictionsLists;
    }


    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
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
        final Button PlusBt = (Button) rowView.findViewById(R.id.btn_wish_plus);
        ((TextView)rowView.findViewById(R.id.item_index)).setText(String.valueOf(position));
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

        if(tempObj.getResults()[2].compareTo("0")==0) {
            btnResult3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ab.show();
                }
            });
        } else {
            btnResult3.setText(tempObj.getResults()[2]);
            btnResult3.setBackgroundColor(Color.parseColor("#DCDCDC"));
            btnResult3.setTextColor(Color.BLACK);
            btnResult3.setTypeface(Typeface.DEFAULT_BOLD);
            btnResult3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }

        if(tempObj.getResults()[3].compareTo("0")==0) {
            btnResult4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ab.show();
                }
            });
        } else {
            btnResult4.setText(tempObj.getResults()[3]);
            btnResult4.setBackgroundColor(Color.parseColor("#DCDCDC"));
            btnResult4.setTextColor(Color.BLACK);
            btnResult4.setTypeface(Typeface.DEFAULT_BOLD);
            btnResult4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }

        if(tempObj.getResults()[4].compareTo("0")==0) {
            btnResult5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ab.show();
                }
            });
        } else {
            btnResult5.setText(tempObj.getResults()[4]);
            btnResult5.setBackgroundColor(Color.parseColor("#DCDCDC"));
            btnResult5.setTextColor(Color.BLACK);
            btnResult5.setTypeface(Typeface.DEFAULT_BOLD);
            btnResult5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }

        if(new CalculatorItemWrapper().getCalculatorItem().indexOf(matchupPredictionsLists.get(position)) != -1) {
            PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus_checked));
        }
        //if(new CalculatorItemWrapper().getCalculatorItem().indexOf(matchupPredictionsLists.get(position)) == -1) {
            PlusBt.setOnClickListener(new View.OnClickListener() {
                boolean lock = false;
                @Override
                public void onClick(View v) {
                    ////
                    if(new CalculatorItemWrapper().getCalculatorItem().indexOf(matchupPredictionsLists.get(position)) == -1) {
                        if(lock) return;
                        lock = true;
                        CalculatorItemWrapper calculatorItemWrapper = new CalculatorItemWrapper();
                        ArrayList<MatchupPrediction> calculatorItemArrayList;
                        calculatorItemArrayList = calculatorItemWrapper.getCalculatorItem();
                        calculatorItemArrayList.add(matchupPredictionsLists.get(position));
                        calculatorItemWrapper.setCalculatorItem(calculatorItemArrayList);

                        ListView calculatorList;
                        calculatorList = (ListView) ((Activity) getContext()).findViewById(R.id.calculator_list);
                        CalculatorAdapter calculatorAdapter = new CalculatorAdapter((Activity) getContext(), calculatorItemArrayList);
                        calculatorAdapter.notifyDataSetChanged();
                        calculatorList.setAdapter(calculatorAdapter);
                        PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus_checked));
                        Toast.makeText(getContext(), "Match is added to Calculator", Toast.LENGTH_SHORT).show();
                        lock = false;
                    } else {
                        if(lock) return;
                        lock = true;
                        CalculatorItemWrapper calculatorItemWrapper = new CalculatorItemWrapper();
                        ArrayList<MatchupPrediction> calculatorItemArrayList;
                        calculatorItemArrayList = calculatorItemWrapper.getCalculatorItem();
                        calculatorItemArrayList.remove(matchupPredictionsLists.get(position));
                        calculatorItemWrapper.setCalculatorItem(calculatorItemArrayList);

                        ListView calculatorList;
                        calculatorList = (ListView) ((Activity) getContext()).findViewById(R.id.calculator_list);
                        CalculatorAdapter calculatorAdapter = new CalculatorAdapter((Activity) getContext(), calculatorItemArrayList);
                        calculatorAdapter.notifyDataSetChanged();
                        calculatorList.setAdapter(calculatorAdapter);

                        PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus));
                        Toast.makeText(getContext(), "Match is removed from Calculator", Toast.LENGTH_SHORT).show();
                        lock = false;
                    }
                }
            });
        //} else {
        //    PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus_checked));
        //}
        return rowView;
    }
}