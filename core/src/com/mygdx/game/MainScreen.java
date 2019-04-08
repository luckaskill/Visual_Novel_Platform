package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.entity.*;

import java.io.IOException;
import java.util.ArrayList;

public class MainScreen implements Screen {

    private Stage mainStage;
    private String mainFilePath = "Scenario.txt";
    private String picturePath = "Lerka/Pics/coon.jpg";

    private ScriptReader scriptReader = new ScriptReader();
    private BackScripter backScripter = new BackScripter();
    private FrontScripter frontScripter = new FrontScripter();
    private ImplementChoice choiceImpl = new ImplementChoice();
    private GameSaver saver = new GameSaver();
    private MusicPlayer dj = new MusicPlayer();
    private SpeakerInspector speakerer = new SpeakerInspector(scriptReader, frontScripter);
    private CharacterInspector myChar = new CharacterInspector(20, 20, 5, 5);
    private PictureInspector picturer = new PictureInspector(frontScripter);

    private ArrayList<String> script;
    private ArrayList<String> frontScript;
    private ArrayList<String> backScript;
    private ArrayList<String> possibleChoices;
    private ArrayList<String> speakerList;
    private ArrayList<Integer> speakerChangeIndexes;
    private ArrayList<Integer> picChangeIndexes;

    private int speakerIndex = 0;
    private int pictureIndex = 0;

    private SpriteBatch picBatch;
    private Texture mainPic;
    private Sprite mainPicSprite;

    private SpriteBatch barBatch;
    private Texture greenLine;
    private Texture redLine;
    private Sprite greenLineSpriteDwam;
    private Sprite greenLineSpriteDetox;
    private Sprite greenLineSpriteHealth;

    private Sprite tranSprite;
    private Texture transpar;

    private Skin mySkin = new Skin(Gdx.files.internal("skin\\glassy-ui.json"));

    private SnapshotArray<Button> choiceButtons = new SnapshotArray<Button>();

    private int countOfPresses = 0;

    private Label mainLabel;
    private Label speakerLabel;

    MainScreen() {
        load();
//        dj.randomMusicPlay();
        scriptReader.setScriptList(scriptReader.readScriptFromFile(mainFilePath));
        script = scriptReader.readScriptFromFile(mainFilePath);
        backScript = backScripter.getBackScriptFromScript(script);
        possibleChoices = backScripter.getPossibleChoicesFromScript(backScript);
        frontScript = frontScripter.getFrontScriptFromScript(script);
        speakerList = speakerer.getSpeakersListFromScript();
        speakerChangeIndexes = speakerer.getSpeakerChangeIndexesFromScript();
        picChangeIndexes = picturer.getPicChangeIndexesFromScript();
        create();
    }


    private void create() {
        mainStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(mainStage);
        Gdx.graphics.setWindowedMode(1600, 900);
//        DisplayMode display = new DisplayMode(1980,1080,)
//        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        picturePath = picturer.getPicturePath();

        createMainPicture();

        createMainLabel();
        createSpeakerLabel();
        createTranSpace();

        createSkipButton();
        createBackButton();
        createSaveButton();
        createToEndButton();

        createDwamnessBar();
        createDetoxBar();
        createHealthBar();

    }


