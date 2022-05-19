package character.interActorObject.Laser;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameMode;

import worldBuilding.BuildBody;

public class LaserObject extends Actor {
    private final Texture Laser;
    private Sprite sprite;
    private Body body;
    private String type;
    private float weight;
    private float height;
    private float rotation;
    private float speed_x;
    private float speed_y;
    private long start = 0;

    //private Sound on = Gdx.audio.newSound(Gdx.files.internal(""));
    public LaserObject(World gameWorld, String texture1, String type, float x, float y,
                       float weight, float height, float rotation, float speed_x, float speed_y, float fixBoxOrigin_constant,
                       float fixBoxWeight_constant, float fixBoxHeight_constant) {
        Laser = new Texture(Gdx.files.internal(texture1));
        this.type = type;
        this.weight = weight;
        this.height = height;
        this.rotation = rotation;
        this.speed_x = speed_x;
        this.speed_y = speed_y;
        sprite = new Sprite(Laser);
        sprite.setPosition(x, y);
        sprite.setSize(weight * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createBox(gameWorld, x, y,
                sprite.getWidth() / GameMode.PPM / 2 + fixBoxWeight_constant,
                sprite.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(sprite.getWidth() / GameMode.PPM / 2, sprite.getHeight() / GameMode.PPM / 2),
                0, 0, 0, true, false, true);

        body.isSleepingAllowed();
        body.setUserData(this);
        //on.play(1);

//        ((PolygonShape)body.getFixtureList().first().getShape()).setAsBox(10,10);

    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, body.getPosition().x, body.getPosition().y, sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM, 1, 1, rotation);
    }

    public void touch_leri(float x, float y) {
        float height = sprite.getHeight();
        float weight = Math.abs(body.getPosition().x - x);
        sprite.setSize(weight * GameMode.PPM, height);
    }

    public void touch_doup(float x, float y) {
        float height = Math.abs(body.getPosition().y - y);
        float weight = sprite.getWidth();
        sprite.setSize(weight, height * GameMode.PPM + 100);
    }

    public void left() {
        sprite.setSize(weight * GameMode.PPM, height * GameMode.PPM);
    }

    public String get_type() {
        return this.type;
    }

    public long get_start() {
        return this.start;
    }

    public void set_startTime() {
        start = TimeUtils.nanoTime();
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

//    public void adjust_leri(float t, float r0 ,float r1) {
//    	if(body.getPosition().y > t) {
//    		
//    	}
//    	else if(body.getPosition().y < t) {
//    		sprite.setSize(sprite.getWidth(), r0 );
//    	}
//    }

    public void dispose() {
        Laser.dispose();
    }


}
