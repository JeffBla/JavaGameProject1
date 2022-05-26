package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen {
    public SpriteBatch HUDBatch;
    public static Sprite GameOver;
    public static Texture texture1;
    public static boolean gameover=false;
    public static boolean restart=false;
    public static boolean stage=false;
    public GameOverScreen() {
        HUDBatch =new SpriteBatch();

        texture1=new Texture("StageSelection/GameOverScreen.png");
        GameOver=new Sprite(texture1);
        GameOver.setPosition(-10, -250);
        GameOver.setScale(0.8f);
    }

    public void render(float delta) {
        HUDBatch.begin();
        GameOver.draw(HUDBatch);
        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX()>452&&Gdx.input.getX()<788&&Gdx.input.getY()>643&&Gdx.input.getY()<715) {
                restart=true;
            }
            else if(Gdx.input.getX()>1110&&Gdx.input.getX()<1387&&Gdx.input.getY()>643&&Gdx.input.getY()<733) {
                stage=true;
            }
        }
        HUDBatch.end();
    }
    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
    }
}