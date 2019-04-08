package com.mygdx.game.entity;

public class CharacterInspector {
    private float healthValue;
    private float winnerPointValue;
    private float detoxificationValue;
    private float dwamnessValue;

    public CharacterInspector(float healthValue, float winnerPointValue, float detoxificationValue, float dwamnessValue) {
        this.healthValue = healthValue;
        this.winnerPointValue = winnerPointValue;
        this.detoxificationValue = detoxificationValue;
        this.dwamnessValue = dwamnessValue;
    }


    public float getHealthValue() {
        return healthValue;
    }


    public float getWinnerPointValue() {
        return winnerPointValue;
    }


    public float getDetoxificationValue() {
        return detoxificationValue;
    }


    public float getDwamnessValue() {
        return dwamnessValue;
    }

    public void setHealthValue(float healthValue) {
        this.healthValue = healthValue;
    }

    public void setWinnerPointValue(float winnerPointValue) {
        this.winnerPointValue = winnerPointValue;
    }

    public void setDetoxificationValue(float detoxificationValue) {
        this.detoxificationValue = detoxificationValue;
    }

    public void setDwamnessValue(float dwamnessValue) {
        this.dwamnessValue = dwamnessValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterInspector that = (CharacterInspector) o;
        return healthValue == that.healthValue &&
                winnerPointValue == that.winnerPointValue &&
                detoxificationValue == that.detoxificationValue &&
                dwamnessValue == that.dwamnessValue;
    }

}
