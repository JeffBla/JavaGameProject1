package character.interActorObject.Laser;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameMode;

import worldBuilding.BuildBody;

public class LaserObjectLine extends Actor{
    private final Texture laserSheetTexture;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> laserAnimation;
    int perCellWidth;
    int perCellHeight;
    float stateTime =0;
    private Sprite sprite_laser;
    private Body body;
    private String type;
    private boolean can_vanish;
    private float weight;
    private float height;
    private float fixBoxWeight_constant;
    private float fixBoxHeight_constant;
    private float origin_x;
    private float end_x;
    private float transform_x1;
    private float transform_x2;
    private float speed_x;
    private float speed_y;
    private int frameCol;
    private int frameRow;
    private long start = 0;
    private boolean begin_touch = false;
    private boolean leave = false;

    public LaserObjectLine(World gameWorld, String texture1,String type, boolean can_vanish ,  float x, float y,
                           float weight, float height, float anima_duration, float speed_x, float speed_y, float fixBoxOrigin_constant,
                           float fixBoxWeight_constant, float fixBoxHeight_constant) {
        laserSheetTexture = new Texture(Gdx.files.internal(texture1));
        if(can_vanish==true) {
            if(texture1=="laser/laser_updo.png"||texture1=="laser/laser_doup.png") {
                frameCol=15;
                frameRow=1;
                int perCellWidth = laserSheetTexture.getWidth() / frameCol;
                int perCellHeight = laserSheetTexture.getHeight() / frameRow;
                TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
                laserAnimation = new Animation<TextureRegion>(anima_duration, cellRegion[0]);
                laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
                currentFrame = laserAnimation.getKeyFrame(stateTime);
            }
            else if(texture1=="laser/laser_leri.png"||texture1=="laser/laser_rile.png") {
                frameCol=1;
                frameRow=15;
                int perCellWidth = laserSheetTexture.getWidth() / frameCol;
                int perCellHeight = laserSheetTexture.getHeight() / frameRow;
                TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
                TextureRegion[] col_cellRegion = new TextureRegion[15];
                for(int i=0;i<15;i++) {
                    col_cellRegion[i] = cellRegion[i][0];
                }
                laserAnimation = new Animation<TextureRegion>(anima_duration, col_cellRegion);
                laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
                currentFrame = laserAnimation.getKeyFrame(stateTime);
            }
        }
        else if(can_vanish==false) {
            if(texture1=="laser/laser_updo_2.png"||texture1=="laser/laser_doup_2.png") {
                frameCol=4;
                frameRow=1;
                int perCellWidth = laserSheetTexture.getWidth() / frameCol;
                int perCellHeight = laserSheetTexture.getHeight() / frameRow;
                TextureRegion[][] cellRegion = TextureRegion.split(laserSheetTexture, perCellWidth, perCellHeight);
                laserAnimation = new Animation<TextureRegion>(anima_duration, cellRegion[0]);
                laserAnimation.setPlayMode(Animation.PlayMode.LOOP);
                currentFrame = laserAnimation.getKeyFrame(stateTime);
            }
            else if(texture1=="laser/laser_leri_2.png"||texture1=="laser/laser_rile_2.png") {
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
            }
        }

        this.type = type;
        this.weight = weight;
        this.height = height;
        this.fixBoxWeight_constant = fixBoxWeight_constant;
        this.fixBoxHeight_constant = fixBoxHeight_constant;
        this.origin_x = x;
        this.end_x = x+weight;
        this.speed_x = speed_x;
        this.speed_y = speed_y;
        sprite_laser = new Sprite(laserSheetTexture);
        sprite_laser.setPosition(x, y);
        sprite_laser.setSize(weight * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createBox(gameWorld, x, y,
                sprite_laser.getWidth() / GameMode.PPM / 2 + fixBoxWeight_constant,
                sprite_laser.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(sprite_laser.getWidth() / GameMode.PPM / 2, sprite_laser.getHeight() / GameMode.PPM / 2),
                0, 0, 0, false, true, true);
        body.setSleepingAllowed(can_vanish == true);
        body.setUserData(this);

    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        currentFrame = laserAnimation.getKeyFrame(stateTime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, body.getPosition().x, body.getPosition().y, sprite_laser.getWidth() / GameMode.PPM, sprite_laser.getHeight() / GameMode.PPM);
    }

    public Body get_body() {
        return this.body;
    }

    public Sprite get_sprite() {
        return this.sprite_laser;
    }

    public String get_type() {
        return this.type;
    }

    public float get_origin_x() {
        return origin_x;
    }

    public float get_end_x() {
        return end_x;
    }

    public void set_transfrom(float x1,float x2) {
        transform_x1 = x1;
        transform_x2 = x2;
    }

    public void touch_rile() {
        float height = sprite_laser.getHeight();
        float weight = transform_x2-transform_x1;

        sprite_laser.setSize(weight * GameMode.PPM, height);
        ((PolygonShape)body.getFixtureList().first().getShape()).setAsBox(
                sprite_laser.getWidth() / GameMode.PPM / 2 + fixBoxWeight_constant,
                sprite_laser.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(sprite_laser.getWidth() / GameMode.PPM / 2, sprite_laser.getHeight() / GameMode.PPM / 2),
                0);
        body.setTransform( transform_x1 , body.getPosition().y, 0);
    }


    public void left_rile() {
        sprite_laser.setSize(weight * GameMode.PPM, height * GameMode.PPM);
        ((PolygonShape)body.getFixtureList().first().getShape()).setAsBox(
                sprite_laser.getWidth() / GameMode.PPM / 2 + fixBoxWeight_constant,
                sprite_laser.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(sprite_laser.getWidth() / GameMode.PPM / 2, sprite_laser.getHeight() / GameMode.PPM / 2),
                0);
        body.setTransform( origin_x , body.getPosition().y, 0);
    }

    public long get_start() {
        return this.start;
    }

    public void set_startTime() {
        start = TimeUtils.nanoTime();
    }

    public void set_begin_touch(boolean condition) {
        begin_touch = condition;
    }

    public boolean get_begin_touch() {
        return begin_touch;
    }

    public void set_leave(boolean condition) {
        leave = condition;
    }

    public boolean get_leave() {
        return leave;
    }

    public void sleep() {
        body.setActive(false);
    }

    public void awake() {
        body.setActive(true);
    }

    public void move_X(float x0, float x1) {
        if (body.getPosition().x > x1 || body.getPosition().x < x0) {
            speed_x = -speed_x;
        }
        body.setTransform(body.getPosition().x - speed_x, body.getPosition().y, 0);
    }

    public void move_Y(float y0, float y1) {
        if (body.getPosition().y > y1 || body.getPosition().y < y0) {
            speed_y = -speed_y;
        }
        body.setTransform(body.getPosition().x, body.getPosition().y - speed_y, 0);
    }

    public void dispose() {
        laserSheetTexture.dispose();
    }


}
