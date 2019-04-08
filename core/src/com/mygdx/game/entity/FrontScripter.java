package com.mygdx.game.entity;

import java.util.ArrayList;

public class FrontScripter {
    private String str;
    private int iter;
    private ArrayList<String> frontScript;

    public FrontScripter() {
        frontScript=new ArrayList<String>();
        str="";
        iter=0;
    }

    public ArrayList<String> getFrontScriptFromScript(ArrayList<String> script) {
        frontScript.clear();
        for (int i = 0; i < script.size(); i++) {
            if (script.get(i).equals("/c") || script.get(i).equals("/fn") ) {
                break;
            }
            frontScript.add(script.get(i));
        }
        return frontScript;
    }


    public String getScriptLine() {
        if (frontScript.get(iter).contains("|")) {
            iter++;
        }
        if (frontScript.get(iter).contains("/pic")) {
            iter++;
        }
        if (frontScript.get(iter).contains("|")) {
            iter++;
        }

        str=frontScript.get(iter);
        return str;
    }


    public String getScriptLine(int iter) {
        str=frontScript.get(iter);
        return str;
    }


   public String getPreScriptLine() {
        if (frontScript.get(iter).contains("|")) {
            iter--;
        }
        if (frontScript.get(iter).contains("/pic")) {
            iter--;
        }
        if (frontScript.get(iter).contains("|")) {
            iter--;
        }

        str=frontScript.get(iter);
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }


    public int getIter() {
        return iter;
    }

    public String getStr() {
        return str;
    }

    public ArrayList<String> getFrontScript() {
        return frontScript;
    }

    public void setIter(int iter) {
        this.iter = iter;
    }

    public void setFrontScript(ArrayList<String> frontScript) {
        this.frontScript = frontScript;
    }

}
