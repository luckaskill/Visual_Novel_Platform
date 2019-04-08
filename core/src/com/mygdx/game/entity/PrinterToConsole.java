package com.mygdx.game.entity;

import java.util.ArrayList;

public class PrinterToConsole {

    public void printFrontScript(ArrayList script) {
        System.out.println(script);
        for (int i = 0; i < script.size(); i++) {
            if (script.get(i).equals("/pc")) {
                i += 6;
            }
            if (script.get(i).equals("/c")) {
                break;
            }
            System.out.println(script.get(i));
        }
    }



    public void printAll(ArrayList script) {
        for (int i = 0; i < script.size(); i++) {
            System.out.println(script.get(i));
        }
    }

    public void printPoints(CharacterInspector myChar){
        System.out.println(myChar.getHealthValue()+"\t"+myChar.getDetoxificationValue()+"\t"+myChar.getDwamnessValue()
        +"\t"+myChar.getWinnerPointValue());
    }
}
