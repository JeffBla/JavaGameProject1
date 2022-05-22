package com.mygdx.game;

import character.interActorObject.Gear.GearActor;
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
import character.interActorObject.Laser.LaserObjectBase;
import character.interActorObject.Laser.LaserObjectLine;
import character.mainCharacter.MainCharacter;
import worldBuilding.BuildBody;

public class Level4 implements Screen {

	final GameMode gameMode;
    final ScreenMusic screenMusic;
    final WallObject frameObjectUp;
    final WallObject frameObjectDown;
    final WallObject frameObjectFont;
    final WallObject frameObjectRear;
    final WallObject wallObject1;
    final WallObject wallObject2;
    final WallObject wallObject3;
    final WallObject wallObject4;
    final WallObject wallObject5;
    final WallObject wallObject6;
    final LaserObjectLine laserline1;
    final LaserObjectBase laserbase1;
    final LaserObjectLine laserline2;
    final LaserObjectBase laserbase2;
    final Enemy_robot enemy_robot1;
    final Enemy_robot enemy_robot2;
    private GearActor gearActor;
//    final DoorObject doorObject;
//    final Body doorBlockLeft;
//    final Body doorBlockRight;
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
        frameObjectUp = new WallObject(gameWorld4, 0f, 18f, 51f, 3f, 0.3f,
                0, -0.3f);
        frameObjectDown = new WallObject(gameWorld4, 0f, 0f, 51f, 3f, 0.3f,
                0, -0.3f);
        frameObjectFont = new WallObject(gameWorld4, -0.5f, -7f, 1f, 28f,
                0f, 0f, 0f);
        frameObjectRear = new WallObject(gameWorld4, 50f, -7f, 1f, 28f,
                0f, 0f, 0f);
        wallObject1 = new WallObject(gameWorld4, 5, 0f, 1f, 9f,
                0f, 0, 0f);
        wallObject2 = new WallObject(gameWorld4, 5, 10f, 1f, 8f,
                1.2f, 0, 0f);
        wallObject3 = new WallObject(gameWorld4, 15.5f, 5f, 3f, 2f,
                0f, 0, 0f);
        wallObject4 = new WallObject(gameWorld4, 24, 9f, 3f, 2f,
                0f, 0, 0f);
        wallObject5 = new WallObject(gameWorld4, 13.5f, 14f, 3f, 2f,
                0f, 0, 0f);
        wallObject6 = new WallObject(gameWorld4, 25, 13f, 3f, 2f,
                0f, 0, 0f);

        enemy_robot1 = new Enemy_robot(gameWorld4, 28f, 5f, 5, -2f);
        enemy_robot2 = new Enemy_robot(gameWorld4, 20, 12.5f, 5, 2f);
        gearActor = new GearActor(gameWorld4, 40,10, 6,6);

        laserline1 = new LaserObjectLine(gameWorld4,"laser/laser_rile_2.png","rile", false , 6f, 17.5f, 43.5f, 1f, 0.1f, 0f, 0.06f, 0, 0f, -0.3f);
        laserbase1 = new LaserObjectBase(laserline1.get_body(),"laser/rile.png","rile", 1.2f, 1f, 43.1f, 0f);
        laserline2 = new LaserObjectLine(gameWorld4,"laser/laser_leri.png","leri", true , 6.5f, 3.4f, 43.5f, 1f, 0.167562f, 0f, 0f, 0, 0f, -0.3f);
        laserbase2 = new LaserObjectBase(laserline2.get_body(),"laser/leri.png","leri", 1.2f, 1.2f, -0.92f, -0.05f);
//        doorObject = new DoorObject(gameWorld4, "doorLeft.png", "doorRight.png",
//                30f, 0, 3f, 1f, 2, -2,
//                0, 0, 0);
//        doorBlockLeft = BuildBody.createBox(gameWorld4, 28, 0, 0.5f, 0.5f,
//                new Vector2(0, 0), 0, 0, 0, true, false, false);
//        doorBlockRight = BuildBody.createBox(gameWorld4, 35, 0, 0.5f, 0.5f,
//                new Vector2(0, 0), 0, 0, 0, true, false, false);
        
        Gdx.input.setInputProcessor(gameStage4);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());
        
        // 40 is good
        stageViewport = new FitViewport(51, 51 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(35, 35 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);

        gameStage4 = new Stage(stageViewport);
        gameStage4.addActor(frameObjectUp);
        gameStage4.addActor(frameObjectFont);
        gameStage4.addActor(frameObjectRear);
        gameStage4.addActor(frameObjectDown);
        gameStage4.addActor(wallObject1);
        gameStage4.addActor(wallObject2);
        gameStage4.addActor(wallObject3);
        gameStage4.addActor(wallObject4);
        gameStage4.addActor(wallObject5);
        gameStage4.addActor(wallObject6);
        gameStage4.addActor(enemy_robot1);
        gameStage4.addActor(enemy_robot2);
        gameStage4.addActor(gearActor);
        gameStage4.addActor(laserbase1);
        gameStage4.addActor(laserline1);
        gameStage4.addActor(laserbase2);
        gameStage4.addActor(laserline2);
        gameStage4.addActor(mainCharacter);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

        if(gameStage4.getViewport() == mainCharacterViewport) {
            mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(4, 1.5f));
        }
        gameStage4.getCamera().update();
        gameMode.batch.setProjectionMatrix(gameStage4.getCamera().combined);

        gameStage4.act();
        update(delta);

        gameMode.batch.begin();
        gameStage4.draw();
        gameMode.batch.end();
        
        laserline1.move_Y(3.2f, 17.5f);

        if(laserline1.get_body().getPosition().y >= 4.3 && laserline1.get_body().getPosition().y<=6.7) {
        	 laserline1.touch_rile(18.5f,49.5f);
        }
        else if(laserline1.get_body().getPosition().y >= 8.3 && laserline1.get_body().getPosition().y <= 10.7) {
        	laserline1.touch_rile(27,49.5f);
        }
        else if(laserline1.get_body().getPosition().y >= 13.3 && laserline1.get_body().getPosition().y <= 15.7) {
        	laserline1.touch_rile(34,49.5f);
        }
        else  {
        	laserline1.left_rile();
        }
       
        if (TimeUtils.nanoTime() - laserline2.get_start() > 2500000000f) {
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

        box2DDebugRenderer.render(gameWorld4, gameStage4.getCamera().combined);
        gameWorld4.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    private void update(float delta) {
//    	if (Level2.isTheDoorOpen) {
//            doorBlockLeft.setTransform(27, 0, 0);
//            doorBlockRight.setTransform(36f, 0, 0);
//        }
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
    	isTheDoorOpen=false;
    	gameStage4.dispose();
    	screenMusic.dispose();
        mainCharacter.dispose();
        frameObjectUp.dispose();
        frameObjectFont.dispose();
        frameObjectRear.dispose();
        //doorObject.dispose();
    }
}