package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.junyeop_imaciislab.moneyball.R;
import com.example.junyeop_imaciislab.moneyball.common.adapter.PredictionAdapter;
import com.example.junyeop_imaciislab.moneyball.common.view.MatchupPrediction;

import java.util.ArrayList;

public class ScorePredictionActivity extends ActionBarActivity {

    ListView listPrediction;
    //PredictionAdapter predictionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_prediction);

        ArrayList<MatchupPrediction> matchupPrediction = new ArrayList<MatchupPrediction>();
        MatchupPrediction tmpPrediction = new MatchupPrediction();

        tmpPrediction.setStadium("Dodger Stadium");
        tmpPrediction.setTime("19:05");
        tmpPrediction.setTeam1("Samsung");
        tmpPrediction.setTeam2("Lotte");
        String [] tmpResults = {"10 : 3", "5 : 2", "1 : 3" ,"5 : 7" , "10 : 11"};
        tmpPrediction.setResults(tmpResults);
        String [] tmpProbs = {"10%", "15%", "20%" ,"25%" ,"30%"};
        tmpPrediction.setProb(tmpProbs);


        matchupPrediction.add(0, tmpPrediction);
        matchupPrediction.add(1, tmpPrediction);
        matchupPrediction.add(2, tmpPrediction);
        matchupPrediction.add(3, tmpPrediction);
        matchupPrediction.add(4, tmpPrediction);

        listPrediction = (ListView)findViewById(R.id.prediction_list);

        PredictionAdapter predictionAdapter = new PredictionAdapter(this,matchupPrediction);
        listPrediction.setAdapter(predictionAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_prediction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
