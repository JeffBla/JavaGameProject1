package com.mygdx.game;

import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen extends HUDScreen{
    private final SpriteBatch HUDBatch;
    private final Sprite GameOver,Restart,RestartP,Stage,StageP;
    private final Texture texture1,texture2,texture2p,texture3,texture3p;
    private final ScreenMusic screenMusic;
    private final GameMode gameMode;
    private final Screen screen;
    private float timer=0;
    public static boolean gameover = false;
    public boolean restart = false;
    public boolean stage = false;

    public GameOverScreen(ScreenMusic screenMusic, GameMode gameMode, Screen screen) {
        this.screenMusic = screenMusic;
        this.gameMode = gameMode;
        this.screen = screen;

        HUDBatch = new SpriteBatch();

        texture1=new Texture("StageSelection/GameOverScreen.png");
        texture2=new Texture("StageSelection/GOrestart.png");
        texture2p=new Texture("StageSelection/GOrestartPressed.png");
        texture3=new Texture("StageSelection/GOstage.png");
        texture3p=new Texture("StageSelection/GOstagePressed.png");
        GameOver=new Sprite(texture1);
        GameOver.setPosition(-10, -250);
        GameOver.setScale(0.8f);
        Restart=new Sprite(texture2);
        Restart.setPosition(380,130);
        Restart.setScale(0.8f);
        RestartP=new Sprite(texture2p);
        RestartP.setPosition(380,130);
        RestartP.setScale(0.8f);
        Stage=new Sprite(texture3);
        Stage.setPosition(1000, 130);
        Stage.setScale(0.8f);
        StageP=new Sprite(texture3p);
        StageP.setPosition(1000, 130);
        StageP.setScale(0.8f);
    }

    private void render(float delta) {
        HUDBatch.begin();
        GameOver.draw(HUDBatch);
        Restart.draw(HUDBatch);
        Stage.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if(Gdx.input.getX()>Restart.getX()+50&&
                    Gdx.input.getX()<Restart.getX()+Restart.getWidth()-50&&
                    Gdx.input.getY()>Restart.getY()-Restart.getHeight()+655&&
                    Gdx.input.getY()<Restart.getY()+625) {
                RestartP.draw(HUDBatch);
                if(delay(delta)) {
                    restart = true;
                }
            }
            else if(
                    Gdx.input.getX()>Stage.getX()+50&&
                            Gdx.input.getX()<Stage.getX()+Restart.getWidth()-50&&
                            Gdx.input.getY()>Stage.getY()-Stage.getHeight()+655&&
                            Gdx.input.getY()<Stage.getY()+625
            ) {
                StageP.draw(HUDBatch);
                if(delay(delta)) {
                    stage = true;
                }
            }
        }
        HUDBatch.end();
    }

    private void initial() {
        gameover = false;
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
        if (gameover) {
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