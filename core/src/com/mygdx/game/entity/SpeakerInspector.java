package com.mygdx.game.entity;

import java.util.ArrayList;

public class SpeakerInspector {
    private ArrayList<String> speakersList;
    private ArrayList<Integer> speakerChangeIndexes;
    private ScriptReader scripter;
    private FrontScripter frontScripter;
    private int changeIndex = 0;


    public SpeakerInspector(ScriptReader scripter, FrontScripter frontScripter) {
        this.scripter =scripter;
        this.frontScripter=frontScripter;
        speakersList=new ArrayList<String>();
        speakerChangeIndexes =new ArrayList<Integer>();
    }

    public ArrayList<Integer> getSpeakerChangeIndexesFromScript() {
        speakerChangeIndexes.clear();
        for (int i = 0; i < frontScripter.getFrontScript().size(); i++) {
            if (scripter.getScriptList().get(i).contains("|")) {
                speakerChangeIndexes.add(i);
            }
        }
        return speakerChangeIndexes;
    }

    public ArrayList<String> getSpeakersListFromScript() {
        String speakerName;
        speakersList.clear();
        for (int i = 0; i < frontScripter.getFrontScript().size(); i++) {
            if (frontScripter.getFrontScript().get(i).contains("|")) {
                speakerName=frontScripter.getFrontScript().get(i).substring(1);
                speakersList.add(speakerName);
            }
        }
        return speakersList;
    }


    public ArrayList<String> getSpeakersList() {
        return speakersList;
    }

    public void setSpeakersList(ArrayList<String> speakersList) {
        this.speakersList = speakersList;
    }

    public ArrayList<Integer> getSpeakerChangeIndexes() {
        return speakerChangeIndexes;
    }

    public void setSpeakerChangeIndexes(ArrayList<Integer> speakerChangeIndexes) {
        this.speakerChangeIndexes = speakerChangeIndexes;
    }

    public ScriptReader getScripter() {
        return scripter;
    }

    public void setScripter(ScriptReader scripter) {
        this.scripter = scripter;
    }

    public int getChangeIndex() {
        return changeIndex;
    }

    public void setChangeIndex(int changeIndex) {
        this.changeIndex = changeIndex;
    }
}
