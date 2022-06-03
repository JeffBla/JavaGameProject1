package com.mygdx.game;

import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AllClearScreen extends HUDScreen {
    private final SpriteBatch HUDBatch;
    private final Sprite Clear,Restart,RestartP,Stage,StageP;
    private final Texture texture1,texture2,texture2p,texture3,texture3p;
    private final ScreenMusic screenMusic;
    private final GameMode gameMode;
    private final Screen screen;
    private float timer=0;
    public boolean complete = false;
    public boolean restart = false;
    public boolean stage = false;

    public AllClearScreen(ScreenMusic screenMusic, GameMode gameMode, Screen screen) {
        this.screenMusic = screenMusic;
        this.gameMode = gameMode;
        this.screen = screen;

        HUDBatch = new SpriteBatch();
        texture1=new Texture("StageSelection/ClearScreen.png");
        texture2=new Texture("StageSelection/CSrestart.png");
        texture2p=new Texture("StageSelection/CSrestartPressed.png");
        texture3=new Texture("StageSelection/CSstage.png");
        texture3p=new Texture("StageSelection/CSstagePressed.png");
        Clear=new Sprite(texture1);
        Clear.setPosition(-80, -300);
        Clear.setScale(0.8f);
        Restart=new Sprite(texture2);
        Restart.setPosition(292,80);
        Restart.setScale(0.8f);
        RestartP=new Sprite(texture2p);
        RestartP.setPosition(292,80);
        RestartP.setScale(0.8f);
        Stage=new Sprite(texture3);
        Stage.setPosition(1005,80);
        Stage.setScale(0.8f);
        StageP=new Sprite(texture3p);
        StageP.setPosition(1005,80);
        StageP.setScale(0.8f);
    }

    private void render(float delta) {
        HUDBatch.begin();
        Clear.draw(HUDBatch);
        Restart.draw(HUDBatch);
        Stage.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if(Gdx.input.getX()>Restart.getX()+50
                    &&Gdx.input.getX()<Restart.getX()+Restart.getWidth()-50
                    &&Gdx.input.getY()>Restart.getY()-Restart.getHeight()+700
                    && Gdx.input.getY()<Restart.getY()+675) {
                RestartP.draw(HUDBatch);
                if(delay(delta)) {
                    restart = true;
                }
            }
            if(Gdx.input.getX()>Stage.getX()+40
                    &&Gdx.input.getX()<Stage.getX()+Stage.getWidth()-45
                    && Gdx.input.getY()>Stage.getY()-Stage.getHeight()+705
                    &&Gdx.input.getY()<Stage.getY()+680){
                StageP.draw(HUDBatch);
                if(delay(delta)) {
                    stage = true;
                }
            }
        }
        HUDBatch.end();
    }

    private void initial() {
        complete = false;
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
        texture2p.dispose();
        texture3.dispose();
        texture3p.dispose();
    }

    public void stateAnalyze(float delta, MainCharacter mainCharacter) {
        if (complete) {
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
