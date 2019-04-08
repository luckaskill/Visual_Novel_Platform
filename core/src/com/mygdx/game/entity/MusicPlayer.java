package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.io.File;
import java.util.Random;

public class MusicPlayer {
    private Music musicPlayer;
    private File[] playList;
    private Random rn;

    public MusicPlayer() {
        rn = new Random();
        playList = new File("C:\\GDXApp\\New_Novel_01\\android\\assets\\Lerka\\MusicPack").listFiles();
    }


    public void randomMusicPlay() {
        musicPlayer = Gdx.audio.newMusic(Gdx.files.internal((playList[rn.nextInt(playList.length-1)].toString())));
        musicPlayer.play();

    }

    public Music getMusicPlayer() {
        return musicPlayer;
    }

    public void setMusicPlayer(Music musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    public File[] getPlayList() {
        return playList;
    }

    public void setPlayList(File[] playList) {
        this.playList = playList;
    }

    public Random getRn() {
        return rn;
    }

    public void setRn(Random rn) {
        this.rn = rn;
    }
}
