package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMode extends Game {
	public static final	float PPM= 100f; // pixel per meter (PPM) ratio
	public static final float interActerObject_collisionConstant = 0.5f;

	SpriteBatch batch;
	BitmapFont bitmapFont;
	float bitmapFontSize= 5.0f;
	// ^for testing and
	// providing the programmer some information

	public void create () {
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		bitmapFont.getData().setScale(bitmapFontSize);
		this.setScreen(new MainMenuScreen(this));
	}

	public void render () {
		super.render();
	}

	public void dispose () {
		batch.dispose();
		bitmapFont.dispose();
	}
}
