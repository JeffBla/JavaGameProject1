package com.mygdx.game;

import character.enemy.robot.Enemy_robot;
import character.interActorObject.BoxObject;
import character.interActorObject.ButtonObject;
import character.interActorObject.DoorObject;
import character.interActorObject.WallObject;
import character.mainCharacter.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import worldBuilding.BuildBody;

public class GameLobby implements Screen {
    final GameMode gameMode;
    final ScreenMusic screenMusic;
    final WallObject wallObject0;
    final WallObject wallObject1;
    final WallObject frameObjectUp;
    final WallObject frameObjectDownPartOne;
    final WallObject frameObjectDownPartTwo;
    final WallObject frameObjectDownDown;
    final WallObject frameObjectFont;
    final WallObject frameObjectRear;
    final BoxObject boxObject;
    final ButtonObject openDoorButton;
    final DoorObject doorObject;
    final Body doorBlockLeft;
    final Body doorBlockRight;


    final MainCharacter mainCharacter;
    final Enemy_robot enemy_robot;

    private World gameWorld;
    private Stage gameStage;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;

    public static boolean isTheDoorOpen =false;

    public GameLobby(final GameMode gameMode) {
        this.gameMode = gameMode;

        gameWorld = new World(new Vector2(0, 0), true);
        gameWorld.setContactListener(new GameLobbyContactListener());

        box2DDebugRenderer = new Box2DDebugRenderer();

        screenMusic = new ScreenMusic();
        screenMusic.playGameLobbyMusic();

        mainCharacter = new MainCharacter(gameWorld, 2, 2);
        enemy_robot =new Enemy_robot(gameWorld, 10f,5f);

        wallObject0 = new WallObject(gameWorld, 8f, 8f);
        wallObject1 = new WallObject(gameWorld, 8f, -1);
        frameObjectUp = new WallObject(gameWorld, 0f, 12f, 40f, 3f, 0.3f,
                0, -0.3f);
        frameObjectDownPartOne = new WallObject(gameWorld, 0, -0.5f, 28.5f, 2f,
                1f, 0f, -1f);
        frameObjectDownPartTwo = new WallObject(gameWorld, 34.5f, -0.5f, 5f, 2f,
                1, 0f, -1f);
        frameObjectDownDown = new WallObject(gameWorld, 0, -1f, 40f, 1f,
                0f, 0f, 0f);
        frameObjectFont = new WallObject(gameWorld, -0.5f, -7f, 1f, 22f,
                0f, 0f, 0f);
        frameObjectRear = new WallObject(gameWorld, 39f,-7f,1f,22f,
                0f,0f,0f);
        frameObjectRear.setTrigger(gameWorld,
                0,0,0);

        boxObject = new BoxObject(gameWorld, 8, 5);

        doorObject = new DoorObject(gameWorld, "doorLeft.png", "doorRight.png",
                30f, 0, 3f,1f,2,-2,
                0,0,0);
        openDoorButton= new ButtonObject(gameWorld, "gameButtonSmall.png",
                "gameButtonSmall_pressed.png",
                30, 10, 1f, 1f, 0,0,0);

        doorBlockLeft =BuildBody.createBox(gameWorld, 28, 0, 0.5f,0.5f,
                new Vector2(0,0),0,0,0,true,false,false);
        doorBlockRight=BuildBody.createBox(gameWorld, 35, 0, 0.5f,0.5f,
                new Vector2(0,0),0,0,0,true,false,false);

        Gdx.input.setInputProcessor(gameStage);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(25, 25/ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0,0,1);

        gameStage = new Stage(stageViewport);

        gameStage.addActor(frameObjectUp);
        gameStage.addActor(wallObject0);
        gameStage.addActor(openDoorButton);
        gameStage.addActor(boxObject);
        gameStage.addActor(frameObjectFont);
        gameStage.addActor(frameObjectRear);
        gameStage.addActor(frameObjectDownPartOne);
        gameStage.addActor(frameObjectDownPartTwo);
        gameStage.addActor(frameObjectDownDown);
        gameStage.addActor(wallObject1);
        gameStage.addActor(doorObject);
        gameStage.addActor(enemy_robot);
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

        box2DDebugRenderer.render(gameWorld, gameStage.getCamera().combined);
        gameWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);

    }

    public void update(float delta) {
        if(isTheDoorOpen){
            doorBlockLeft.setTransform(27, 0, 0);
            doorBlockRight.setTransform(36f, 0, 0);
        }
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
