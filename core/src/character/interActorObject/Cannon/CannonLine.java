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
    private Sprite sprite_laser;
    private Body body_cannon;
    private Body body_line;
    private int frameCol;
    private int frameRow;
    float stateTime =0;
    public CannonLine(World gameWorld, Body cannon_body,
                      float weight, float height, float anima_duration,
                      float fixBoxOrigin_constant,float fixBoxWeight_constant, float fixBoxHeight_constant) {

        laserSheetTexture = new Texture(Gdx.files.internal("Cannon/laser_2.png"));
        this.body_cannon = cannon_body;

        frameCol=1;
        frameRow=4;
        int perCellWidth = laserSheetTexture.getWidth() / frameCol;
        int perCellHeight = laserSheetTexture.getHeight() / frameRow;
        TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
        TextureRegion[] col_cellRegion = new TextureRegion[4];
        for(int i=0;i<4;i++) {
            col_cellRegion[i] = cellRegion[i][0];
        }
        laserAnimation = new Animation<TextureRegion>(anima_duration, col_cellRegion);
        laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
        currentFrame = laserAnimation.getKeyFrame(stateTime);

        sprite_laser = new Sprite(laserSheetTexture);
        sprite_laser.setPosition(cannon_body.getPosition().x, cannon_body.getPosition().y);
        sprite_laser.setSize(weight * GameMode.PPM, height * GameMode.PPM);
        body_line = BuildBody.createBox(gameWorld, cannon_body.getPosition().x, cannon_body.getPosition().y,
                sprite_laser.getWidth() / GameMode.PPM / 2 + fixBoxWeight_constant,
                sprite_laser.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(sprite_laser.getWidth() / GameMode.PPM / 2, sprite_laser.getHeight() / GameMode.PPM / 2),
                0, 0, 0, false, true, true);
        body_line.setSleepingAllowed(true);
        body_line.setUserData(this);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        currentFrame = laserAnimation.getKeyFrame(stateTime);
        body_line.setTransform(body_cannon.getPosition().x, body_cannon.getPosition().y, (float) body_cannon.getAngle());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame,(float)( body_line.getPosition().x + 1.2*Math.cos(body_cannon.getAngle())), (float)( body_line.getPosition().y + 1.2*Math.sin(body_cannon.getAngle())), 0, 0, sprite_laser.getWidth() / GameMode.PPM, sprite_laser.getHeight() / GameMode.PPM, 1, 1, (float)Math.toDegrees(body_cannon.getAngle()));
    }

    public Body get_body() {
        return body_line;
    }

    public void sleep() {
        body_line.setActive(false);
    }

    public void awake() {
        body_line.setActive(true);
    }

    public void dispose() {
        laserSheetTexture.dispose();
    }
}
