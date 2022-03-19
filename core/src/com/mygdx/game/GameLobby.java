package com.mygdx.game;

import character.interActerObject.TestObject;
import character.mainCharacter.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameLobby implements Screen {
    final GameMode gameMode;
    final MainCharacter mainCharacter;
    final TestObject testObject;

    World gameWorld;
    Stage gameStage;
    Box2DDebugRenderer box2DDebugRenderer;
    OrthographicCamera camera;
//    OrthographicCamera mainCharacterCamera;

    public GameLobby(final GameMode gameMode) {
        this.gameMode = gameMode;

        gameWorld = new World(new Vector2(0, 0), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(gameStage);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        camera =new OrthographicCamera();
        camera.setToOrtho(false, 2300, 2300 / ratio);

        FitViewport fitViewport = new FitViewport(2300, 2300 / ratio, camera);
        gameStage = new Stage(fitViewport);

//        test = new Texture(Gdx.files.internal("wallSample.png"));

        mainCharacter = new MainCharacter(gameWorld, 0, 0);
        testObject = new TestObject(gameWorld, 500, 500);


        gameStage.addActor(mainCharacter);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);

        gameStage.act();
        gameStage.getCamera().update();
        gameMode.batch.setProjectionMatrix(gameStage.getCamera().combined);

        update(delta);

        gameMode.batch.begin();
        testObject.draw(gameMode.batch);
        mainCharacter.draw(gameMode.batch);
        gameMode.batch.end();

        mainCharacter.stateTime += Gdx.graphics.getDeltaTime();

        box2DDebugRenderer.render(gameWorld, gameStage.getCamera().combined);
        gameWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);

    }

    public void update(float delta) {
//        if (mainCharacter.rigid_body.overlaps(testObject.rigid_body))
//            testObject.rigid_body.x = testObject.rigid_body.getX() + testCollisionWidth(mainCharacter.rigid_body, testObject.rigid_body);
    }

//    public float testCollisionHeight(Rectangle rect, Rectangle otherRect) {
//        if (rect.y > otherRect.y)
//            return ((otherRect.height + otherRect.y) - rect.y);
//        else
//            return -((rect.height + rect.y) - otherRect.y);
//    }
//
//    public float testCollisionWidth(Rectangle rect, Rectangle otherRect) {
//        if (rect.x > otherRect.x)
//            return ((otherRect.width + otherRect.x) - rect.x);
//        else
//            return -((rect.width + rect.x) - otherRect.x);
//    }

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
        gameStage.dispose();
        mainCharacter.dispose();
    }
}
