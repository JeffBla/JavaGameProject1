package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import character.mainCharacter.MainCharacterShield;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
    public SpriteBatch HUDBatch;
    public static Sprite FullHp1, FullHp2, FullHp3, EmptyHp1, EmptyHp2, EmptyHp3, Pausedbutton;
    public static int hp = 3;
    public static boolean isHpDecrease = false;
    private float hpCounter = 0;

    private Texture texture1 = new Texture(Gdx.files.internal("HUD/FullHeart.png"));
    private Texture texture2 = new Texture(Gdx.files.internal("HUD/EmptyHeart.png"));
    private Texture texture3 = new Texture(Gdx.files.internal("StageSelection/Pausedbutton.png"));

    public HUD() {
        HUDBatch = new SpriteBatch();

        FullHp1 = new Sprite(texture1);
        FullHp1.setPosition(650, 140);
        FullHp1.setScale(0.05f);
        FullHp2 = new Sprite(texture1);
        FullHp2.setPosition(725, 140);
        FullHp2.setScale(0.05f);
        FullHp3 = new Sprite(texture1);
        FullHp3.setPosition(800, 140);
        FullHp3.setScale(0.05f);
        EmptyHp1 = new Sprite(texture2);
        EmptyHp1.setPosition(650, 140);
        EmptyHp1.setScale(0.05f);
        EmptyHp2 = new Sprite(texture2);
        EmptyHp2.setPosition(725, 140);
        EmptyHp2.setScale(0.05f);
        EmptyHp3 = new Sprite(texture2);
        EmptyHp3.setPosition(800, 140);
        EmptyHp3.setScale(0.05f);
        Pausedbutton = new Sprite(texture3);
        Pausedbutton.setPosition(15, 760);
        Pausedbutton.setScale(0.65f);
    }

    public void render(float delta) {
        HUDBatch.begin();
        FullHp1.draw(HUDBatch);
        FullHp2.draw(HUDBatch);
        FullHp3.draw(HUDBatch);
        EmptyHp1.draw(HUDBatch);
        EmptyHp2.draw(HUDBatch);
        EmptyHp3.draw(HUDBatch);
        Pausedbutton.draw(HUDBatch);
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > (Pausedbutton.getX() + 20)
                    && Gdx.input.getX() < (Pausedbutton.getX() + Pausedbutton.getWidth() - 20)
                    && Gdx.input.getY() < (Pausedbutton.getY() - 640)
                    && Gdx.input.getY() > (Pausedbutton.getY() - Pausedbutton.getHeight() - 602)) {
                PausedScreen.pause = true;
            }
        }
        HUDBatch.end();

        if (isHpDecrease) {
            hpCounter += delta;
            if (hpCounter >= MainCharacterShield.invincibleTime) {
                hpCounter = 0;
                isHpDecrease = false;
            }
        }
    }

    public void dispose() {
        HUDBatch.dispose();
        texture1.dispose();
        texture2.dispose();
        texture3.dispose();
    }

    public static void whenHpDecrease() {
        switch (HUD.hp) {
            case 3:
                break;
            case 2:
                FullHp3.setPosition(100000, 100000);
                isHpDecrease = true;
                break;
            case 1:
                FullHp2.setPosition(100000, 100000);
                isHpDecrease = true;
                break;
            case 0:
                FullHp1.setPosition(100000, 100000);
                isHpDecrease = true;
                GameOverScreen.gameover=true;
                break;
        }
    }
}
