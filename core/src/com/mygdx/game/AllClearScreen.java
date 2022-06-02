package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AllClearScreen extends HUDScreen {
    private SpriteBatch HUDBatch;
    private Sprite Clear;
    private Texture texture1;
    private final ScreenMusic screenMusic;
    private final GameMode gameMode;
    private final Screen screen;
    public boolean complete = false;
    public boolean restart = false;
    public boolean stage = false;

    public AllClearScreen(ScreenMusic screenMusic, GameMode gameMode, Screen screen) {
        this.screenMusic = screenMusic;
        this.gameMode = gameMode;
        this.screen = screen;

        HUDBatch = new SpriteBatch();
        texture1 = new Texture("StageSelection/ClearScreen.png");
        Clear = new Sprite(texture1);
        Clear.setPosition(-80, -300);
        Clear.setScale(0.8f);
    }

    private void render() {
        HUDBatch.begin();
        Clear.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > 370 && Gdx.input.getX() < 702 &&
                    Gdx.input.getY() > 705 && Gdx.input.getY() < 780) {
                restart = true;
            }
            if (Gdx.input.getX() > 1080 && Gdx.input.getX() < 1360 &&
                    Gdx.input.getY() > 705 && Gdx.input.getY() < 800) {
                stage = true;
            }
        }
        HUDBatch.end();
    }

    private void initial() {
        complete = false;
        restart = false;
        stage = false;
    }

    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
    }

    public void stateAnalyze() {
        if (complete) {
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
