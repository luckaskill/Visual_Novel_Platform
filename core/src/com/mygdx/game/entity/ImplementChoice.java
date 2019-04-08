package com.mygdx.game.entity;

import java.util.ArrayList;

public class ImplementChoice {
    private String newFilePath = "";

    public String getNewFilePathFromScript(ArrayList<String> backScript) {
        for (int i = 0; i < backScript.size(); i++) {
            if(backScript.get(i).contains("/fn")){
                newFilePath=backScript.get(i+1);
            }
        }
        return newFilePath;
    }

    public String getNewFilePathFromScript(ArrayList<String> backScript, String choice) {
        for (int i = 0; i < backScript.size(); i++) {
            if (choice.equals(backScript.get(i))) {
                newFilePath = backScript.get(i + 2);
                break;
            }
        }
        return newFilePath;
    }

    public void setNewPointValues(CharacterInspector myChar, ArrayList<String> backScript) {
        float value;
        int count = 0;

        for (int i = 0; i < backScript.size(); i++) {
            if (backScript.get(i).equals("/point")) {
                i++;
                while (count < 4) {
                    value = Float.parseFloat(backScript.get(i));

                    if (count == 0) {
                        myChar.setHealthValue((myChar.getHealthValue() + value));
                        if(myChar.getHealthValue()<0){
                            myChar.setHealthValue(0);
                        }
                    }
                    if (count == 1) {
                        myChar.setDetoxificationValue(myChar.getDetoxificationValue()+value);
                        if(myChar.getDetoxificationValue()<0){
                            myChar.setDetoxificationValue(0);
                        }
                    }
                    if (count == 2) {
                        myChar.setDwamnessValue(myChar.getDwamnessValue() + value);
                        if(myChar.getDwamnessValue()<0){
                            myChar.setDwamnessValue(0);
                        }
                    }
                    if (count == 3) {
                        myChar.setWinnerPointValue(myChar.getWinnerPointValue()+ value);
                        if(myChar.getWinnerPointValue()<0){
                            myChar.setWinnerPointValue(0);
                        }
                    }
                    count++;
                    i++;
                }
                break;
            }
        }

    }

    public void setNewPointValues(String choice, CharacterInspector myChar, ArrayList<String> backScript) {
        float value;
        int count = 0;

        for (int j = 0; j < backScript.size(); j++) {
            if (backScript.get(j).equals(choice)) {
                for (int i = j; i < backScript.size(); i++) {
                    if (backScript.get(i).equals("/point")) {
                        i++;
                        while (count < 4) {
                            value = Float.parseFloat(backScript.get(i));

                            if (count == 0) {
                                myChar.setHealthValue((myChar.getHealthValue() + value));
                                if(myChar.getHealthValue()<0){
                                    myChar.setHealthValue(0);
                                }
                            }
                            if (count == 1) {
                                myChar.setDetoxificationValue(myChar.getDetoxificationValue()+value);
                                if(myChar.getDetoxificationValue()<0){
                                    myChar.setDetoxificationValue(0);
                                }
                            }
                            if (count == 2) {
                                myChar.setDwamnessValue(myChar.getDwamnessValue() + value);
                                if(myChar.getDwamnessValue()<0){
                                    myChar.setDwamnessValue(0);
                                }
                            }
                            if (count == 3) {
                                myChar.setWinnerPointValue(myChar.getWinnerPointValue()+ value);
                                if(myChar.getWinnerPointValue()<0){
                                    myChar.setWinnerPointValue(0);
                                }
                            }
                            count++;
                            i++;
                        }
                        break;
                    }
                }
                break;
            }
        }
    }



    public float implementOperation(float value, float newValue, char operation) {
        switch (operation) {
            case '+':
                value += newValue;
                return value;
            case '-':
                System.out.println(value);
                System.out.println(newValue);
                value -= newValue;
                System.out.println(value);
                System.out.println(newValue);
                return value;
        }
        return value;
    }


    public String getNewFilePath() {
        return newFilePath;
    }

    public void setNewFilePath(String newFilePath) {
        this.newFilePath = newFilePath;
    }
}
