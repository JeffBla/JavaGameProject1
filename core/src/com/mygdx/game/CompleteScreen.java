package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CompleteScreen {
    private SpriteBatch HUDBatch;
    private Sprite Complete;
    private Texture texture1;
    public static boolean complete=false;
    public static boolean restart=false;
    public static boolean stage=false;
    public static boolean nextstage=false;

    public CompleteScreen() {
        HUDBatch =new SpriteBatch();

        texture1=new Texture("StageSelection/StageComplete.png");
        Complete=new Sprite(texture1);
        Complete.setPosition(-10, -250);
        Complete.setScale(0.8f);
    }

    public void render(float delta) {
        HUDBatch.begin();
        Complete.draw(HUDBatch);
        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX()>405&&Gdx.input.getX()<748&&Gdx.input.getY()>637&&Gdx.input.getY()<730) {
                restart=true;
            }
            else if(Gdx.input.getX()>835&&Gdx.input.getX()<1047&&Gdx.input.getY()>637&&Gdx.input.getY()<730) {
                stage=true;
            }
            else if(Gdx.input.getX()>1162&&Gdx.input.getX()<1452&&Gdx.input.getY()>613&&Gdx.input.getY()<775) {
                nextstage=true;
            }
        }
        HUDBatch.end();
    }
    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
    }
}
