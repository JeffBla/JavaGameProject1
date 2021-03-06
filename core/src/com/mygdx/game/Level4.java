package com.mygdx.game;

import character.interActorObject.Gear.GearActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import character.enemy.robot.Enemy_robot;
import character.interActorObject.WallObject;
import character.interActorObject.Laser.Laser;
import character.interActorObject.Cannon.Cannon;
import character.mainCharacter.MainCharacter;

public class Level4 implements Screen {
    private final GameMode gameMode;
    private final ScreenMusic screenMusic;
    private final WallObject frameObjectUp;
    private final WallObject frameObjectDown;
    private final WallObject frameObjectFont;
    private final WallObject frameObjectRear;
    private final WallObject wallObject1;
    private final WallObject wallObject2;
    private final WallObject wallObject3;
    private final WallObject wallObject4;
    private final WallObject wallObject5;
    private final WallObject wallObject6;
    private final Laser laser1;
    private Cannon cannon1;
    private Enemy_robot enemy_robot1;
    private Enemy_robot enemy_robot2;
    private GearActor gearActor;
    private final MainCharacter mainCharacter;
    private World gameWorld4;
    private Stage gameStage4;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FitViewport stageViewport;
    private FitViewport mainCharacterViewport;
    public static boolean isTheDoorOpen = false;
    public HUD HUDBatch;
    public PausedScreen Pause;
    public GameOverScreen GameOver;
    public AllClearScreen Complete;
    private int state = 1;

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
        wallObject5 = new WallObject(gameWorld4, 13.5f, 13.6f, 3f, 1.8f,
                0f, 0, 0f);
        wallObject6 = new WallObject(gameWorld4, 25, 13.5f, 3f, 2f,
                0f, 0, 0f);

        gearActor = new GearActor(gameWorld4, 40, 10, 6, 6);

        laser1 = new Laser(gameWorld4, "laser/lineRile2.png", "laser/baseRile.png", "rile", false, 6.05f, 17.5f, 43.3f, 1f, 0.1f, 0f, 0.06f, 0, 0f, -0.3f, 43.1f, 0f, 1.2f, 1f);
        Gdx.input.setInputProcessor(gameStage4);
        float ratio = (float) (Gdx.graphics.getWidth()) / (float) (Gdx.graphics.getHeight());

        // 40 is good
        stageViewport = new FitViewport(51, 51 / ratio); // This is for developer
        mainCharacterViewport = new FitViewport(35, 35 / ratio); // This is for gamer
        mainCharacterViewport.getCamera().position.set(0, 0, 1);
        Pause = new PausedScreen(screenMusic, gameMode, this);
        GameOver = new GameOverScreen(screenMusic, gameMode, this);
        Complete = new AllClearScreen(screenMusic, gameMode, this);
        HUDBatch = new HUD(Pause);

        gameStage4 = new Stage(mainCharacterViewport);
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
        gameStage4.addActor(laser1);
        gameStage4.addActor(mainCharacter);
        gameStage4.addActor(gearActor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);

        if (gameStage4.getViewport() == mainCharacterViewport) {
            mainCharacterViewport.getCamera().position.set(mainCharacter.getPosition(4, 1.5f));
        }
        gameStage4.getCamera().update();
        gameMode.batch.setProjectionMatrix(gameStage4.getCamera().combined);

        gameMode.batch.begin();
        gameStage4.draw();
        gameMode.batch.end();

//            box2DDebugRenderer.render(gameWorld4, gameStage4.getCamera().combined);
        HUDBatch.render(delta);

        update(delta);
    }

    private void update(float delta) {
        if (gearActor.getHp() < 80) {
            if (state == 1) {
                state = 2;
                enemy_robot1 = new Enemy_robot(gameWorld4, 28f, 5f, 5, -2f, true);
                enemy_robot1.sleep();
                enemy_robot1.setStartTime(TimeUtils.nanoTime());
                enemy_robot2 = new Enemy_robot(gameWorld4, 20, 12.5f, 5, 2f, true);
                enemy_robot2.sleep();
                cannon1 = new Cannon(gameWorld4, mainCharacter.getBody(), 15, 8, 1.5f, 1, 0.1f);
                cannon1.setStartTime(TimeUtils.nanoTime());
                gameStage4.addActor(enemy_robot1);
                gameStage4.addActor(enemy_robot2);
                gameStage4.addActor(cannon1);
            } else if (state == 2 && (TimeUtils.nanoTime() - enemy_robot1.getStartTime()) > 3000000000f) {
                state = 3;
                enemy_robot1.awake();
                enemy_robot2.awake();
            }
        }

        if (state == 3) {
            if(cannon1.getBase().getBeAttacked() == true) {
                cannon1.BeAttacked();
                cannon1.getBase().setBeAttacked(false);
            }
            else if(cannon1.getBase().getOnRecover() == true && TimeUtils.nanoTime() - cannon1.getStart() > 5000000000f) {
                cannon1.reset();
            }
            else if(cannon1.getBase().getBeAttacked() == false){
                if (TimeUtils.nanoTime() - cannon1.getStart() > 1500000000f) {
                    if (cannon1.getBase().getMove() == true) {
                        cannon1.aim();
                    }
                    if (TimeUtils.nanoTime() - cannon1.getStart() > 2500000000f) {
                        if (cannon1.getWarningLine().getAim() == true) {
                            cannon1.attack();
                        }
                        if (TimeUtils.nanoTime() - cannon1.getStart() > 4000000000f) {
                            cannon1.reDestination();
                        }
                    }
                }
            }
        }
        if (gearActor.getHp() > 50 || (gearActor.getHp() <= 30 && gearActor.getHp() != 0)) {
            gearActor.shoot_FireBall(gameStage4, delta);
        }
        if (gearActor.getHp() <= 0) {
            // finish
            Complete.complete = true;
        }

        Pause.stateAnalyze(delta, mainCharacter);
        GameOver.stateAnalyze(delta, mainCharacter);
        Complete.stateAnalyze(delta, mainCharacter);
        if (Pause.resume) {
            screenMusic.playGameLobbyMusic();
            Pause.resume = false;
        }
        if (!Pause.pause && !GameOver.gameover && !Complete.complete) {

            laser1.moveY(3.2f, 17.5f);
            if (laser1.getLine().getTouch() == true) {
                laser1.getLine().touch_rile();
                laser1.getLine().setTouch(false);
            }

            if (laser1.getLine().getLeave() == true) {
                laser1.getLine().touch_rile();
                laser1.getLine().setLeave(false);
            }

            gameStage4.act();
            gameWorld4.step(Gdx.graphics.getDeltaTime(), 6, 2);
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
        isTheDoorOpen = false;
        gameStage4.dispose();
        screenMusic.dispose();
        mainCharacter.dispose();
        frameObjectUp.dispose();
        frameObjectFont.dispose();
        frameObjectRear.dispose();
        laser1.dispose();
        if (cannon1 != null) {
            cannon1.dispose();
        }
        HUD.hp = 3;
        HUDBatch.dispose();
        Pause.dispose();
        GameOver.dispose();
        Complete.dispose();
    }
}