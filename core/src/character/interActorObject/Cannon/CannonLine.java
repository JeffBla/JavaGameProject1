package character.interActorObject.Cannon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;

import worldBuilding.BuildBody;

public class CannonLine extends Actor{
    private final Texture laserSheetTexture;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> laserAnimation;
    int perCellWidth;
    int perCellHeight;
    private Sprite spriteLaser;
    private Body bodyCannon;
    private Body bodyLine;
    private int frameCol;
    private int frameRow;
    private boolean onAttack = true;
    float stateTime =0;
    public CannonLine(World gameWorld, Body bodyCannon,
                      float width, float height, float animaDuration,
                      float fixBoxOrigin_constant,float fixBoxWidth_constant, float fixBoxHeight_constant) {

        laserSheetTexture = new Texture(Gdx.files.internal("Cannon/line.png"));
        this.bodyCannon = bodyCannon;

        frameCol=1;
        frameRow=4;
        int perCellWidth = laserSheetTexture.getWidth() / frameCol;
        int perCellHeight = laserSheetTexture.getHeight() / frameRow;
        TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
        TextureRegion[] col_cellRegion = new TextureRegion[4];
        for(int i=0;i<4;i++) {
            col_cellRegion[i] = cellRegion[i][0];
        }
        laserAnimation = new Animation<TextureRegion>(animaDuration, col_cellRegion);
        laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
        currentFrame = laserAnimation.getKeyFrame(stateTime);

        spriteLaser = new Sprite(laserSheetTexture);
        spriteLaser.setPosition(bodyCannon.getPosition().x, bodyCannon.getPosition().y);
        spriteLaser.setSize(width * GameMode.PPM, height * GameMode.PPM);
        bodyLine = BuildBody.createBox(gameWorld, bodyCannon.getPosition().x, bodyCannon.getPosition().y,
                spriteLaser.getWidth() / GameMode.PPM / 2 + fixBoxWidth_constant,
                spriteLaser.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(spriteLaser.getWidth() / GameMode.PPM / 2, spriteLaser.getHeight() / GameMode.PPM / 2),
                0, 0, 0, false, true, true);
        bodyLine.setSleepingAllowed(true);
        bodyLine.setUserData(this);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        currentFrame = laserAnimation.getKeyFrame(stateTime);
        bodyLine.setTransform(bodyCannon.getPosition().x, bodyCannon.getPosition().y, (float) bodyCannon.getAngle());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame,(float)( bodyLine.getPosition().x + 1.2*Math.cos(bodyCannon.getAngle())), (float)( bodyLine.getPosition().y + 1.2*Math.sin(bodyCannon.getAngle())), 0, 0, spriteLaser.getWidth() / GameMode.PPM, spriteLaser.getHeight() / GameMode.PPM, 1, 1, (float)Math.toDegrees(bodyCannon.getAngle()));
    }

    public Body getBody() {
        return bodyLine;
    }

    public TextureRegion getTextureRegion() {
        return currentFrame;
    }

    public Sprite getSprite() {
        return spriteLaser;
    }

    public boolean getAttack() {
        return onAttack;
    }

    public void setAttack(boolean condition) {
        onAttack = condition;
    }

    public void sleep() {
        bodyLine.setActive(false);
    }

    public void awake() {
        bodyLine.setActive(true);
    }

    public void dispose() {
        laserSheetTexture.dispose();
    }
}
