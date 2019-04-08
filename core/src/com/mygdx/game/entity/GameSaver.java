package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.FileWriter;
import java.io.IOException;

public class GameSaver {

    private String saveFileName = "saves/save.txt";
    private String[] saveInfo;


    public void save(String filePath, CharacterInspector myChar,FrontScripter frontScripter, PictureInspector pictureInspector,
                     int pictureIndex, int speakerIndex) throws IOException {

        FileWriter writer = new FileWriter(saveFileName, false);

        writer.write(filePath);
        writer.append("\n");

        writer.write(myChar.getHealthValue() + "\n" + myChar.getDwamnessValue() + "\n" + myChar.getDetoxificationValue() +
                "\n" + myChar.getWinnerPointValue());
        writer.append("\n");

        writer.write((frontScripter.getIter()-1)+"");
        writer.append("\n");

        writer.write(pictureInspector.getPicturePath());
        writer.append("\n");

        writer.write(pictureIndex+"");
        writer.append("\n");

        writer.write(speakerIndex+"");

        writer.flush();
        writer.close();
    }

    public String[] getSaveInfoFromFile() {
        FileHandle file = Gdx.files.internal(saveFileName);
        String line = file.readString();
        if (line.isEmpty()) {
            return null;
        }
        saveInfo = line.split("\n");

        return saveInfo;
    }


    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String[] getSaveInfo() {
        return saveInfo;
    }

    public void setSaveInfo(String[] saveInfo) {
        this.saveInfo = saveInfo;
    }
}
