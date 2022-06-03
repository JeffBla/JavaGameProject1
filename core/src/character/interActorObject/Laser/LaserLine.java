package character.interActorObject.Laser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;
import worldBuilding.BuildBody;

public class LaserLine extends Actor {
    private final Texture laserSheetTexture;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> laserAnimation;
    float stateTime = 0;
    private Sprite spriteLaser;
    private final Body body;
    private float width;
    private float height;
    private float fixBoxWidth_constant;
    private float fixBoxHeight_constant;
    private float originX;
    private float endX;
    private float transformX1;
    private float transformX2;
    private int frameCol;
    private int frameRow;
    private String type;
    private boolean beginTouch = false;
    private boolean leave = false;

    public LaserLine(World gameWorld, String texture, String type, boolean canVanish, float x, float y,
                     float width, float height, float animaDuration, float fixBoxOrigin_constant,
                     float fixBoxWidth_constant, float fixBoxHeight_constant) {
        laserSheetTexture = new Texture(Gdx.files.internal(texture));
        if (canVanish == true) {
            if (texture.equals("laser/lineUpdo.png") || texture.equals("laser/lineDoup.png")) {
                frameCol = 15;
                frameRow = 1;
                int perCellWidth = laserSheetTexture.getWidth() / frameCol;
                int perCellHeight = laserSheetTexture.getHeight() / frameRow;
                TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
                laserAnimation = new Animation<TextureRegion>(animaDuration, cellRegion[0]);
                laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
                currentFrame = laserAnimation.getKeyFrame(stateTime);
            } else if (texture.equals("laser/lineLeri.png")  || texture.equals("laser/lineRile.png")) {
                frameCol = 1;
                frameRow = 15;
                int perCellWidth = laserSheetTexture.getWidth() / frameCol;
                int perCellHeight = laserSheetTexture.getHeight() / frameRow;
                TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
                TextureRegion[] col_cellRegion = new TextureRegion[15];
                for (int i = 0; i < 15; i++) {
                    col_cellRegion[i] = cellRegion[i][0];
                }
                laserAnimation = new Animation<TextureRegion>(animaDuration, col_cellRegion);
                laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
                currentFrame = laserAnimation.getKeyFrame(stateTime);
            }
        } else if (canVanish == false) {
            if (texture.equals("laser/lineUpdo2.png") || texture.equals("laser/lineDoup2.png")) {
                frameCol = 4;
                frameRow = 1;
                int perCellWidth = laserSheetTexture.getWidth() / frameCol;
                int perCellHeight = laserSheetTexture.getHeight() / frameRow;
                TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
                laserAnimation = new Animation<TextureRegion>(animaDuration, cellRegion[0]);
                laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
                currentFrame = laserAnimation.getKeyFrame(stateTime);
            } else if (texture.equals("laser/lineLeri2.png") || texture.equals("laser/lineRile2.png")) {
                frameCol = 1;
                frameRow = 4;
                int perCellWidth = laserSheetTexture.getWidth() / frameCol;
                int perCellHeight = laserSheetTexture.getHeight() / frameRow;
                TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
                TextureRegion[] col_cellRegion = new TextureRegion[4];
                for (int i = 0; i < 4; i++) {
                    col_cellRegion[i] = cellRegion[i][0];
                }
                laserAnimation = new Animation<TextureRegion>(animaDuration, col_cellRegion);
                laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
                currentFrame = laserAnimation.getKeyFrame(stateTime);
            }
        }

        this.width = width;
        this.height = height;
        this.fixBoxWidth_constant = fixBoxWidth_constant;
        this.fixBoxHeight_constant = fixBoxHeight_constant;
        this.originX = x;
        this.endX = x + width;
        this.type = type;
        spriteLaser = new Sprite(laserSheetTexture);
        spriteLaser.setPosition(x, y);
        spriteLaser.setSize(width * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createBox(gameWorld, x, y,
                spriteLaser.getWidth() / GameMode.PPM / 2 + fixBoxWidth_constant,
                spriteLaser.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(spriteLaser.getWidth() / GameMode.PPM / 2, spriteLaser.getHeight() / GameMode.PPM / 2),
                0, 0, 0, false, true, true);
        body.setSleepingAllowed(canVanish == true);
        body.setUserData(this);

    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        currentFrame = laserAnimation.getKeyFrame(stateTime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, body.getPosition().x, body.getPosition().y, spriteLaser.getWidth() / GameMode.PPM, spriteLaser.getHeight() / GameMode.PPM);
    }

    public Body getBody() {
        return this.body;
    }

    public Sprite getSprite() {
        return this.spriteLaser;
    }

    public float getOriginX() {
        return originX;
    }

    public float getEndX() {
        return endX;
    }

    public void setTransform(float x1, float x2) {
        transformX1 = x1;
        transformX2 = x2;
    }

    public void touch_rile() {
        float height = spriteLaser.getHeight();
        float weight = transformX2 - transformX1;

        spriteLaser.setSize(weight * GameMode.PPM, height);
        ((PolygonShape) body.getFixtureList().first().getShape()).setAsBox(
                spriteLaser.getWidth() / GameMode.PPM / 2 + fixBoxWidth_constant,
                spriteLaser.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(spriteLaser.getWidth() / GameMode.PPM / 2, spriteLaser.getHeight() / GameMode.PPM / 2),
                0);
        body.setTransform(transformX1, body.getPosition().y, 0);
    }

    public void sleep() {
        body.setActive(false);
    }

    public void awake() {
        body.setActive(true);
    }

    public String getType() {
        return type;
    }

    public boolean getTouch() {
        return beginTouch;
    }

    public void setTouch(boolean condition) {
        beginTouch = condition;
    }

    public boolean getLeave() {
        return leave;
    }

    public void setLeave(boolean condition) {
        leave = condition;
    }

    public void dispose() {
        laserSheetTexture.dispose();
    }

}