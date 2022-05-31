package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PausedScreen {
    private SpriteBatch HUDBatch;
    private Sprite Paused;
    private Texture texture1;
    public static boolean pause = false;
    public static boolean restart = false;
    public static boolean stage = false;

    public PausedScreen() {
        HUDBatch = new SpriteBatch();

        texture1 = new Texture("StageSelection/PausedScreen.png");
        Paused = new Sprite(texture1);
        Paused.setPosition(-10, -250);
        Paused.setScale(0.8f);
    }

    public void render(float delta,String Stage) {
        HUDBatch.begin();
        Paused.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > 760 && Gdx.input.getX() < 1125
                    && Gdx.input.getY() > 330 && Gdx.input.getY() < 405) {
                pause = false;
                switch(Stage){
                    case "GameLobby":
                        GameLobby.screenMusic.playGameLobbyMusic();
                        break;
                    case "Level2":
                        Level2.screenMusic.playGameLobbyMusic();
                        break;
                    case "Level3":
                        Level3.screenMusic.playGameLobbyMusic();
                        break;
                    case "Level4":
                        Level4.screenMusic.playGameLobbyMusic();
                        break;
                }
            } else if (Gdx.input.getX() > 778 && Gdx.input.getX() < 1105
                    && Gdx.input.getY() > 485 && Gdx.input.getY() < 555) {
                restart = true;
            } else if (Gdx.input.getX() > 796 && Gdx.input.getX() < 1078
                    && Gdx.input.getY() > 632 && Gdx.input.getY() < 723) {
                stage = true;
            }
        }
        HUDBatch.end();
    }

    public static void initial(){
        pause=false;
        restart=false;
        stage=false;
    }
    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
    }
}
