package com.mygdx.game;

import character.mainCharacter.MainCharacterShield;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
    public SpriteBatch HUDBatch;
    public BitmapFont font1;
    public static Sprite FullHp1, FullHp2, FullHp3, EmptyHp1, EmptyHp2, EmptyHp3;
    public static Texture texture1, texture2, texture3, texture4;
    public static int hp = 3;
    public static boolean isHpDecrease = false;
    private float hpCounter = 0;

    public HUD() {
        HUDBatch = new SpriteBatch();
        font1 = new BitmapFont();
        font1.setColor(Color.BLUE);
        font1.getData().setScale(3.0f);

        texture1 = new Texture("HUD/FullHeart.png");
        texture2 = new Texture("HUD/FullHeart.png");
        texture3 = new Texture("HUD/FullHeart.png");
        texture4 = new Texture("HUD/EmptyHeart.png");
        FullHp1 = new Sprite(texture1);
        FullHp1.setPosition(650, 140);
        FullHp1.setScale(0.05f);
        FullHp2 = new Sprite(texture2);
        FullHp2.setPosition(725, 140);
        FullHp2.setScale(0.05f);
        FullHp3 = new Sprite(texture3);
        FullHp3.setPosition(800, 140);
        FullHp3.setScale(0.05f);
        EmptyHp1 = new Sprite(texture4);
        EmptyHp1.setPosition(650, 140);
        EmptyHp1.setScale(0.05f);
        EmptyHp2 = new Sprite(texture4);
        EmptyHp2.setPosition(725, 140);
        EmptyHp2.setScale(0.05f);
        EmptyHp3 = new Sprite(texture4);
        EmptyHp3.setPosition(800, 140);
        EmptyHp3.setScale(0.05f);
    }

    public void render(float delta) {
        HUDBatch.begin();
        font1.draw(HUDBatch, "Stage 1", 20, 880);
        FullHp1.draw(HUDBatch);
        FullHp2.draw(HUDBatch);
        FullHp3.draw(HUDBatch);
        EmptyHp1.draw(HUDBatch);
        EmptyHp2.draw(HUDBatch);
        EmptyHp3.draw(HUDBatch);
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
        font1.dispose();
        texture1.dispose();
        texture2.dispose();
        texture3.dispose();
        texture4.dispose();
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
                break;
        }
    }
}
