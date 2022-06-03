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

public class CannonWarningLine extends Actor{
    private final Texture warningLineSheetTexture;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> warningLineAnimation;
    private Sprite spriteWarningLine;
    private final Body bodyCannon;
    private final Body bodyWarningLine;
    private boolean aim;
    float stateTime =0;
    public CannonWarningLine(World gameWorld, Body bodyCannon,
                             float width, float height, float animaDuration,
                             float fixBoxOrigin_constant,float fixBoxWidth_constant, float fixBoxHeight_constant) {

        warningLineSheetTexture = new Texture(Gdx.files.internal("Cannon/warningLine.png"));
        this.bodyCannon = bodyCannon;

        int frameCol=1;
        int frameRow=4;
        int perCellWidth = warningLineSheetTexture.getWidth() / frameCol;
        int perCellHeight = warningLineSheetTexture.getHeight() / frameRow;
        TextureRegion[][] cellRegion = TextureRegion.split(warningLineSheetTexture, perCellWidth, perCellHeight);
        TextureRegion[] col_cellRegion = new TextureRegion[4];
        for(int i=0;i<4;i++) {
            col_cellRegion[i] = cellRegion[i][0];
        }
        warningLineAnimation = new Animation<TextureRegion>(animaDuration, col_cellRegion);
        warningLineAnimation.setPlayMode(Animation.PlayMode.LOOP);
        currentFrame = warningLineAnimation.getKeyFrame(stateTime);

        spriteWarningLine = new Sprite(warningLineSheetTexture);
        spriteWarningLine.setPosition(bodyCannon.getPosition().x, bodyCannon.getPosition().y);
        spriteWarningLine.setSize(width * GameMode.PPM, height * GameMode.PPM);
        bodyWarningLine = BuildBody.createBox(gameWorld, bodyCannon.getPosition().x, bodyCannon.getPosition().y,
                spriteWarningLine.getWidth() / GameMode.PPM / 2 + fixBoxWidth_constant,
                spriteWarningLine.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(spriteWarningLine.getWidth() / GameMode.PPM / 2, spriteWarningLine.getHeight() / GameMode.PPM / 2),
                0, 0, 0, false, true, true);
        bodyWarningLine.setSleepingAllowed(true);
        bodyWarningLine.setUserData(this);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        currentFrame = warningLineAnimation.getKeyFrame(stateTime);
        bodyWarningLine.setTransform(bodyCannon.getPosition().x, bodyCannon.getPosition().y,bodyCannon.getAngle());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame,(float)(bodyWarningLine.getPosition().x + 1.2*Math.cos(bodyCannon.getAngle())), (float)( bodyWarningLine.getPosition().y + 1.2*Math.sin(bodyCannon.getAngle())), 0, 0, spriteWarningLine.getWidth() / GameMode.PPM, spriteWarningLine.getHeight() / GameMode.PPM, 1, 1, (float)Math.toDegrees(bodyCannon.getAngle()));
    }

    public Body getBody() {
        return bodyWarningLine;
    }

    public void sleep() {
        bodyWarningLine.setActive(false);
    }

    public void awake() {
        bodyWarningLine.setActive(true);
    }

    public void dispose() {
        warningLineSheetTexture.dispose();
    }

    public Sprite getSprite() {
        return spriteWarningLine;
    }

    public boolean getAim() {
        return aim;
    }

    public void setAim(boolean condition) {
        aim = condition;
    }
}
