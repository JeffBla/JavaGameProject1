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

public class Level3 implements Screen {

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
    private World gameWorld3;
    private Stage gameStage3;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;
    private Texture hintTexture;
    public static boolean isTheDoorOpen = false;
    public HUD HUDBatch;
    public PausedScreen Pause;
    public GameOverScreen GameOver;
    public CompleteScreen Complete;

    public Level3(GameMode gameMode) {
        this.gameMode = gameMode;
        Level3.isTheDoorOpen = false;
        gameWorld3 = new World(new Vector2(0, 0), true);
        gameWorld3.setContactListener(new Level3ContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();
        screenMusic = new ScreenMusic();
        screenMusic.playGameLobbyMusic();

        hintTexture = new Texture(Gdx.files.internal("Level3_hint2.png"));

        mainCharacter = new MainCharacter(gameWorld3, 2, 2);

        wallObject0 = new WallObject(gameWorld3, 5, -3f, 1f, 11f,
                0f, 0, 0f);
        frameObjectUp = new WallObject(gameWorld3, 0f, 19f, 40f, 3f, 0.3f,
                0, -0.3f);
        frameObjectDownPartOne = new WallObject(gameWorld3, 0, -0.5f, 28.5f, 2f,
                1f, 0f, -1f);
        frameObjectDownPartTwo = new WallObject(gameWorld3, 34.5f, -0.5f, 5f, 2f,
                1, 0f, -1f);
        frameObjectDownDown = new WallObject(gameWorld3, 0, -1f, 40f, 1f,
                0f, 0f, 0f);
        frameObjectDownDown.setType("Bound");
        frameObjectFont = new WallObject(gameWorld3, -0.5f, -7f, 1f, 27f,
                0f, 0f, 0f);
        frameObjectRear = new WallObject(gameWorld3, 39f, -7f, 1f, 27f,
                0f, 0f, 0f);

        doorObject = new DoorObject(gameWorld3, "doorLeft.png", "doorRight.png",
                30f, 0, 3f, 1f, 2, -2,
                0, 0, 0);
        doorBlockLeft = BuildBody.createBox(gameWorld3, 28, 0, 0.5f, 0.5f,
                new Vector2(0, 0), 0, 0, 0, true, false, false);
        doorBlockRight = BuildBody.createBox(gameWorld3, 35, 0, 0.5f, 0.5f,
                new Vector2(0, 0), 0, 0, 0, true, false, false);
        doorBlockUp = BuildBody.createEdge(gameWorld3, 28.5f, 1.08f, 0, 0,
                0.1f, 0, 0, true, false, false);
        openDoorButton = new ButtonObject(gameWorld3, "gameButtonSmall.png",
                "gameButtonSmall_pressed.png",
                36, 17f, 1f, 1f, 0, 0, 0);

        Gdx.input.setInputProcessor(gameStage3);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(25, 25 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);
        HUDBatch = new HUD();
        Pause = new PausedScreen();
        GameOver = new GameOverScreen();
        Complete = new CompleteScreen();

        gameStage3 = new Stage(stageViewport);

        gameStage3.addActor(frameObjectUp);
        gameStage3.addActor(wallObject0);
        gameStage3.addActor(openDoorButton);
        gameStage3.addActor(frameObjectFont);
        gameStage3.addActor(frameObjectRear);
        gameStage3.addActor(frameObjectDownPartOne);
        gameStage3.addActor(frameObjectDownPartTwo);
        gameStage3.addActor(frameObjectDownDown);
        gameStage3.addActor(doorObject);
        gameStage3.addActor(mainCharacter);

        random = new Random();

        for (int i = 0; i <= dotInitNum; i++) {
            dotCounter = spawnDots(dotCounter);
        }

    }

    @Override
    public void render(float delta) {
        if (PausedScreen.pause) {
            screenMusic.stopGameLobbyMusic();
            gameWorld3.getContactList().clear();
            Pause.render(delta,getClass().getName());
            if (PausedScreen.restart) {
                PausedScreen.initial();
                gameMode.setScreen(new Level3(gameMode));
                dispose();
            } else if (PausedScreen.stage) {
                PausedScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            }
        } else if (GameOverScreen.gameover) {
            screenMusic.stopGameLobbyMusic();
            gameWorld3.getContactList().clear();
            GameOver.render(delta);
            if (GameOverScreen.restart) {
                GameOverScreen.initial();
                gameMode.setScreen(new Level3(gameMode));
                dispose();
            } else if (GameOverScreen.stage) {
                GameOverScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            }
        } else if (CompleteScreen.complete) {
            screenMusic.stopGameLobbyMusic();
            gameWorld3.getContactList().clear();
            Complete.render(delta);
            if (CompleteScreen.restart) {
                CompleteScreen.initial();
                gameMode.setScreen(new Level3(gameMode));
                dispose();
            } else if (CompleteScreen.stage) {
                CompleteScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            } else if (CompleteScreen.nextstage) {
                CompleteScreen.initial();
                gameMode.setScreen(new Level4(gameMode));
                dispose();
            }
        } else {
            if(PausedScreen.resume){
                screenMusic.playGameLobbyMusic();
                PausedScreen.resume=false;
            }
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

            if (gameStage3.getViewport() == mainCharacterViewport) {
                mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(6f, 1.5f));
            }
            gameStage3.getCamera().update();
            gameMode.batch.setProjectionMatrix(gameStage3.getCamera().combined);

            gameStage3.act();
            update(delta);

            gameMode.batch.begin();
            gameStage3.draw();
            gameMode.batch.draw(hintTexture, 1, 5, 6, 4);
            gameMode.batch.end();

            box2DDebugRenderer.render(gameWorld3, gameStage3.getCamera().combined);
            HUDBatch.render(delta);
        }
    }

    private void update(float delta) {
        createDotTimer += delta;
        if (dotCounter <= dotMaxNum && createDotTimer >= createDotThreshold) {
            dotCounter = spawnDots(dotCounter);
            createDotTimer = 0;
        }
        if (Level3.isTheDoorOpen) {
            doorBlockLeft.setTransform(27, 0, 0);
            doorBlockRight.setTransform(36f, 0, 0);
        }
        if (mainCharacter.getIsBound()) {
            CompleteScreen.complete = true;
        }
        gameWorld3.step(Gdx.graphics.getDeltaTime(), 6, 2);
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
        gameStage3.dispose();
        screenMusic.dispose();
        wallObject0.dispose();
        mainCharacter.dispose();
        HUD.hp = 3;
        HUDBatch.dispose();
        Pause.dispose();
        GameOver.dispose();
        Complete.dispose();
    }

    private int spawnDots(int dotNum) {
        float random_posX;
        while (true) {
            random_posX = random.nextFloat() * 38;
            if (random_posX >= 2) {
                break;
            }
        }
        float random_posY;
        while (true) {
            random_posY = random.nextFloat() * 18;
            if (random_posY >= 2) {
                break;
            }
        }
        DotObject tmpDotObject = new DotObject(gameWorld3, random_posX,
                random_posY, dotRadius, dotRadius);
        gameStage3.addActor(tmpDotObject);
        return dotNum + 1;
    }
}
