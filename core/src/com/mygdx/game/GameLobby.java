package com.mygdx.game;

import character.mainCharacter.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameLobby implements Screen {
    final GameMode gameMode;
    final MainCharacter mainCharacter;

    OrthographicCamera camera;

    public GameLobby(final GameMode gameMode) {
        this.gameMode = gameMode;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 700);

        mainCharacter = new MainCharacter();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 0);

        camera.update();
        gameMode.batch.setProjectionMatrix(camera.combined);

        update(delta);

        mainCharacter.stateTime += Gdx.graphics.getDeltaTime();

        gameMode.batch.begin();
//        gameMode.bitmapFont.draw(gameMode.batch, "Test", 800-50, 350);
        gameMode.batch.draw(mainCharacter.currentFrame,
                mainCharacter.rigid_body.x, mainCharacter.rigid_body.y);
        gameMode.batch.end();

    }

    public void update(float delta){
        mainCharacter.keyInput(delta);
    }

    public void show() {

    }

    public void hide() {

    }

    public void resize(int width, int height) {

    }

    public void resume() {

    }

    public void pause() {

    }

    public void dispose() {
        mainCharacter.dispose();
    }
}
