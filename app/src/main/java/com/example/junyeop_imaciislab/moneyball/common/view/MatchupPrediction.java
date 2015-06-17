package com.example.junyeop_imaciislab.moneyball.common.view;

import java.util.ArrayList;

/**
 * Created by junyeop_imaciislab on 2015. 5. 22..
 */
public class MatchupPrediction {
    private String stadium;
    public String time;
    public String team1;
    public String team2;
    public String [] results;
    public String [] prob;
    public ArrayList<Boolean> isSelected;

    public MatchupPrediction() {
        isSelected = new ArrayList<Boolean>();
        for( int i = 0 ; i <  5 ; i++ ) {
            isSelected.add(false);
        }
    }

    public ArrayList<Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(ArrayList<Boolean> isSelected) {
        this.isSelected = isSelected;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String[] getResults() {
        return results;
    }

    public void setResults(String[] results) {
        this.results = results;
    }

    public String[] getProb() {
        return prob;
    }

    public void setProb(String[] prob) {
        this.prob = prob;
    }

}