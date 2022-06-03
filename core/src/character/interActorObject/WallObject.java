package character.interActorObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;

import worldBuilding.BuildBody;

public class WallObject extends Actor {
    private Body body;
    private String type = "None";
    private float wall_width, wall_height;

    private final Texture texture = new Texture(Gdx.files.internal("wallSample2.png"));
    ;
    private final Sprite sprite;

    // custom wall
    public WallObject(World gameWorld, float x, float y, float wall_width, float wall_height,
                      float fixBoxOrigin_constant, float fixBoxWeight_constant, float fixBoxHeight_constant) {
        this.wall_width = wall_width;
        this.wall_height = wall_height;

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(wall_width * GameMode.PPM, wall_height * GameMode.PPM);

        body = BuildBody.createBox(gameWorld, x, y, wall_width / 2 + fixBoxWeight_constant,
                wall_height / 2 + fixBoxHeight_constant, new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
                        sprite.getHeight() / 2f / GameMode.PPM + fixBoxOrigin_constant),
                0, 0, 0, true, false, false);

        body.setUserData(this);
    }

    // regular wall
    public WallObject(World gameWorld, float x, float y) {

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);

        this.wall_width = sprite.getWidth() / GameMode.PPM;
        this.wall_height = sprite.getHeight() / GameMode.PPM;

        body = BuildBody.createBox(gameWorld, x, y, this.wall_width / 2,
                this.wall_height / 2 - 0.5f, new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
                        sprite.getHeight() / 2f / GameMode.PPM + 0.5f),
                0, 0, 0, true, false, false);
        body.setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, body.getPosition().x, body.getPosition().y,
                sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM);
    }

    public void dispose() {
        texture.dispose();
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public String getType() {
        return type;
    }

    public float getWallHeight() {
        return wall_height;
    }

    public float getWallWeight() {
        return wall_width;
    }

    public Body getBody(){
        return body;
    }
}
