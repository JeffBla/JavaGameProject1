package character.interActorObject;

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
    private Body trigger;
    private String type = "None";

    private static final Texture texture = new Texture("wallSample2.png");

    private final Sprite sprite;

    // custom wall
    public WallObject(World gameWorld, float x, float y, float weight, float height,
                      float fixBoxOrigin_constant, float fixBoxWeight_constant, float fixBoxHeight_constant) {
        trigger = null;

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(weight * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createBox(gameWorld, x, y, weight / 2 + fixBoxWeight_constant,
                height / 2 + fixBoxHeight_constant, new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
                        sprite.getHeight() / 2f / GameMode.PPM + fixBoxOrigin_constant),
                0, 0, 0, true, false, false);
    }

    // regular wall
    public WallObject(World gameWorld, float x, float y) {
        trigger = null;

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);

        body = BuildBody.createBox(gameWorld, x, y, sprite.getWidth() / 2f / GameMode.PPM,
                sprite.getHeight() / 2f / GameMode.PPM - 0.5f
                , new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
                        sprite.getHeight() / 2f / GameMode.PPM + 0.5f),
                0, 0, 0, true, false, false);
    }

    public void setTrigger(World gameWorld, float x, float y, float weight, float height,
                           float fixBoxOrigin_constant, float fixBoxWeight_constant, float fixBoxHeight_constant) {

        trigger = BuildBody.createBox(gameWorld, x, y, weight / 2 + fixBoxWeight_constant,
                height / 2 + fixBoxHeight_constant, new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
                        sprite.getHeight() / 2f / GameMode.PPM + fixBoxOrigin_constant),
                0, 0, 0, true, false, true);

        body.setUserData(this);
    }

    public void setTrigger(World gameWorld, float fixBoxOrigin_constant, float fixBoxWeight_constant, float fixBoxHeight_constant) {

        setTrigger(gameWorld, body.getPosition().x, body.getPosition().y,
                sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM,
                fixBoxOrigin_constant, fixBoxWeight_constant, fixBoxHeight_constant);
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
}
