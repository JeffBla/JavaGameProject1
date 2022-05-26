package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import character.enemy.robot.Enemy_robot;
import character.interActorObject.ButtonObject;
import character.interActorObject.DoorObject;
import character.interActorObject.WallObject;
import character.interActorObject.Laser.LaserObjectBase;
import character.interActorObject.Laser.LaserObjectLine;
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
    final LaserObjectLine laserline1;
    final LaserObjectBase laserbase1;
    final LaserObjectLine laserline2;
    final LaserObjectBase laserbase2;
    final LaserObjectLine laserline3;
    final LaserObjectBase laserbase3;
    final LaserObjectLine laserline4;
    final LaserObjectBase laserbase4;
    final LaserObjectLine laserline5;
    final LaserObjectBase laserbase5;

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

        laserline1 = new LaserObjectLine(gameWorld2,"laser/laser_leri.png","leri", true , 6.5f, 6.6f, 8.5f, 1.3f, 0.0811227f, 0, 0.03f, 0, 0f, -0.35f);
        laserbase1 = new LaserObjectBase(laserline1.get_body(),"laser/leri.png","leri", 1.2f, 1.1f, -0.85f, 0.15f);
        laserline2 = new LaserObjectLine(gameWorld2,"laser/laser_updo.png","updo", true , 5.0f, 8.05f, 1.0f, 4.05f, 0.0811227f, 0, 0f, 0, -0.25f, 0f);
        laserbase2 = new LaserObjectBase(laserline2.get_body(),"laser/updo.png","updo", 1.1f, 1.2f, -0.05f, 3.8f);
        laserline3 = new LaserObjectLine(gameWorld2,"laser/laser_doup_2.png","doup", false , 17f, 2f, 1.3f, 8.5f, 0.1f, 0.04f, 0, 0, -0.35f, 0f);
        laserbase3 = new LaserObjectBase(laserline3.get_body(),"laser/doup.png","doup", 1.2f, 1.2f, 0.05f, -0.8f);
        laserline4 = new LaserObjectLine(gameWorld2,"laser/laser_updo_2.png","updo", false , 30f, 3.75f, 1.2f, 8.5f, 0.1f, 0.02f, 0, 0, -0.3f, 0f);
        laserbase4 = new LaserObjectBase(laserline4.get_body(),"laser/updo.png","updo", 1.2f, 1.2f, 0f, 8.1f);
        laserline5 = new LaserObjectLine(gameWorld2,"laser/laser_rile_2.png","rile", false , 34.3f, 3f, 4.2f, 1f, 0.1f, 0f, 0.04f, 0, 0f, -0.3f);
        laserbase5 = new LaserObjectBase(laserline5.get_body(),"laser/rile.png","rile", 1.2f, 1f, 3.8f, 0f);

        Gdx.input.setInputProcessor(gameStage2);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(25, 25 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);
        HUDBatch =new HUD();

        gameStage2 = new Stage(stageViewport);
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

        gameStage2.addActor(laserbase1);
        gameStage2.addActor(laserline1);
        gameStage2.addActor(laserbase2);
        gameStage2.addActor(laserline2);
        gameStage2.addActor(laserbase3);
        gameStage2.addActor(laserline3);
        gameStage2.addActor(laserbase4);
        gameStage2.addActor(laserline4);
        gameStage2.addActor(laserbase5);
        gameStage2.addActor(laserline5);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

        if(gameStage2.getViewport() == mainCharacterViewport) {
            mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(4, 1.5f));
        }
        gameStage2.getCamera().update();
        gameMode.batch.setProjectionMatrix(gameStage2.getCamera().combined);

        gameStage2.act();
        update(delta);

        gameMode.batch.begin();
        gameStage2.draw();
        gameMode.batch.end();

        laserline1.move_Y(4.45f,6.6f);
        if (TimeUtils.nanoTime() - laserline1.get_start() > 1210000000f) {

            laserline1.set_startTime();
            if(laserline1.isVisible()) {
                laserline1.setVisible(false);
                laserline1.sleep();
            }
            else {
                laserline1.setVisible(true);
                laserline1.awake();
            }
        }
        if (TimeUtils.nanoTime() - laserline2.get_start() > 1210000000f) {
            laserline2.set_startTime();
            if(laserline2.isVisible()) {
                laserline2.setVisible(false);
                laserline2.sleep();
            }
            else {
                laserline2.setVisible(true);
                laserline2.awake();
            }
        }
        laserline3.move_X(17f,23f);
        laserline4.move_X(30f, 34f);
        laserline5.move_Y(3f, 7.5f);
        box2DDebugRenderer.render(gameWorld2, gameStage2.getCamera().combined);
        gameWorld2.step(Gdx.graphics.getDeltaTime(), 6, 2);
        HUDBatch.render(delta);
    }

    private void update(float delta) {
        if (Level2.isTheDoorOpen) {
            doorBlockLeft.setTransform(27, 0, 0);
            doorBlockRight.setTransform(36f, 0, 0);
        }
        if (mainCharacter.getIsBound()) {
            gameMode.setScreen(new Stageselection(gameMode));
            HUD.hp=3;
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
        isTheDoorOpen=false;
        gameStage2.dispose();
        screenMusic.dispose();
        wallObject1.dispose();
        wallObject2.dispose();
        mainCharacter.dispose();
        laserline1.dispose();
        laserbase1.dispose();
        laserline2.dispose();
        laserbase2.dispose();
        laserline3.dispose();
        laserbase3.dispose();
        laserline4.dispose();
        laserbase4.dispose();
        laserline5.dispose();
        laserbase5.dispose();
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
    }
}
