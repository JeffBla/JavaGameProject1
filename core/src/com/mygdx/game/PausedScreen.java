package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PausedScreen extends HUDScreen{
    private SpriteBatch HUDBatch;
    private Sprite pausedSprite;
    private Texture texture1;
    private ScreenMusic screenMusic;
    private GameMode gameMode;
    private final Screen screen;
    public static boolean pause = false;
    public boolean resume = false;
    public boolean restart = false;
    public boolean stage = false;

    public PausedScreen(ScreenMusic screenMusic, GameMode gameMode, Screen screen) {
        this.screenMusic = screenMusic;
        this.gameMode = gameMode;
        this.screen = screen;

        HUDBatch = new SpriteBatch();

        texture1 = new Texture("StageSelection/PausedScreen.png");
        pausedSprite = new Sprite(texture1);
        pausedSprite.setPosition(-10, -250);
        pausedSprite.setScale(0.8f);
    }

    private void render() {
        HUDBatch.begin();
        pausedSprite.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > 760 && Gdx.input.getX() < 1125
                    && Gdx.input.getY() > 330 && Gdx.input.getY() < 405) {
                pause = false;
                resume = true;
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

    private void initial() {
        pause = false;
        restart = false;
        stage = false;
    }

    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
    }

    public void stateAnalyze() {
        if (pause) {
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
