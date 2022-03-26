package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class ScreenMusic {

    private Music gameLobbyMusic;

    public ScreenMusic(){
        gameLobbyMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/gameLobbyMusic.mp3"));
    }

    public void playGameLobbyMusic(){
        gameLobbyMusic.setLooping(true);
        gameLobbyMusic.setVolume(0.2f);
        gameLobbyMusic.play();
    }
}
