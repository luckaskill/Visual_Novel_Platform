package com.mygdx.game.entity;

import java.util.ArrayList;

public class PictureInspector {
    private String picturePath;
    private FrontScripter frontScripter;
    private ArrayList<Integer> picChangeIndexes;

    public PictureInspector(FrontScripter frontScripter) {
        picturePath = "Lerka/Pics/coon.jpg";
        this.frontScripter = frontScripter;
        picChangeIndexes=new ArrayList<Integer>();
    }


    public ArrayList<Integer> getPicChangeIndexesFromScript() {
        picChangeIndexes.clear();
        for (int i = 0; i < frontScripter.getFrontScript().size(); i++) {
            if(frontScripter.getFrontScript().get(i).contains("/pic")){
                picChangeIndexes.add(i);
            }
        }
        return picChangeIndexes;
    }

    public void setNewPicturePath(String scriptPictureLine) {
        picturePath = "Lerka/Pics/" + scriptPictureLine.substring(5);
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public FrontScripter getFrontScripter() {
        return frontScripter;
    }

    public void setFrontScripter(FrontScripter frontScripter) {
        this.frontScripter = frontScripter;
    }

    public ArrayList<Integer> getPicChangeIndexes() {
        return picChangeIndexes;
    }

    public void setPicChangeIndexes(ArrayList<Integer> picChangeIndexes) {
        this.picChangeIndexes = picChangeIndexes;
    }
}
