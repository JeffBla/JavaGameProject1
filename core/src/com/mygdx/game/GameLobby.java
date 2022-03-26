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
    final BoxObject boxObject;

    private World gameWorld;
    private Stage gameStage;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;
//    OrthographicCamera stageCamera;
//    OrthographicCamera mainCharacterCamera;

    public GameLobby(final GameMode gameMode) {
        this.gameMode = gameMode;

        gameWorld = new World(new Vector2(0, 0), true);
        gameWorld.setContactListener(new GameLobbyContactListener());

        box2DDebugRenderer = new Box2DDebugRenderer();

        screenMusic = new ScreenMusic();
        screenMusic.playGameLobbyMusic();

        mainCharacter = new MainCharacter(gameWorld, 0, 0);
        wallObject0 = new WallObject(gameWorld, 5f, 6.5f);
        wallObject1 = new WallObject(gameWorld, 5f, 0);
        boxObject = new BoxObject(gameWorld, 8, 5);


        Gdx.input.setInputProcessor(gameStage);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        stageViewport = new FitViewport(1000, 1000 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(20, 20/ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0,0,1f);
        mainCharacterViewport.getCamera().lookAt(mainCharacter.getPosition());

        gameStage = new Stage(mainCharacterViewport);

        gameStage.addActor(mainCharacter);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);

        if(gameStage.getViewport() == mainCharacterViewport) {
            mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(4, 1.5f));
        }
        gameStage.getCamera().update();
        gameMode.batch.setProjectionMatrix(gameStage.getCamera().combined);

        gameStage.act();
        boxObject.act(delta);
        update(delta);

        gameMode.batch.begin();
        wallObject0.draw(gameMode.batch);
        boxObject.draw(gameMode.batch);
        wallObject1.draw(gameMode.batch);

        mainCharacter.draw(gameMode.batch);
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
