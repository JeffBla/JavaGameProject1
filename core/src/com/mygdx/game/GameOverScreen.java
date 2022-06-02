package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen extends HUDScreen{
    private SpriteBatch HUDBatch;
    private Sprite GameOver;
    private Texture texture1;
    private ScreenMusic screenMusic;
    private GameMode gameMode;
    private final Screen screen;
    public static boolean gameover = false;
    public boolean restart = false;
    public boolean stage = false;

    public GameOverScreen(ScreenMusic screenMusic, GameMode gameMode, Screen screen) {
        this.screenMusic = screenMusic;
        this.gameMode = gameMode;
        this.screen = screen;

        HUDBatch = new SpriteBatch();

        texture1 = new Texture("StageSelection/GameOverScreen.png");
        GameOver = new Sprite(texture1);
        GameOver.setPosition(-10, -250);
        GameOver.setScale(0.8f);
    }

    private void render() {
        HUDBatch.begin();
        GameOver.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > 452 && Gdx.input.getX() < 788 &&
                    Gdx.input.getY() > 643 && Gdx.input.getY() < 715) {
                restart = true;
            } else if (Gdx.input.getX() > 1110 && Gdx.input.getX() < 1387 &&
                    Gdx.input.getY() > 643 && Gdx.input.getY() < 733) {
                stage = true;
            }
        }
        HUDBatch.end();
    }

    private void initial() {
        gameover = false;
        restart = false;
        stage = false;
    }

    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
    }

    public void stateAnalyze() {
        if (gameover) {
            screenMusic.stopLevelMusic();
            render();
            if (restart) {
                initial();
                gameMode.setScreen(analyzeCurrentLevel_new(screen, gameMode));
                screen.dispose();
            } else if (stage) {
                initial();
                gameMode.setScreen(new Stageselection(gameMode));
                screen.dispose();
            }
        }
    }
}