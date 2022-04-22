package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final GameMode gameMode;
    OrthographicCamera camera;
    private Texture backgroundImg;
    private ImageButton StartImgButton;
    private ImageButton ExitImgButton;
    private Stage stage;

    public MainMenuScreen(final GameMode gameMode){
        this.gameMode = gameMode;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 700);

        backgroundImg = new Texture(Gdx.files.internal("startmenu/start_game_white_city.png"));

        Drawable startButtonUpDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("startmenu/SpyStartButtonUp.png")));
        Drawable startButtonDownDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("startmenu/SpyStartButtonDown.png")));
        Drawable startButtonCheckDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("startmenu/SpyStartButtonCheck.png")));
        StartImgButton =new ImageButton(startButtonUpDrawable, startButtonDownDrawable, startButtonCheckDrawable);
        StartImgButton.setPosition(100, 600);
        // Height = 148.0f; Width = 182.0f;
        StartImgButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameMode.setScreen(new GameLobby(gameMode));
                dispose();
            }
        });

        Drawable exitButtonUpDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("startmenu/exitButtonUp.png")));
        Drawable exitButtonDownDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("startmenu/exitButtonDown.png")));
        Drawable exitButtonCheckDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("startmenu/exitButtonUp.png")));
        ExitImgButton = new ImageButton(exitButtonUpDrawable, exitButtonDownDrawable, exitButtonCheckDrawable);
        ExitImgButton.setPosition(100,100);
        ExitImgButton.setWidth(148.0f);
        ExitImgButton.setHeight(182.0f);
        ExitImgButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                Gdx.app.exit();
            }
        });

        stage.addActor(StartImgButton);
        stage.addActor(ExitImgButton);
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(1,1,1,0);

        camera.update();
        gameMode.batch.setProjectionMatrix(camera.combined);

        gameMode.batch.begin();
        gameMode.batch.draw(backgroundImg, 0,0);
        gameMode.batch.end();

        stage.draw();
    }

    public void show(){

    }

    public void hide(){

    }

    public void resize(int width, int height) {

    }

    public void resume(){

    }

    public void pause(){

    }

    public void dispose(){
        backgroundImg.dispose();
    }
}
