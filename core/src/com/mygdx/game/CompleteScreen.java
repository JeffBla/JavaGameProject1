package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CompleteScreen extends HUDScreen{
    private SpriteBatch HUDBatch;
    private Sprite Complete;
    private Texture texture1;
    private final ScreenMusic screenMusic;
    private final GameMode gameMode;
    private final Screen screen;
    public boolean complete = false;
    public boolean restart = false;
    public boolean stage = false;
    public boolean nextstage = false;

    public CompleteScreen(ScreenMusic screenMusic, GameMode gameMode, Screen screen) {
        this.screenMusic = screenMusic;
        this.gameMode = gameMode;
        this.screen = screen;

        HUDBatch = new SpriteBatch();

        texture1 = new Texture("StageSelection/StageComplete.png");
        Complete = new Sprite(texture1);
        Complete.setPosition(-10, -250);
        Complete.setScale(0.8f);
    }

    private void render() {
        HUDBatch.begin();
        Complete.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > 405 && Gdx.input.getX() < 748 &&
                    Gdx.input.getY() > 637 && Gdx.input.getY() < 730) {
                restart = true;
            } else if (Gdx.input.getX() > 835 && Gdx.input.getX() < 1047 &&
                    Gdx.input.getY() > 637 && Gdx.input.getY() < 730) {
                stage = true;
            } else if (Gdx.input.getX() > 1162 && Gdx.input.getX() < 1452 &&
                    Gdx.input.getY() > 613 && Gdx.input.getY() < 775) {
                nextstage = true;
            }
        }
        HUDBatch.end();
    }

    private void initial() {
        complete = false;
        restart = false;
        stage = false;
        nextstage = false;
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
            } else if (nextstage) {
                initial();
                gameMode.setScreen(nextLevel_new(screen,gameMode));
                screen.dispose();
            }
        }
    }
}
