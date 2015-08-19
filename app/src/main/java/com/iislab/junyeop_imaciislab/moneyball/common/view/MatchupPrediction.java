package com.iislab.junyeop_imaciislab.moneyball.common.view;

import java.util.ArrayList;

/**
 * Created by junyeop_imaciislab on 2015. 5. 22..
 */
public class MatchupPrediction {
    public int matchNum;
    private String stadium;
    public String time;
    public String team1;
    public String team2;

    public String rate1;
    public String rate2;

    public Boolean rate1_selected;
    public Boolean rate2_selected;

    public String [] results;
    public String [] prob;
    public ArrayList<Boolean> isSelected;

    public MatchupPrediction() {
        isSelected = new ArrayList<Boolean>();
        for( int i = 0 ; i <  6 ; i++ ) {
            isSelected.add(false);
        }
        rate1_selected = rate2_selected = false;
    }


    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public Boolean getRate2_selected() {
        return rate2_selected;
    }

    public void setRate2_selected(Boolean rate2_selected) {
        this.rate2_selected = rate2_selected;
    }

    public Boolean getRate1_selected() {

        return rate1_selected;
    }

    public void setRate1_selected(Boolean rate1_selected) {
        this.rate1_selected = rate1_selected;
    }

    public String getRate2() {
        return rate2;
    }

    public void setRate2(String rate2) {
        this.rate2 = rate2;
    }

    public String getRate1() {

        return rate1;
    }

    public void setRate1(String rate1) {
        this.rate1 = rate1;
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