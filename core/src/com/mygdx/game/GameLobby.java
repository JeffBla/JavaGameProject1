package com.mygdx.game;

import character.interActorObject.BoxObject;
import character.interActorObject.WallObject;
import character.mainCharacter.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameLobby implements Screen {
    final GameMode gameMode;
    final ScreenMusic screenMusic;
    final MainCharacter mainCharacter;
    final WallObject wallObject0;
    final WallObject wallObject1;
    final WallObject frameObject0;
    final WallObject frameObject1;
    final WallObject frameObject2;
    final WallObject frameObject3;
    final BoxObject boxObject;

    private World gameWorld;
    private Stage gameStage;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;

    public GameLobby(final GameMode gameMode) {
        this.gameMode = gameMode;

        gameWorld = new World(new Vector2(0, 0), true);
        gameWorld.setContactListener(new GameLobbyContactListener());

        box2DDebugRenderer = new Box2DDebugRenderer();

        screenMusic = new ScreenMusic();
        screenMusic.playGameLobbyMusic();

        mainCharacter = new MainCharacter(gameWorld, 2, 2);
        wallObject0 = new WallObject(gameWorld, 8f, 8f);
        wallObject1 = new WallObject(gameWorld, 8f, -1);
        frameObject0 = new WallObject(gameWorld, 0f, 12f, 40f, 3f, 0.3f,
                0, -0.3f);
        frameObject1 = new WallObject(gameWorld, 0, -0.5f, 40f, 2f,
                0f, 0f, 0f);
        frameObject2 = new WallObject(gameWorld, -0.5f, -7f, 1f, 22f,
                0f, 0f, 0f);
        frameObject3 = new WallObject(gameWorld, 39f,-7f,1f,22f,
                0f,0f,0f);
        boxObject = new BoxObject(gameWorld, 8, 5);


        Gdx.input.setInputProcessor(gameStage);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(20, 20/ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0,0,1);
//        mainCharacterViewport.getCamera().lookAt(mainCharacter.getPosition());  /* have bug */

        gameStage = new Stage(stageViewport);

        gameStage.addActor(frameObject0);
        gameStage.addActor(wallObject1);
        gameStage.addActor(frameObject1);
        gameStage.addActor(frameObject2);
        gameStage.addActor(frameObject3);
        gameStage.addActor(boxObject);
        gameStage.addActor(wallObject0);

        gameStage.addActor(mainCharacter);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

        if(gameStage.getViewport() == mainCharacterViewport) {
                mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(4, 1.5f));
        }
        gameStage.getCamera().update();
        gameMode.batch.setProjectionMatrix(gameStage.getCamera().combined);

        gameStage.act();
        update(delta);

        gameMode.batch.begin();
        gameStage.draw();
        gameMode.batch.end();
        mainCharacter.stateTime += Gdx.graphics.getDeltaTime();

        box2DDebugRenderer.render(gameWorld, gameStage.getCamera().combined);
        gameWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);

    }

    public void update(float delta) {

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
        gameStage.dispose();
        wallObject0.dispose();
        wallObject1.dispose();
        boxObject.dispose();
        mainCharacter.dispose();
    }
}
