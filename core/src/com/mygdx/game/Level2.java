package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import character.enemy.robot.Enemy_robot;
import character.interActorObject.ButtonObject;
import character.interActorObject.DoorObject;
import character.interActorObject.WallObject;
import character.interActorObject.Laser.Laser;
import character.mainCharacter.MainCharacter;
import worldBuilding.BuildBody;

public class Level2 implements Screen {

    final GameMode gameMode;
    final ScreenMusic screenMusic;
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
    final Laser laser1;
    final Laser laser2;
    final Laser laser3;
    final Laser laser4;
    final Laser laser5;
    final MainCharacter mainCharacter;
    final Enemy_robot enemy_robot1;
    final Enemy_robot enemy_robot2;
    private World gameWorld2;
    private Stage gameStage2;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;
    public static boolean isTheDoorOpen = false;
    public HUD HUDBatch;
    public PausedScreen Pause;
    public GameOverScreen GameOver;
    public CompleteScreen Complete;

    public Level2(GameMode gameMode) {
        this.gameMode = gameMode;
        Level2.isTheDoorOpen = false;
        gameWorld2 = new World(new Vector2(0, 0), true);
        gameWorld2.setContactListener(new Level2ContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();
        screenMusic = new ScreenMusic();
        screenMusic.playGameLobbyMusic();

        mainCharacter = new MainCharacter(gameWorld2, 2, 2);
        enemy_robot1 = new Enemy_robot(gameWorld2, 10f, 2f, 4, 0);
        enemy_robot2 = new Enemy_robot(gameWorld2, 20f, 8f, 4, 0);
        wallObject1 = new WallObject(gameWorld2, 5, -3f, 1f, 11f,
                0f, 0, 0f);
        wallObject2 = new WallObject(gameWorld2, 15, 2f, 1f, 11f,
                3f, 0, 0f);
        frameObjectUp = new WallObject(gameWorld2, 0f, 12f, 40f, 3f, 0.3f,
                0, -0.3f);
        frameObjectDownPartOne = new WallObject(gameWorld2, 0, -0.5f, 28.5f, 2f,
                1f, 0f, -1f);
        frameObjectDownPartTwo = new WallObject(gameWorld2, 34.5f, -0.5f, 5f, 2f,
                1, 0f, -1f);
        frameObjectDownDown = new WallObject(gameWorld2, 0, -1f, 40f, 1f,
                0f, 0f, 0f);
        frameObjectDownDown.setType("Bound");
        frameObjectFont = new WallObject(gameWorld2, -0.5f, -7f, 1f, 22f,
                0f, 0f, 0f);
        frameObjectRear = new WallObject(gameWorld2, 39f, -7f, 1f, 22f,
                0f, 0f, 0f);

        doorObject = new DoorObject(gameWorld2, "doorLeft.png", "doorRight.png",
                30f, 0, 3f, 1f, 2, -2,
                0, 0, 0);
        doorBlockLeft = BuildBody.createBox(gameWorld2, 28, 0, 0.5f, 0.5f,
                new Vector2(0, 0), 0, 0, 0, true, false, false);
        doorBlockRight = BuildBody.createBox(gameWorld2, 35, 0, 0.5f, 0.5f,
                new Vector2(0, 0), 0, 0, 0, true, false, false);
        openDoorButton = new ButtonObject(gameWorld2, "gameButtonSmall.png",
                "gameButtonSmall_pressed.png",
                36, 9f, 1f, 1f, 0, 0, 0);
        laser1 = new Laser(gameWorld2, "laser/lineLeri.png", "laser/baseLeri.png", "leri", true, 6.5f, 6.6f, 8.5f, 1.3f, 0.0811227f, 0, 0.03f, 0, 0f, -0.35f, -0.85f, 0.15f, 1.2f, 1.1f);
        laser2 = new Laser(gameWorld2, "laser/lineUpdo.png", "laser/baseUpdo.png", "updo", true, 5.0f, 8.05f, 1.0f, 4.05f, 0.0811227f, 0, 0f, 0, -0.25f, 0f, -0.05f, 3.8f, 1.1f, 1.2f);
        laser3 = new Laser(gameWorld2, "laser/lineDoup2.png", "laser/baseDoup.png", "doup", false, 17f, 2f, 1.3f, 8.5f, 0.1f, 0.04f, 0, 0, -0.35f, 0f, 0.05f, -0.8f, 1.2f, 1.2f);
        laser4 = new Laser(gameWorld2, "laser/lineUpdo2.png", "laser/baseUpdo.png", "updo", false, 30f, 3.75f, 1.2f, 8.5f, 0.1f, 0.02f, 0, 0, -0.3f, 0f, 0f, 8.1f, 1.2f, 1.2f);
        laser5 = new Laser(gameWorld2, "laser/lineRile2.png", "laser/baseRile.png", "rile", false, 34.3f, 3f, 4.2f, 1f, 0.1f, 0f, 0.04f, 0, 0f, -0.3f, 3.8f, 0f, 1.2f, 1f);

        Gdx.input.setInputProcessor(gameStage2);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(25, 25 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);
        HUDBatch =new HUD();
        Pause=new PausedScreen();
        GameOver=new GameOverScreen();
        Complete=new CompleteScreen();

        gameStage2 = new Stage(mainCharacterViewport);
        gameStage2.addActor(frameObjectUp);
        gameStage2.addActor(wallObject1);
        gameStage2.addActor(wallObject2);
        gameStage2.addActor(openDoorButton);
        gameStage2.addActor(frameObjectFont);
        gameStage2.addActor(frameObjectRear);
        gameStage2.addActor(frameObjectDownPartOne);
        gameStage2.addActor(frameObjectDownPartTwo);
        gameStage2.addActor(frameObjectDownDown);
        gameStage2.addActor(doorObject);
        gameStage2.addActor(enemy_robot1);
        gameStage2.addActor(enemy_robot2);
        gameStage2.addActor(mainCharacter);
        gameStage2.addActor(laser1);
        gameStage2.addActor(laser2);
        gameStage2.addActor(laser3);
        gameStage2.addActor(laser4);
        gameStage2.addActor(laser5);
    }

    @Override
    public void render(float delta) {
        if (PausedScreen.pause) {
            screenMusic.stopGameLobbyMusic();
            gameWorld2.getContactList().clear();
            Pause.render(delta,getClass().getName());
            if (PausedScreen.restart) {
                PausedScreen.initial();
                gameMode.setScreen(new Level2(gameMode));
                dispose();
            } else if (PausedScreen.stage) {
                PausedScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            }
        } else if (GameOverScreen.gameover) {
            screenMusic.stopGameLobbyMusic();
            gameWorld2.getContactList().clear();
            GameOver.render(delta);
            if (GameOverScreen.restart) {
                GameOverScreen.initial();
                gameMode.setScreen(new Level2(gameMode));
                dispose();
            } else if (GameOver.stage) {
                GameOverScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            }
        } else if (CompleteScreen.complete) {
            screenMusic.stopGameLobbyMusic();
            gameWorld2.getContactList().clear();
            Complete.render(delta);
            if (CompleteScreen.restart) {
                CompleteScreen.initial();
                gameMode.setScreen(new Level2(gameMode));
                dispose();
            } else if (CompleteScreen.stage) {
                CompleteScreen.initial();
                gameMode.setScreen(new Stageselection(gameMode));
                dispose();
            } else if (CompleteScreen.nextstage) {
                CompleteScreen.initial();
                gameMode.setScreen(new Level3(gameMode));
                dispose();
            }
        } else {
            if(PausedScreen.resume){
                screenMusic.playGameLobbyMusic();
                PausedScreen.resume=false;
            }
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

            if (gameStage2.getViewport() == mainCharacterViewport) {
                mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(4, 1.5f));
            }
            gameStage2.getCamera().update();
            gameMode.batch.setProjectionMatrix(gameStage2.getCamera().combined);

            gameStage2.act();
            update(delta);

            gameMode.batch.begin();
            gameStage2.draw();
            gameMode.batch.end();

            laser1.moveY(4.45f, 6.6f);
            if (TimeUtils.nanoTime() - laser1.getStart() > 1210000000f) {
                laser1.setStartTime();
                if (laser1.getAttack()) {
                    laser1.setAttack(false);
                    laser1.getLine().setVisible(false);
                    laser1.getLine().sleep();
                }
                else {
                    laser1.setAttack(true);
                    laser1.getLine().setVisible(true);
                    laser1.getLine().awake();
                }
            }
            if (TimeUtils.nanoTime() - laser2.getStart() > 1210000000f) {
                laser2.setStartTime();
                if (laser2.getAttack()) {
                    laser2.setAttack(false);
                    laser2.getLine().setVisible(false);
                    laser2.getLine().sleep();
                }
                else {
                    laser2.setAttack(true);
                    laser2.getLine().setVisible(true);
                    laser2.getLine().awake();
                }
            }

            laser3.moveX(17f, 23f);
            laser4.moveX(30f, 34f);
            laser5.moveY(3f, 7.5f);
            box2DDebugRenderer.render(gameWorld2, gameStage2.getCamera().combined);
            HUDBatch.render(delta);
        }
    }

    private void update(float delta) {
        if (Level2.isTheDoorOpen) {
            doorBlockLeft.setTransform(27, 0, 0);
            doorBlockRight.setTransform(36f, 0, 0);
        }
        if (mainCharacter.getIsBound()) {
            CompleteScreen.complete=true;
            HUD.hp=3;
        }
        gameWorld2.step(Gdx.graphics.getDeltaTime(), 6, 2);
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
        isTheDoorOpen=false;
        gameStage2.dispose();
        screenMusic.dispose();
        wallObject1.dispose();
        wallObject2.dispose();
        mainCharacter.dispose();
        laser1.dispose();
        laser2.dispose();
        laser3.dispose();
        laser4.dispose();
        laser5.dispose();
        frameObjectUp.dispose();
        frameObjectDownPartOne.dispose();
        frameObjectDownPartTwo.dispose();
        frameObjectDownDown.dispose();
        frameObjectFont.dispose();
        frameObjectRear.dispose();
        openDoorButton.dispose();
        doorObject.dispose();
        enemy_robot1.dispose();
        enemy_robot2.dispose();
        HUD.hp=3;
        HUDBatch.dispose();
        Pause.dispose();
        GameOver.dispose();
        Complete.dispose();
    }
}
