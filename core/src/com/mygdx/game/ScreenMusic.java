package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class ScreenMusic{

    private Music gameLobbyMusic;

    public ScreenMusic(){
        gameLobbyMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/VID_20220204_112731.mp3"));
    }

    public void playGameLobbyMusic(){
        gameLobbyMusic.setLooping(true);
        gameLobbyMusic.setVolume(0.4f);
        gameLobbyMusic.play();
    }

    public void dispose(){
        gameLobbyMusic.stop();
        gameLobbyMusic.dispose();
    }
}
