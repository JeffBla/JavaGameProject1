package com.mygdx.game;

import character.interActorObject.Gear.GearActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import worldBuilding.UI.ButtonBulider;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainMenuScreen implements Screen {
    private final GameMode gameMode;
    private OrthographicCamera camera;
    private final Texture backgroundImg = new Texture(Gdx.files.internal("startMenu/start_game_white_city.png")),
            Cover =new Texture(Gdx.files.internal("startMenu/icon.png"));
//    private Sprite icon;
    private final ImageButton StartImgButton;
    private final ImageButton ExitImgButton;
    private final Stage stage;
    private final Window exitConfirmWindowFrame;
    private final ImageButton exitConfirmWindow_exitButton;
    private final ImageButton exitConfirmWindow_noButton;
    private final CoverImg coverImg;
    private final CoverAnimation coverAnimation;

    public MainMenuScreen(final GameMode gameMode) {
        this.gameMode = gameMode;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 700);

        coverImg = new CoverImg(300f,-300,0,0,0.8f);
        coverAnimation = new CoverAnimation();
        coverImg.addAction(coverAnimation.action1Scale());

        StartImgButton = ButtonBulider.createImgButton("startMenu/SpyStartButtonUp.png", "startMenu/SpyStartButtonDown.png",
                "startMenu/SpyStartButtonCheck.png", 100, 600, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        gameMode.setScreen(new Stageselection(gameMode));
                        dispose();
                    }
                });

        ExitImgButton = ButtonBulider.createImgButton("startMenu/exitButtonUp.png", "startMenu/exitButtonDown.png",
                "startMenu/exitButtonUp.png", 100, 100, 148.0f, 182.0f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        stage.addActor(exitConfirmWindowFrame);
                    }
                });

        Drawable exitWindowDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("startMenu/exitWindowFrame.png")));
        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BROWN, exitWindowDrawable);
        exitConfirmWindowFrame = new Window("", windowStyle);
        exitConfirmWindowFrame.setHeight(300);
        exitConfirmWindowFrame.setWidth(500);
        exitConfirmWindowFrame.setPosition(900 - 250, 450 - 200);

        exitConfirmWindow_exitButton = ButtonBulider.createImgButton("startMenu/exitWindow_exitButtonUp.png", "startMenu/exitWindow_exitButtonDown.png",
                "startMenu/exitWindow_exitButtonUp.png", 50, 20, 300 / 2, 90 / 2,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dispose();
                        Gdx.app.exit();
                    }
                });

        exitConfirmWindow_noButton = ButtonBulider.createImgButton("startMenu/exitWindow_noButtonUp.png", "startMenu/exitWindow_noButtonDown.png",
                "startMenu/exitWindow_noButtonUp.png", 270, 20, 300 / 2, 90 / 2,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        stage.clear();
                        stage.addActor(StartImgButton);
                        stage.addActor(ExitImgButton);
                    }
                });

        exitConfirmWindowFrame.addActor(exitConfirmWindow_exitButton);
        exitConfirmWindowFrame.addActor(exitConfirmWindow_noButton);

        stage.addActor(StartImgButton);
        stage.addActor(ExitImgButton);
        stage.addActor(coverImg);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 0);

        camera.update();
        gameMode.batch.setProjectionMatrix(camera.combined);

        gameMode.batch.begin();
        gameMode.batch.draw(backgroundImg, 0, 0);
//        icon.draw(gameMode.batch);
        gameMode.batch.end();

        stage.draw();
        stage.act();
    }

    public void show() {

    }

    public void hide() {

    }

    public void resize(int width, int height) {

    }

    public void resume() {

    }

    public void pause() {

    }

    public void dispose() {
        backgroundImg.dispose();
        Cover.dispose();
    }
}
