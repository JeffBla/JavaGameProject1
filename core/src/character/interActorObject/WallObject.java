package character.interActorObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;

import worldBuilding.BuildBody;

public class WallObject extends Actor {
    private Body body;
    private String type = "None";
    private float weight, height;

    private final Texture texture = new Texture(Gdx.files.internal("wallSample2.png"));
    ;

    private final Sprite sprite;

    // custom wall
    public WallObject(World gameWorld, float x, float y, float weight, float height,
                      float fixBoxOrigin_constant, float fixBoxWeight_constant, float fixBoxHeight_constant) {
        this.weight = weight;
        this.height = height;

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(weight * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createBox(gameWorld, x, y, weight / 2 + fixBoxWeight_constant,
                height / 2 + fixBoxHeight_constant, new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
                        sprite.getHeight() / 2f / GameMode.PPM + fixBoxOrigin_constant),
                0, 0, 0, true, false, false);

        body.setUserData(this);
    }

    // regular wall
    public WallObject(World gameWorld, float x, float y) {

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);

        this.weight = sprite.getWidth() / GameMode.PPM;
        this.height = sprite.getHeight() / GameMode.PPM;

        body = BuildBody.createBox(gameWorld, x, y, this.weight / 2,
                this.height / 2 - 0.5f, new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
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
        return height;
    }

    public float getWallWeight() {
        return weight;
    }
}
