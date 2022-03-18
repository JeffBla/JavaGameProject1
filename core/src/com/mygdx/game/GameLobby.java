package com.mygdx.game;

import character.interActerObject.TestObject;
import character.mainCharacter.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameLobby implements Screen {
    final GameMode gameMode;
    final MainCharacter mainCharacter;
    final TestObject testObject;

    Texture test;
    OrthographicCamera camera;

    public GameLobby(final GameMode gameMode) {
        this.gameMode = gameMode;

        test = new Texture(Gdx.files.internal("wallSample.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 700);

        mainCharacter = new MainCharacter();
        testObject = new TestObject(500, 500);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.9f, 0.9f, 0.9f, 1);

        camera.update();
        gameMode.batch.setProjectionMatrix(camera.combined);

        update(delta);;

        mainCharacter.stateTime += Gdx.graphics.getDeltaTime();

        gameMode.batch.begin();
//        gameMode.bitmapFont.draw(gameMode.batch, "Test", 800-50, 350);
//        gameMode.batch.draw(test, 500, 500);
//
//        gameMode.batch.draw(test, 500, 0);
        gameMode.batch.draw(testObject.texture,
                testObject.rigid_body.x, testObject.rigid_body.y);
        /* mainCharacter must be the last, since the character should stand out */
        gameMode.batch.draw(mainCharacter.currentFrame,
                mainCharacter.rigid_body.x, mainCharacter.rigid_body.y);

        gameMode.batch.end();

    }

    public void update(float delta) {
        mainCharacter.keyInput(delta);
        if (mainCharacter.rigid_body.overlaps(testObject.rigid_body))
            testObject.rigid_body.x = testObject.rigid_body.getX() + testCollisionWidth(mainCharacter.rigid_body, testObject.rigid_body);
    }

    public float testCollisionHeight(Rectangle rect, Rectangle otherRect) {
        if (rect.y > otherRect.y)
            return ((otherRect.height + otherRect.y) - rect.y);
        else
            return -((rect.height + rect.y) - otherRect.y);
    }

    public float testCollisionWidth(Rectangle rect, Rectangle otherRect){
        if(rect.x>otherRect.x)
            return ((otherRect.width+otherRect.x)-rect.x);
        else
            return -((rect.width+rect.x)-otherRect.x);
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
