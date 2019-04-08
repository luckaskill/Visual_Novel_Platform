package com.mygdx.game.entity;

import java.util.ArrayList;

public class BackScripter {
    private ArrayList<String> backScript = new ArrayList<String>();
    private ArrayList<String> possibleChoices = new ArrayList<String>();


    public ArrayList<String> getBackScriptFromScript(ArrayList<String> script) {
        backScript.clear();
        for (int i = 0; i < script.size(); i++) {
            if (script.get(i).equals("/c") || script.get(i).equals("/fn")) {
                for (int j = i; j < script.size(); j++) {
                    backScript.add(script.get(j));
                }
                break;
            }
        }
        return backScript;
    }

    public ArrayList<String> getPossibleChoicesFromScript(ArrayList<String> backScript) {
        possibleChoices.clear();
        for (int i = 0; i < backScript.size(); i++) {
            if (backScript.get(i).equals("/c")) {
                possibleChoices.add(backScript.get(i + 1));
            }
        }
        return possibleChoices;
    }

    public ArrayList<String> getBackScript() {
        return backScript;
    }

    public void setBackScript(ArrayList<String> backScript) {
        this.backScript = backScript;
    }


    public ArrayList<String> getPossibleChoices() {
        return possibleChoices;
    }

    public void setPossibleChoices(ArrayList<String> possibleChoices) {
        this.possibleChoices = possibleChoices;
    }
}

