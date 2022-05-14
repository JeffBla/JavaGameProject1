package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level2 implements Screen {

    private final GameMode gameMode;
    private final World gameWorld2;
    private final Stage gameStage2;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;

    private final Box2DDebugRenderer box2DDebugRenderer;

    float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

    public Level2(GameMode gameMode) {
        this.gameMode = gameMode;

        gameWorld2 = new World(new Vector2(0, 0), true);

        box2DDebugRenderer = new Box2DDebugRenderer();

        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(25, 25 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);

        gameStage2 = new Stage(stageViewport);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

//        if(gameStage2.getViewport() == mainCharacterViewport) {
//            mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(4, 1.5f));
//        }
        gameStage2.getCamera().update();
        gameMode.batch.setProjectionMatrix(gameStage2.getCamera().combined);

        gameStage2.act();
        update(delta);

        gameMode.batch.begin();
        gameStage2.draw();
        gameMode.batch.end();

        box2DDebugRenderer.render(gameWorld2, gameStage2.getCamera().combined);
        gameWorld2.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    private void update(float delta) {

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
