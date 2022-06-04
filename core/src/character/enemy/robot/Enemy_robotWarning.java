package character.enemy.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;

public class Enemy_robotWarning{
    private final Texture warningSheetTexture;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> warningAnimation;
    int perCellWidth;
    int perCellHeight;
    private Sprite spriteWarning;
    private int frameCol;
    private int frameRow;
    private float x;
    private float y;
    float stateTime =0;
    public Enemy_robotWarning(World gameWorld,float x,float y) {
        warningSheetTexture = new Texture(Gdx.files.internal("enemy_robot/enemy_robotSpawnWarning.png"));
        this.x = x;
        this.y = y;
        frameCol=4;
        frameRow=1;
        int perCellWidth = warningSheetTexture.getWidth() / frameCol;
        int perCellHeight = warningSheetTexture.getHeight() / frameRow;
        TextureRegion[][] cellRegion = TextureRegion.split(warningSheetTexture, perCellWidth, perCellHeight);
        warningAnimation = new Animation<TextureRegion>(0.2f, cellRegion[0]);
        warningAnimation.setPlayMode(Animation.PlayMode.LOOP);
        currentFrame = warningAnimation.getKeyFrame(stateTime);
        spriteWarning = new Sprite(warningSheetTexture);
        spriteWarning.setPosition(x, y);
        spriteWarning.setSize(2.5f * GameMode.PPM, 3f * GameMode.PPM);

    }

    public void act(float delta) {
        stateTime += delta;
        currentFrame = warningAnimation.getKeyFrame(stateTime);
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame,x,y,spriteWarning.getWidth() / GameMode.PPM, spriteWarning.getHeight() / GameMode.PPM);
    }

    public void dispose() {
        warningSheetTexture.dispose();
    }
}
