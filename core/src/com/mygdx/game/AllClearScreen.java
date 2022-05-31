package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AllClearScreen {
    private SpriteBatch HUDBatch;
    private Sprite Clear;
    private Texture texture1;
    public static boolean complete=false;
    public static boolean restart=false;
    public static boolean stage=false;

    public AllClearScreen() {
        HUDBatch =new SpriteBatch();
        texture1=new Texture("StageSelection/ClearScreen.png");
        Clear=new Sprite(texture1);
        Clear.setPosition(-80, -300);
        Clear.setScale(0.8f);
    }

    public void render(float delta) {
        HUDBatch.begin();
        Clear.draw(HUDBatch);
        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX()>370&&Gdx.input.getX()<702&&
                    Gdx.input.getY()>705&&Gdx.input.getY()<780) {
                restart=true;
            }
            if(Gdx.input.getX()>1080&&Gdx.input.getX()<1360&&
                    Gdx.input.getY()>705&&Gdx.input.getY()<800){
                stage=true;
            }
        }
        HUDBatch.end();
    }
    public static void initial(){
        complete=false;
        restart=false;
        stage=false;
    }
    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
    }
}
