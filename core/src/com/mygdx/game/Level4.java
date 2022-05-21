package com.mygdx.game;

import character.interActorObject.ButtonObject;
import character.interActorObject.DoorObject;
import character.interActorObject.DotObject;
import character.interActorObject.WallObject;
import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import worldBuilding.BuildBody;

import java.util.Random;

public class Level4 implements Screen {

    final GameMode gameMode;
    final ScreenMusic screenMusic;
    final WallObject wallObject0;
    final WallObject frameObjectUp;
    final WallObject frameObjectDownPartOne;
    final WallObject frameObjectDownPartTwo;
    final WallObject frameObjectDownDown;
    final WallObject frameObjectFont;
    final WallObject frameObjectRear;
    final ButtonObject openDoorButton;
    final DoorObject doorObject;
    final Body doorBlockLeft;
    final Body doorBlockRight;
    private final Body doorBlockUp;

    private float createDotTimer;
    private final float createDotThreshold = 3.0f;
    private final int dotInitNum = 25;
    private final int dotMaxNum = 40;
    private int dotCounter = 0;
    private final float dotRadius = 1;
    private Random random;

    final MainCharacter mainCharacter;
    private World gameWorld4;
    private Stage gameStage4;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;
    public static boolean isTheDoorOpen = false;

    public Level4(GameMode gameMode) {
        this.gameMode = gameMode;
        Level4.isTheDoorOpen = false;
        gameWorld4 = new World(new Vector2(0, 0), true);
        gameWorld4.setContactListener(new Level4ContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();
        screenMusic = new ScreenMusic();
        screenMusic.playGameLobbyMusic();

        mainCharacter = new MainCharacter(gameWorld4, 2, 2);

        wallObject0 = new WallObject(gameWorld4, 5, -3f, 1f, 11f,
                0f, 0, 0f);
        frameObjectUp = new WallObject(gameWorld4, 0f, 19f, 40f, 3f, 0.3f,
                0, -0.3f);
        frameObjectDownPartOne = new WallObject(gameWorld4, 0, -0.5f, 28.5f, 2f,
                1f, 0f, -1f);
        frameObjectDownPartTwo = new WallObject(gameWorld4, 34.5f, -0.5f, 5f, 2f,
                1, 0f, -1f);
        frameObjectDownDown = new WallObject(gameWorld4, 0, -1f, 40f, 1f,
                0f, 0f, 0f);
        frameObjectDownDown.setType("Bound");
        frameObjectDownDown.setTrigger(gameWorld4, 0, 0, 0);
        frameObjectFont = new WallObject(gameWorld4, -0.5f, -7f, 1f, 27f,
                0f, 0f, 0f);
        frameObjectRear = new WallObject(gameWorld4, 39f, -7f, 1f, 27f,
                0f, 0f, 0f);

        doorObject = new DoorObject(gameWorld4, "doorLeft.png", "doorRight.png",
                30f, 0, 3f, 1f, 2, -2,
                0, 0, 0);
        doorBlockLeft = BuildBody.createBox(gameWorld4, 28, 0, 0.5f, 0.5f,
                new Vector2(0, 0), 0, 0, 0, true, false, false);
        doorBlockRight = BuildBody.createBox(gameWorld4, 35, 0, 0.5f, 0.5f,
                new Vector2(0, 0), 0, 0, 0, true, false, false);
        doorBlockUp = BuildBody.createEdge(gameWorld4, 28.5f, 1.08f, 0, 0,
                0.1f, 0, 0, true, false, false);
        openDoorButton = new ButtonObject(gameWorld4, "gameButtonSmall.png",
                "gameButtonSmall_pressed.png",
                36, 17f, 1f, 1f, 0, 0, 0);

        Gdx.input.setInputProcessor(gameStage4);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(25, 25 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);

        gameStage4 = new Stage(stageViewport);

        gameStage4.addActor(frameObjectUp);
        gameStage4.addActor(wallObject0);
        gameStage4.addActor(openDoorButton);
        gameStage4.addActor(frameObjectFont);
        gameStage4.addActor(frameObjectRear);
        gameStage4.addActor(frameObjectDownPartOne);
        gameStage4.addActor(frameObjectDownPartTwo);
        gameStage4.addActor(frameObjectDownDown);
        gameStage4.addActor(doorObject);
        gameStage4.addActor(mainCharacter);

        random = new Random();

        for (int i = 0; i <= dotInitNum; i++) {
            dotCounter= spawnDots(dotCounter);
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

        if (gameStage4.getViewport() == mainCharacterViewport) {
            mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(6f, 1.5f));
        }
        gameStage4.getCamera().update();
        gameMode.batch.setProjectionMatrix(gameStage4.getCamera().combined);

        gameStage4.act();
        update(delta);

        gameMode.batch.begin();
        gameStage4.draw();
        gameMode.batch.end();

        box2DDebugRenderer.render(gameWorld4, gameStage4.getCamera().combined);
        gameWorld4.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    private void update(float delta) {
        createDotTimer += delta;
        if (dotCounter <= dotMaxNum && createDotTimer >= createDotThreshold) {
            dotCounter= spawnDots(dotCounter);
            createDotTimer = 0;
        }
        if (Level4.isTheDoorOpen) {
            doorBlockLeft.setTransform(27, 0, 0);
            doorBlockRight.setTransform(36f, 0, 0);
        }
        if (mainCharacter.getIsBound()) {
            gameMode.setScreen(new Stageselection(gameMode));
            dispose();
        }
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
        gameStage4.dispose();
        screenMusic.dispose();
        wallObject0.dispose();
        mainCharacter.dispose();
    }

    private int spawnDots(int dotNum){
        DotObject tmpDotObject = new DotObject(gameWorld4, random.nextFloat(2, 38),
                random.nextFloat(2, 18), dotRadius, dotRadius);
        gameStage4.addActor(tmpDotObject);
        return dotNum+1;
    }
}
