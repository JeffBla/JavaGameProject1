package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class ScreenMusic{

    private Music LevelMusic;

    public ScreenMusic(){
        LevelMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/VID_20220204_112731.mp3"));
    }

    public void playGameLobbyMusic(){
        LevelMusic.setLooping(true);
        LevelMusic.setVolume(0.4f);
        LevelMusic.play();
    }

    public void stopLevelMusic(){
        LevelMusic.stop();
    }

    public void dispose(){
        stopLevelMusic();
        LevelMusic.dispose();
    }
}
