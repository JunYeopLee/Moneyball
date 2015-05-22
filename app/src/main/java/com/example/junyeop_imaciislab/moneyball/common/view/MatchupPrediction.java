package com.example.junyeop_imaciislab.moneyball.common.view;

/**
 * Created by junyeop_imaciislab on 2015. 5. 22..
 */
public class MatchupPrediction {
    public static String stadium;
    public static String time;
    public static String team1;
    public static String team2;
    public static String [] results;
    public static String [] prob;


    public static String getStadium() {
        return stadium;
    }

    public static void setStadium(String stadium) {
        MatchupPrediction.stadium = stadium;
    }

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        MatchupPrediction.time = time;
    }

    public static String getTeam1() {
        return team1;
    }

    public static void setTeam1(String team1) {
        MatchupPrediction.team1 = team1;
    }

    public static String getTeam2() {
        return team2;
    }

    public static void setTeam2(String team2) {
        MatchupPrediction.team2 = team2;
    }

    public static String[] getResults() {
        return results;
    }

    public static void setResults(String[] results) {
        MatchupPrediction.results = results;
    }

    public static String[] getProb() {
        return prob;
    }

    public static void setProb(String[] prob) {
        MatchupPrediction.prob = prob;
    }



}
