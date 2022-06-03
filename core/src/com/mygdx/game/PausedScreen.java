package com.mygdx.game;

import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PausedScreen extends HUDScreen{
    private final SpriteBatch HUDBatch;
    private final Sprite pausedSprite,Resume,ResumeP,Restart,RestartP,StageB,StageP;
    private final Texture texture1,texture2,texture3,texture4,texture2p,texture3p,texture4p;
    private final ScreenMusic screenMusic;
    private final GameMode gameMode;
    private final Screen screen;
    private float timer=0;
    public static boolean pause = false;
    public boolean resume = false;
    public boolean restart = false;
    public boolean stage = false;

    public PausedScreen(ScreenMusic screenMusic, GameMode gameMode, Screen screen) {
        this.screenMusic = screenMusic;
        this.gameMode = gameMode;
        this.screen = screen;

        HUDBatch = new SpriteBatch();

        texture1 = new Texture("StageSelection/PausedScreen.png");
        texture2=new Texture("StageSelection/resumebutton.png");
        texture2p=new Texture("StageSelection/resumePressed.png");
        texture3=new Texture("StageSelection/pausedrestart.png");
        texture3p=new Texture("StageSelection/pausedrestartPressed.png");
        texture4=new Texture("StageSelection/pausedstage.png");
        texture4p=new Texture("StageSelection/pausedstagePressed.png");
        pausedSprite = new Sprite(texture1);
        pausedSprite.setPosition(-10, -250);
        pausedSprite.setScale(0.8f);
        Resume=new Sprite(texture2);
        Resume.setPosition(680, 450);
        Resume.setScale(0.8f);
        ResumeP=new Sprite(texture2p);
        ResumeP.setPosition(680, 450);
        ResumeP.setScale(0.8f);
        Restart=new Sprite(texture3);
        Restart.setPosition(680, 300);
        Restart.setScale(0.8f);
        RestartP=new Sprite(texture3p);
        RestartP.setPosition(680, 300);
        RestartP.setScale(0.8f);
        StageB=new Sprite(texture4);
        StageB.setPosition(680, 150);
        StageB.setScale(0.8f);
        StageP=new Sprite(texture4p);
        StageP.setPosition(680, 150);
        StageP.setScale(0.8f);
    }

    private void render(float delta) {
        HUDBatch.begin();
        pausedSprite.draw(HUDBatch);
        Resume.draw(HUDBatch);
        Restart.draw(HUDBatch);
        StageB.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > Resume.getX()+50 &&
                    Gdx.input.getX() < Resume.getX()+Resume.getWidth()-50 &&
                    Gdx.input.getY() > Resume.getY()-Resume.getHeight() &&
                    Gdx.input.getY() < Resume.getY()) {
                ResumeP.draw(HUDBatch);
                if(delay(delta)){
                pause = false;
                resume=true;
                }
            }
            else if (Gdx.input.getX() > Restart.getX()+50 &&
                    Gdx.input.getX() < Restart.getX()+Restart.getWidth()-50 &&
                    Gdx.input.getY() > Restart.getY()+310-Resume.getHeight() &&
                    Gdx.input.getY() < Restart.getY()+285) {
                RestartP.draw(HUDBatch);
                if(delay(delta)) {
                    restart = true;
                }
            }
            else if (Gdx.input.getX() > StageB.getX()+50 &&
                    Gdx.input.getX() < StageB.getX()+StageB.getWidth()-50 &&
                    Gdx.input.getY() > StageB.getY()+615-Resume.getHeight() &&
                    Gdx.input.getY() < StageB.getY()+580) {
                StageP.draw(HUDBatch);
                if(delay(delta)) {
                    stage = true;
                }
            }
        }
        HUDBatch.end();
    }

    private void initial() {
        pause = false;
        restart = false;
        stage = false;
    }
    private boolean delay(float delta) {
        float interval=0.05f;
        timer+=delta;
        if(timer>=interval) {
            timer=0;
            return true;
        }
        return false;
    }

    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
        texture2.dispose();
        texture3.dispose();
        texture4.dispose();
        texture2p.dispose();
        texture3p.dispose();
        texture4p.dispose();
    }

    public void stateAnalyze(float delta, MainCharacter mainCharacter) {
        if (pause) {
            mainCharacter.getSoundEffect().stopRun_sound();
            screenMusic.stopLevelMusic();
            render(delta);
            if (restart) {
                initial();
                gameMode.setScreen(analyzeCurrentLevel_new(screen, gameMode));
                screen.dispose();
            } else if (stage) {
                initial();
                gameMode.setScreen(new Stageselection(gameMode));
                screen.dispose();
            }
        }
    }
}
