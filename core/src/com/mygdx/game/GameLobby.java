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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    final WallObject wallObject2;
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
    final Body doorBlockUp;

    final MainCharacter mainCharacter;
    final Enemy_robot enemy_robot1;
    final Enemy_robot enemy_robot2;
    final Enemy_robot enemy_robot3;

    private World gameWorld;
    private Stage gameStage;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;
    private Texture keyMapTutorial;

    public HUD HUDBatch;
    public PausedScreen Pause;
    public GameOverScreen GameOver;
    public CompleteScreen Complete;
    public static boolean isTheDoorOpen = false;

    public GameLobby(final GameMode gameMode) {
        this.gameMode = gameMode;

        gameWorld = new World(new Vector2(0, 0), true);
        gameWorld.setContactListener(new GameLobbyContactListener());

        box2DDebugRenderer = new Box2DDebugRenderer();

        keyMapTutorial = new Texture(Gdx.files.internal("keyMapToturial.png"));

        screenMusic = new ScreenMusic();
        screenMusic.playGameLobbyMusic();

        mainCharacter = new MainCharacter(gameWorld, 2, 2);
        enemy_robot1 = new Enemy_robot(gameWorld, 10f, 2f, 4, 0);
        enemy_robot2 = new Enemy_robot(gameWorld, 25, 5, 0, -2);
        enemy_robot3 = new Enemy_robot(gameWorld, 13, 3.5f, 2, 2);

        wallObject0 = new WallObject(gameWorld, 8f, 8f);
        wallObject1 = new WallObject(gameWorld, 8f, -1);
        wallObject2 = new WallObject(gameWorld, 33, 7, 6f, 2.5f,
                0.2f, 0, -0.2f);
        frameObjectUp = new WallObject(gameWorld, 0f, 12f, 40f, 3f, 0.3f,
                0, -0.3f);
        frameObjectDownPartOne = new WallObject(gameWorld, 0, -0.5f, 28.5f, 2f,
                1f, 0f, -1f);
        frameObjectDownPartTwo = new WallObject(gameWorld, 34.5f, -0.5f, 5f, 2f,
                1, 0f, -1f);
        frameObjectDownDown = new WallObject(gameWorld, 0, -1f, 40f, 1f,
                0f, 0f, 0f);
        frameObjectDownDown.setType("Bound");
        frameObjectFont = new WallObject(gameWorld, -0.5f, -7f, 1f, 22f,
                0f, 0f, 0f);
        frameObjectRear = new WallObject(gameWorld, 39f, -7f, 1f, 22f,
                0f, 0f, 0f);

        doorObject = new DoorObject(gameWorld, "doorLeft.png", "doorRight.png",
                30f, 0, 3f, 1f, 2, -2,
                0, 0, 0);
        openDoorButton = new ButtonObject(gameWorld, "gameButtonSmall.png",
                "gameButtonSmall_pressed.png",
                37, 10, 1f, 1f, 0, 0, 0);

        doorBlockLeft = BuildBody.createBox(gameWorld, 28, 0, 0.5f, 0.5f,
                new Vector2(0, 0), 0, 0, 0, true, false, false);
        doorBlockRight = BuildBody.createBox(gameWorld, 35, 0, 0.5f, 0.5f,
                new Vector2(0, 0), 0, 0, 0, true, false, false);
        doorBlockUp = BuildBody.createEdge(gameWorld, 28.5f, 1.08f, 0, 0,
                0.1f, 0, 0, true, false, false);

        Gdx.input.setInputProcessor(gameStage);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());


        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(25, 25 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);
        HUDBatch =new HUD();
        Pause=new PausedScreen();
        GameOver=new GameOverScreen();
        Complete=new CompleteScreen();

        gameStage = new Stage(mainCharacterViewport);

        gameStage.addActor(frameObjectUp);
        gameStage.addActor(wallObject0);
        gameStage.addActor(wallObject2);
        gameStage.addActor(openDoorButton);
        gameStage.addActor(frameObjectFont);
        gameStage.addActor(frameObjectRear);
        gameStage.addActor(frameObjectDownPartOne);
        gameStage.addActor(frameObjectDownPartTwo);
        gameStage.addActor(frameObjectDownDown);
        gameStage.addActor(wallObject1);
        gameStage.addActor(doorObject);
        gameStage.addActor(enemy_robot1);
        gameStage.addActor(enemy_robot2);
        gameStage.addActor(enemy_robot3);
        gameStage.addActor(mainCharacter);

    }

    @Override
    public void render(float delta) {

        if(PausedScreen.pause) {
            screenMusic.stopGameLobbyMusic();
            gameWorld.getContactList().clear();
            Pause.render(delta,getClass().getName());
            if(PausedScreen.restart) {
                PausedScreen.initial();
                gameMode.setScreen(new GameLobby(gameMode));
                dispose();
            }
            else if(PausedScreen.stage) {
                PausedScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            }
        }
        else if(GameOverScreen.gameover) {
            screenMusic.stopGameLobbyMusic();
            gameWorld.getContactList().clear();
            GameOver.render(delta);
            if(GameOverScreen.restart) {
                GameOverScreen.initial();
                gameMode.setScreen(new GameLobby(gameMode));
                dispose();
            }
            else if(GameOver.stage) {
                GameOverScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            }
        }
        else if(CompleteScreen.complete) {
            screenMusic.stopGameLobbyMusic();
            gameWorld.getContactList().clear();
            Complete.render(delta);
            if(CompleteScreen.restart) {
                CompleteScreen.initial();
                gameMode.setScreen(new GameLobby(gameMode));
                dispose();
            }
            else if(CompleteScreen.stage) {
                CompleteScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            }
            else if(CompleteScreen.nextstage) {
                CompleteScreen.initial();
                gameMode.setScreen(new Level2(gameMode));
                dispose();
            }
        }
        else {
            if(PausedScreen.resume){
            screenMusic.playGameLobbyMusic();
            PausedScreen.resume=false;
            }
            gameWorld.getContactList().clear();
            gameWorld.setContactListener(new GameLobbyContactListener());
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

            if (gameStage.getViewport() == mainCharacterViewport) {
                mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(4, 1.5f));
            }
            gameStage.getCamera().update();
            gameMode.batch.setProjectionMatrix(gameStage.getCamera().combined);

            gameStage.act();
            update(delta);

            gameMode.batch.begin();
            gameStage.draw();
            gameMode.batch.draw(keyMapTutorial, 1, 5, 6, 4);
            gameMode.batch.end();

            box2DDebugRenderer.render(gameWorld, gameStage.getCamera().combined);

            HUDBatch.render(delta);
        }
        }

    public void update(float delta) {
        if (isTheDoorOpen) {
            doorBlockLeft.setTransform(27, 0, 0);
            doorBlockRight.setTransform(36f, 0, 0);
        }
        if (mainCharacter.getIsBound()) {
            CompleteScreen.complete=true;
        }
        gameWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);
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
        isTheDoorOpen=false;
        gameStage.dispose();
        screenMusic.dispose();
        frameObjectFont.dispose();
        frameObjectDownDown.dispose();
        frameObjectDownPartTwo.dispose();
        frameObjectDownPartOne.dispose();
        frameObjectUp.dispose();
        frameObjectRear.dispose();
        wallObject0.dispose();
        wallObject1.dispose();
        doorObject.dispose();
        openDoorButton.dispose();
        keyMapTutorial.dispose();
        enemy_robot1.dispose();
        enemy_robot2.dispose();
        enemy_robot3.dispose();
        mainCharacter.dispose();

        HUDBatch.dispose();
        HUD.hp=3;
        Pause.dispose();
        GameOver.dispose();
        Complete.dispose();
    }
}