    private void createMainLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("styles.fnt"));


        mainLabel = new Label("ЙО!", labelStyle);
        mainLabel.setSize((float) Gdx.graphics.getWidth() * 8 / 10, (float) Gdx.graphics.getHeight() / 7);
        mainLabel.setPosition((float) Gdx.graphics.getWidth() / 10, (float) Gdx.graphics.getHeight() / 5);
        mainLabel.setWrap(true);
        mainLabel.setAlignment(0);

        mainStage.addActor(mainLabel);
    }

    private void createTranSpace() {
        Pixmap transColour = new Pixmap(Gdx.graphics.getWidth() * 9 / 10,
                Gdx.graphics.getHeight() / 6,
                com.badlogic.gdx.graphics.Pixmap.Format.RGB888);
        transpar = new Texture(transColour);
        tranSprite = new Sprite(transpar);
        tranSprite.setColor(47 / 255f, 17 / 255f, 99 / 255f, 0.8f);
        transColour.fill();
        tranSprite.setPosition((float) Gdx.graphics.getWidth() / 20, (float) Gdx.graphics.getWidth() / 10);
    }

    private void createToEndButton() {
        Button toEnd = new TextButton("toEnd", mySkin, "small");
        toEnd.setSize((float) Gdx.graphics.getWidth() / 10, (float) Gdx.graphics.getHeight() / 25);
        toEnd.setPosition((float) Gdx.graphics.getWidth() / 2 + (float) Gdx.graphics.getWidth() / 8,
                (float) Gdx.graphics.getHeight() / 20);
        toEnd.setColor(Color.GRAY);
        toEnd.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                frontScripter.setIter(frontScripter.getFrontScript().size() - 1);
                try {
                    mainLabel.setText(frontScripter.getScriptLine());
                } catch (IndexOutOfBoundsException e) {
                    frontScripter.setIter(frontScripter.getIter() - 2);
                    mainLabel.setText(frontScripter.getScriptLine());
                }
                speakerIndex = speakerChangeIndexes.size() - 1;
                pictureIndex = picChangeIndexes.size() - 1;
                setNewCue();
                setNewSpeaker();
                setNewPicture();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });

        mainStage.addActor(toEnd);

    }

    private void createSkipButton() {
        Button skipText = new TextButton("Skip", mySkin, "small");
        skipText.setSize((float) Gdx.graphics.getWidth() / 5, (float) Gdx.graphics.getHeight() / 14);
        skipText.setPosition((float) Gdx.graphics.getWidth() / 2 - (float) Gdx.graphics.getWidth() / 10,
                (float) Gdx.graphics.getHeight() / 20);
        skipText.setColor(Color.RED);


        skipText.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                skipTextListener();
            }
        });

        mainStage.addActor(skipText);

    }

    private void setNewPicture() {
        try {
            if (frontScripter.getIter() >= picChangeIndexes.get(pictureIndex)) {
                picturer.setNewPicturePath(frontScript.get(picChangeIndexes.get(pictureIndex)));
                picturePath = picturer.getPicturePath();
                createMainPicture();
                pictureIndex++;
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    private void setNewPictureBack() {
        try {
            if (frontScripter.getIter() <= picChangeIndexes.get(pictureIndex - 1)) {
                pictureIndex--;
                picturer.setNewPicturePath(frontScript.get(picChangeIndexes.get(pictureIndex - 1)));
                picturePath = picturer.getPicturePath();
                createMainPicture();
            }
        } catch (IndexOutOfBoundsException e) {
            picturePath = "Pics/coon.jpg";
//            createMainPicture();
        }
    }

    private void setNewSpeaker() {
        try {
            if (frontScripter.getIter() >= speakerChangeIndexes.get(speakerIndex)) {
                if (speakerList.get(speakerIndex).equals("")) {
                    speakerLabel.setText("");
                    speakerIndex++;
                } else {
                    speakerLabel.setText(speakerList.get(speakerIndex) + ":");
                    speakerIndex++;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    private void setNewSpeakerBack() {
        try {
            if (frontScripter.getIter() <= speakerChangeIndexes.get(speakerIndex - 1)) {
                if (speakerList.get(speakerIndex - 2).equals("")) {
                    speakerLabel.setText("");
                    speakerIndex--;
                } else {
                    speakerLabel.setText(speakerList.get(speakerIndex - 2) + ":");
                    speakerIndex--;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    private void setNewCue() {
        try {
            frontScripter.setIter(frontScripter.getIter() + 1);
            if (frontScripter.getScriptLine(frontScripter.getIter()).equals("END")) {
                frontScripter.setIter((frontScripter.getIter() - 1));
            }
            mainLabel.setText(frontScripter.getScriptLine());

        } catch (IndexOutOfBoundsException e) {
            mainLabel.setText("");
        }
    }

    private void setNewCueBack() {
        try {
            frontScripter.setIter(frontScripter.getIter() - 1);
            mainLabel.setText(frontScripter.getPreScriptLine());
        } catch (IndexOutOfBoundsException e) {
            frontScripter.setIter(0);
        }

    }

    private void createMainPicture() {
        mainPic = new Texture(picturePath);
        picBatch = new SpriteBatch();
        mainPicSprite = new Sprite(mainPic);
        mainPicSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void createBackButton() {
        ImageButton backButton;
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable
                (new TextureRegion(new Texture(Gdx.files.internal("arrow.jpg"))));

        backButton = new ImageButton(textureRegionDrawable);
        backButton.setSize(180, 85);
        backButton.setPosition((float) Gdx.graphics.getWidth() * 4 / 15, (float) Gdx.graphics.getHeight() / 60);

        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (countOfPresses < 1) {
                    createMainPicture();
                    countOfPresses++;
                }

                setNewCueBack();
                setNewPictureBack();
                setNewSpeakerBack();
            }
        });

        mainStage.addActor(backButton);
    }

    private void createSaveButton() {
        Button saveButton = new TextButton("Save", mySkin, "small");
        saveButton.setSize((float) Gdx.graphics.getWidth() / 13, (float) Gdx.graphics.getHeight() / 20);
        saveButton.setPosition((float) (10 * Gdx.graphics.getWidth() / 11), (float) (13 * Gdx.graphics.getHeight() / 14));
        saveButton.setColor(Color.YELLOW);

        saveButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    saver.save(mainFilePath, myChar, frontScripter, picturer, pictureIndex, speakerIndex);
                } catch (IOException ignored) {
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        mainStage.addActor(saveButton);
    }


    private void createChoiceButtons(int choicesCount, CharacterInspector myChar) {

        float defaultScaleY = (float) (Gdx.graphics.getHeight() / 1.62);
        int choiceNumber = 0;
        if (choicesCount >= 3) {
            defaultScaleY += (float) Gdx.graphics.getHeight() / 8;
        }

        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        for (int i = 0; i < choicesCount; i++) {
            String choice = backScripter.getPossibleChoices().get(choiceNumber);

            Button buttonChoice = new TextButton(choice, mySkin, "small");
            buttonChoice.setSize(Gdx.graphics.getWidth() - (float) Gdx.graphics.getWidth() / 5, (float) Gdx.graphics.getHeight() / 10);
            buttonChoice.setPosition((float) Gdx.graphics.getWidth() / 10, defaultScaleY);
            buttonChoice.setColor(Color.BLUE);

            choiceButtons.add(buttonChoice);

            setChoiceButtonAction(choice, buttonChoice, myChar);
            choiceNumber++;
            defaultScaleY -= Gdx.graphics.getHeight() / 6.4;

            mainStage.addActor(buttonChoice);
        }

    }

    private void setChoiceButtonAction(final String choice, Button buttonChoice, final CharacterInspector myChar) {

        buttonChoice.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setNewCue();
                setNewSpeaker();
                setNewPicture();
                setAllPointValuesToBars();
                removeButtons(choiceButtons);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                choiceImpl.setNewPointValues(choice, myChar, backScript);
                mainFilePath = choiceImpl.getNewFilePathFromScript(backScript, choice);
                refreshGameSpace();
                return true;
            }
        });
    }

    private void refreshGameSpace() {
        frontScripter.setIter(0);
        script = scriptReader.readScriptFromFile(mainFilePath);
        backScript = backScripter.getBackScriptFromScript(script);
        frontScript = frontScripter.getFrontScriptFromScript(script);
        possibleChoices = backScripter.getPossibleChoicesFromScript(backScript);
        speakerList = speakerer.getSpeakersListFromScript();
        picChangeIndexes = picturer.getPicChangeIndexesFromScript();
        speakerChangeIndexes = speakerer.getSpeakerChangeIndexesFromScript();
        speakerIndex = 0;
        pictureIndex = 0;
    }

    private void setAllPointValuesToBars() {
        setValueToBar(myChar.getHealthValue(), greenLineSpriteHealth);
        setValueToBar(myChar.getDetoxificationValue(), greenLineSpriteDetox);
        setValueToBar(myChar.getDwamnessValue(), greenLineSpriteDwam);
    }


    private void removeButtons(SnapshotArray<Button> buttons) {
        for (Button button : buttons) {
            button.remove();
        }
        buttons.clear();

    }

    private void createSpeakerLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("speaker-styles.fnt"));

        speakerLabel = new Label("", labelStyle);
        speakerLabel.setSize(Gdx.graphics.getWidth() - (float) Gdx.graphics.getWidth() / 10, (float) Gdx.graphics.getHeight() / 10);
        speakerLabel.setPosition((float) Gdx.graphics.getWidth() / 17, (float) (Gdx.graphics.getHeight() / 3.37));

        mainStage.addActor(speakerLabel);
    }

    private void skipTextListener() {
        if (countOfPresses < 1) {
            createMainPicture();
            try {
                speakerLabel.setText(speakerList.get(speakerIndex - 1));
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
            countOfPresses++;
        }

        setNewCue();
        setNewSpeaker();

        if (frontScripter.getIter() >= frontScript.size() - 1) {
//                if (frontScripter.getIter() >= 1) {
            if (possibleChoices.size() > 0) {
                createChoiceButtons(possibleChoices.size(), myChar);
            } else {
                choiceImpl.setNewPointValues(myChar, backScript);
                setAllPointValuesToBars();
                mainFilePath = choiceImpl.getNewFilePathFromScript(backScript);
                refreshGameSpace();
            }
        }

        setNewPicture();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(60 / 255f, 13 / 255f, 110 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        picBatch.begin();
        mainPicSprite.draw(picBatch);
        tranSprite.draw(picBatch);
        picBatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            skipTextListener();
        }

        barBatch.begin();
        barBatch.draw(redLine, (float) (Gdx.graphics.getWidth() / 1.3), (float) Gdx.graphics.getHeight() / 9,
                146, greenLine.getHeight());
        greenLineSpriteDwam.draw(barBatch);

        barBatch.draw(greenLine, (float) (Gdx.graphics.getWidth() / 1.3),
                (float) Gdx.graphics.getHeight() / 20, 146, greenLine.getHeight());
        greenLineSpriteDetox.draw(barBatch);

        barBatch.draw(redLine, (float) (Gdx.graphics.getWidth() / 10),
                (float) Gdx.graphics.getHeight() / 18, 146, greenLine.getHeight());
        greenLineSpriteHealth.draw(barBatch);
        barBatch.end();


        mainStage.act();

        mainStage.draw();

    }

    private void load() {
        if (saver.getSaveInfoFromFile() != null) {
            mainFilePath = saver.getSaveInfo()[0];
            myChar.setHealthValue(Float.parseFloat(saver.getSaveInfo()[1]));
            myChar.setDwamnessValue(Float.parseFloat(saver.getSaveInfo()[2]));
            myChar.setDetoxificationValue(Float.parseFloat(saver.getSaveInfo()[3]));
            myChar.setWinnerPointValue(Float.parseFloat(saver.getSaveInfo()[4]));
            frontScripter.setIter(Integer.parseInt(saver.getSaveInfo()[5]));
            picturer.setPicturePath(saver.getSaveInfo()[6]);
            pictureIndex = Integer.parseInt(saver.getSaveInfo()[7]);
            speakerIndex = Integer.parseInt(saver.getSaveInfo()[8]);
        }
    }

    private void createDwamnessBar() {
        greenLine = new Texture("greenLine.jpg");
        barBatch = new SpriteBatch();
        redLine = new Texture("redLine.jpg");
        greenLineSpriteDwam = new Sprite(greenLine);
        greenLineSpriteDwam.setPosition((float) (Gdx.graphics.getWidth() / 1.3), (float) Gdx.graphics.getHeight() / 9);
        setValueToBar(myChar.getDwamnessValue(), greenLineSpriteDwam);
    }

    private void setValueToBar(float value, Sprite greenLineSprite) {
        greenLineSprite.setSize(142 * (value / 100), greenLine.getHeight());
    }

    private void createDetoxBar() {
        greenLineSpriteDetox = new Sprite(greenLine);
        greenLineSpriteDetox.setPosition((float) (Gdx.graphics.getWidth() / 1.3), (float) Gdx.graphics.getHeight() / 20);
        greenLineSpriteDetox.setColor(Color.BLUE);
        setValueToBar(myChar.getDetoxificationValue(), greenLineSpriteDetox);
    }

    private void createHealthBar() {
        greenLineSpriteHealth = new Sprite(greenLine);
        greenLineSpriteHealth.setPosition((float) Gdx.graphics.getWidth() / 10, (float) Gdx.graphics.getHeight() / 18);
        setValueToBar(myChar.getHealthValue(), greenLineSpriteHealth);
    }


    @Override
    public void resize(int width, int height) {
        mainStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
