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
import character.interActorObject.BoxObject;
import character.interActorObject.ButtonObject;
import character.interActorObject.DoorObject;
import character.interActorObject.WallObject;
import character.interActorObject.Laser.LaserObject;
import character.mainCharacter.MainCharacter;
import worldBuilding.BuildBody;

public class Level2 implements Screen {

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
    final ButtonObject openDoorButton;
    final DoorObject doorObject;
    final Body doorBlockLeft;
    final Body doorBlockRight;
    final LaserObject laser;
    final LaserObject laser2;
    final LaserObject laser3;
    final LaserObject laser4;
    final LaserObject laser5;
    //final BoxObject boxObject;

    final MainCharacter mainCharacter;
    final Enemy_robot enemy_robot1;
    final Enemy_robot enemy_robot2;
    private World gameWorld2;
    private Stage gameStage2;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;
    public static boolean isTheDoorOpen = false;

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
        wallObject0 = new WallObject(gameWorld2, 5, -3f, 1f, 11f,
                0f, 0, 0f);
        wallObject0.setTrigger(gameWorld2, 0, 0, 0);
        wallObject1 = new WallObject(gameWorld2, 15, 2f, 1f, 11f,
                3f, 0, 0f);
        wallObject1.setTrigger(gameWorld2, 0, 0, 0);
        frameObjectUp = new WallObject(gameWorld2, 0f, 12f, 40f, 3f, 0.3f,
                0, -0.3f);
        frameObjectUp.setTrigger(gameWorld2, 0.3f, 0, -0.3f);
        frameObjectDownPartOne = new WallObject(gameWorld2, 0, -0.5f, 28.5f, 2f,
                1f, 0f, -1f);
        frameObjectDownPartOne.setTrigger(gameWorld2, 1f, 0, -1f);
        frameObjectDownPartTwo = new WallObject(gameWorld2, 34.5f, -0.5f, 5f, 2f,
                1, 0f, -1f);
        frameObjectDownDown = new WallObject(gameWorld2, 0, -1f, 40f, 1f,
                0f, 0f, 0f);
        frameObjectDownDown.setType("Bound");
        frameObjectDownDown.setTrigger(gameWorld2, 0, 0, 0);
        frameObjectFont = new WallObject(gameWorld2, -0.5f, -7f, 1f, 22f,
                0f, 0f, 0f);
        frameObjectRear = new WallObject(gameWorld2, 39f, -7f, 1f, 22f,
                0f, 0f, 0f);
        frameObjectRear.setTrigger(gameWorld2,
                0, 0, 0);

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
        
        laser = new LaserObject(gameWorld2,"Lazer_leri.png","leri", 6f, 5.85f, 9f, 3f, 0, 0, 0.03f, 0, 0f, -1.3f);
        laser2 = new LaserObject(gameWorld2,"Lazer_doup.png","doup", 4f, 8f, 3f, 4.5f, 0, 0, 0, 0, -1.3f, 0f);
        laser3 = new LaserObject(gameWorld2,"Lazer_doup.png","doup", 17f, 1.5f, 3f, 9f, 0, 0.04f, 0, 0, -1.3f, 0f);
        laser4 = new LaserObject(gameWorld2,"laser/laser3.png","doup", 30f, 3.75f, 2f, 9f, 0, 0.02f, 0, 0, -1.3f, 0f);
        laser5 = new LaserObject(gameWorld2,"Lazer_rile.png","leri", 35f, 3f, 4f, 2f, 0, 0f, 0.04f, 0, 0f, -1.1f);
        //boxObject = new BoxObject(gameWorld2, 2, 2);
        
        Gdx.input.setInputProcessor(gameStage2);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());
        
        // 40 is good
        stageViewport = new FitViewport(40, 40 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(25, 25 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);

        gameStage2 = new Stage(mainCharacterViewport);
        gameStage2.addActor(frameObjectUp);
        gameStage2.addActor(wallObject0);
        gameStage2.addActor(wallObject1);
        gameStage2.addActor(openDoorButton);
        gameStage2.addActor(frameObjectFont);
        gameStage2.addActor(frameObjectRear);
        gameStage2.addActor(frameObjectDownPartOne);
        gameStage2.addActor(frameObjectDownPartTwo);
        gameStage2.addActor(frameObjectDownDown);
        gameStage2.addActor(doorObject);
        gameStage2.addActor(enemy_robot1);
        gameStage2.addActor(enemy_robot2);
        //gameStage2.addActor(boxObject);
        gameStage2.addActor(mainCharacter);
        gameStage2.addActor(laser);
        gameStage2.addActor(laser2);
        gameStage2.addActor(laser3);
        gameStage2.addActor(laser4);
        gameStage2.addActor(laser5);
        
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
        
        laser.move_Y(3.7f,5.85f);
        if (TimeUtils.nanoTime() - laser.get_start() > 1210000000f) {
        	laser.set_startTime();
        	if(laser.isVisible()) {
        		laser.setVisible(false);
        		laser.sleep();
        	}
        	else {
        		laser.setVisible(true);
        		laser.awake();
        	}
        }
        if (TimeUtils.nanoTime() - laser2.get_start() > 1000000000f) {
        	laser2.set_startTime();
        	if(laser2.isVisible()) {
        		laser2.setVisible(false);
        		laser2.sleep();
        	}
        	else {
        		laser2.setVisible(true);
        		laser2.awake();
        	}
        }
        laser3.move_X(17f,23f);
        laser4.move_X(30f, 34f);
        laser5.move_Y(3f, 7.5f);
        box2DDebugRenderer.render(gameWorld2, gameStage2.getCamera().combined);
        gameWorld2.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    private void update(float delta) {
    	if (Level2.isTheDoorOpen) {
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
    	gameStage2.dispose();
    	screenMusic.dispose();
        wallObject0.dispose();
        wallObject1.dispose();
        mainCharacter.dispose();
        
    }
}
