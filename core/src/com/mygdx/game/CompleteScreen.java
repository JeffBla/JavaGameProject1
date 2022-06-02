package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CompleteScreen extends HUDScreen{
    private final SpriteBatch HUDBatch;
    private final Sprite Complete,Restart,RestartP,Stage,StageP,NextStage,NextStageP;
    private final Texture texture1,texture2,texture2p,texture3,texture3p,texture4,texture4p;
    private final ScreenMusic screenMusic;
    private final GameMode gameMode;
    private final Screen screen;
    public boolean complete = false;
    public boolean restart = false;
    public boolean stage = false;
    public boolean nextstage = false;

    public CompleteScreen(ScreenMusic screenMusic, GameMode gameMode, Screen screen) {
        this.screenMusic = screenMusic;
        this.gameMode = gameMode;
        this.screen = screen;

        HUDBatch = new SpriteBatch();

        texture1=new Texture("StageSelection/StageComplete.png");
        texture2=new Texture("StageSelection/SCrestart.png");
        texture2p=new Texture("StageSelection/SCrestartPressed.png");
        texture3=new Texture("StageSelection/SCstage.png");
        texture3p=new Texture("StageSelection/SCstagePressed.png");
        texture4=new Texture("StageSelection/SCnextstage.png");
        texture4p=new Texture("StageSelection/SCnextstagePressed.png");
        Complete=new Sprite(texture1);
        Complete.setPosition(-10, -250);
        Complete.setScale(0.8f);
        Restart=new Sprite(texture2);
        Restart.setPosition(362, 103);
        Restart.setScale(0.8f);
        RestartP=new Sprite(texture2p);
        RestartP.setPosition(362, 103);
        RestartP.setScale(0.8f);
        Stage=new Sprite(texture3);
        Stage.setPosition(785, 103);
        Stage.setScale(0.8f);
        StageP=new Sprite(texture3p);
        StageP.setPosition(785, 103);
        StageP.setScale(0.8f);
        NextStage=new Sprite(texture4);
        NextStage.setPosition(1062, 103);
        NextStage.setScale(0.8f);
        NextStageP=new Sprite(texture4p);
        NextStageP.setPosition(1062, 103);
        NextStageP.setScale(0.8f);
    }

    private void render() {
        HUDBatch.begin();
        Complete.draw(HUDBatch);
        Restart.draw(HUDBatch);
        Stage.draw(HUDBatch);
        NextStage.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if(Gdx.input.getX()>Restart.getX()+50 &&
                    Gdx.input.getX()<Restart.getX()+Restart.getWidth()-50 &&
                    Gdx.input.getY()>Restart.getY()-Restart.getHeight()+715 &&
                    Gdx.input.getY()<Restart.getY()+670) {
                RestartP.draw(HUDBatch);
                restart=true;
            }
            else if(Gdx.input.getX()>Stage.getX()+35 &&
                    Gdx.input.getX()<Stage.getX()+Stage.getWidth()-35 &&
                    Gdx.input.getY()>Stage.getY()-Stage.getHeight()+715 &&
                    Gdx.input.getY()<Stage.getY()+670) {
                StageP.draw(HUDBatch);
                stage=true;
            }
            else if(Gdx.input.getX()>NextStage.getX()+45 &&
                    Gdx.input.getX()<NextStage.getX()+NextStage.getWidth()-45&&
                    Gdx.input.getY()>NextStage.getY()-NextStage.getHeight()+715&&
                    Gdx.input.getY()<NextStage.getY()+670) {
                NextStageP.draw(HUDBatch);
                nextstage=true;
            }
        }
        HUDBatch.end();
    }

    private void initial() {
        complete = false;
        restart = false;
        stage = false;
        nextstage = false;
    }

    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
        texture2.dispose();
        texture2p.dispose();
        texture3.dispose();
        texture3p.dispose();
        texture4.dispose();
        texture4p.dispose();
    }

    public void stateAnalyze() {
        if (complete) {
            screenMusic.stopLevelMusic();
            render();
            if (restart) {
                initial();
                gameMode.setScreen(analyzeCurrentLevel_new(screen, gameMode));
                screen.dispose();
            } else if (stage) {
                initial();
                gameMode.setScreen(new Stageselection(gameMode));
                screen.dispose();
            } else if (nextstage) {
                initial();
                gameMode.setScreen(nextLevel_new(screen,gameMode));
                screen.dispose();
            }
        }
    }
}
