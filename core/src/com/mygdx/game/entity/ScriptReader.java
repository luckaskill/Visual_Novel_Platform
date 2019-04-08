package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Arrays;

public class ScriptReader {
    private ArrayList<String> scriptList = new ArrayList<String>();


    public ArrayList<String> readScriptFromFile(String fileName) {
        FileHandle file = Gdx.files.internal("Lerka\\Scenario\\" + fileName);
        String line = file.readString();
        String[] lines = line.split("\n");

        scriptList = new ArrayList<String>(Arrays.asList(lines));
        return scriptList;
    }


    public void setScriptList(ArrayList<String> scriptList) {
        this.scriptList = scriptList;
    }

    public ArrayList<String> getScriptList() {
        return scriptList;
    }
}
