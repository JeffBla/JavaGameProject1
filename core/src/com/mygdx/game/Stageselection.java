package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import worldBuilding.UI.ButtonBulider;

public class Stageselection implements Screen{
    final GameMode gameMode ;
    OrthographicCamera camera;
    private Texture backgroundImg;
    private ImageButton BackImgButton;
    private ImageButton Stage1ImgButton;
    private ImageButton Stage2ImgButton;
    private ImageButton Stage3ImgButton;
    private Stage stage;
    private Window exitConfirmWindowFrame;

    public Stageselection(final GameMode gameMode) {
        this.gameMode = gameMode;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 700);

        backgroundImg = new Texture(Gdx.files.internal("startmenu/start_game_white_city.png"));
        BackImgButton=ButtonBulider.createImgButton("StageSelection/backbuttonUp.png", "StageSelection/backbuttonDown.png",
                "StageSelection/backbuttonPressed.png", 40, 750,150,200, new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        gameMode.setScreen(new MainMenuScreen(gameMode));
                        dispose();
                    }
                });
        Stage1ImgButton=worldBuilding.UI.ButtonBulider.createImgButton("StageSelection/Stage1.png",
                "StageSelection/Stage1down.png",
                "StageSelection/Stage1Pressed.png",
                100, 600, new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameMode.setScreen(new GameLobby(gameMode));
                dispose();
            }
        });
        Stage2ImgButton=worldBuilding.UI.ButtonBulider.createImgButton("StageSelection/Stage2.png",
                "StageSelection/Stage2down.png", "StageSelection/Stage2Pressed.png",
                300, 600, new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameMode.setScreen(new Level2(gameMode));
                dispose();
            }
        });
        Stage3ImgButton=worldBuilding.UI.ButtonBulider.createImgButton("StageSelection/Stage3.png",
                "StageSelection/Stage3down.png", "StageSelection/Stage3Pressed.png",
                500, 600, new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameMode.setScreen(new Level3(gameMode));
                dispose();
            }
        });
        stage.addActor(BackImgButton);
        stage.addActor(Stage1ImgButton);
        stage.addActor(Stage2ImgButton);
        stage.addActor(Stage3ImgButton);

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 0);

        camera.update();
        gameMode.batch.setProjectionMatrix(camera.combined);

        gameMode.batch.begin();
        gameMode.batch.draw(backgroundImg, 0, 0);
        gameMode.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        backgroundImg.dispose();
        // TODO Auto-generated method stub

    }
}
