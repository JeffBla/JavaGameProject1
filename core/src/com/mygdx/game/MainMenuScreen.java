package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final GameMode gameMode;
    OrthographicCamera camera;
    Texture StartButton;

    public MainMenuScreen(final GameMode gameMode){
        this.gameMode = gameMode;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 700);

        StartButton = new Texture(Gdx.files.internal("play_gameButton.png"));
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0,0,0.2f,0);

        camera.update();
        gameMode.batch.setProjectionMatrix(camera.combined);

        gameMode.batch.begin();
        gameMode.batch.draw(StartButton, 0, 0);
        gameMode.batch.end();

        if(Gdx.input.isTouched()){
            gameMode.setScreen(new GameLobby(gameMode));
            dispose();
        }
    }

    public void show(){

    }

    public void hide(){

    }

    public void resize(int width, int height) {

    }

    public void resume(){

    }

    public void pause(){

    }

    public void dispose(){
        StartButton.dispose();
    }
}
