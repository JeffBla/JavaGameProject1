package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CoverImg extends Image {

    public CoverImg(float pos_x, float pos_y, float scale) {
        super(new Texture(Gdx.files.internal("startMenu/icon.png")));
        this.setScale(scale);
        this.setPosition(pos_x, pos_y);
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

}
