package com.example.junyeop_imaciislab.moneyball.common.view;

import java.util.ArrayList;

/**
 * Created by junyeop_imaciislab on 2015. 6. 12..
 */
public class CalculatorItemWrapper {
    private static ArrayList<MatchupPrediction> calculatorItem;

    public CalculatorItemWrapper() {
        if(calculatorItem==null)
            calculatorItem = new ArrayList<MatchupPrediction>();
    }

    public static ArrayList<MatchupPrediction> getCalculatorItem() {
        return calculatorItem;
    }

    public static void setCalculatorItem(ArrayList<MatchupPrediction> calculatorItem) {
        CalculatorItemWrapper.calculatorItem = calculatorItem;
    }
}