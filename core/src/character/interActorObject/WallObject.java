package character.interActorObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameMode;

import worldBuilding.BuildBody;

public class WallObject {
    private Body body;

    private Sprite sprite;

    static private final Texture texture = new Texture("wallSample2.png");

    public WallObject(World gameWorld, float x, float y) {
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);

        body = BuildBody.createBox(gameWorld, x, y, sprite.getWidth() / 2f / GameMode.PPM,
                sprite.getHeight() / 2f / GameMode.PPM - GameMode.interActerObject_collisionConstant,
                new Vector2(sprite.getWidth() / 2f / GameMode.PPM, sprite.getHeight() / 2f / GameMode.PPM + GameMode.interActerObject_collisionConstant),
                0, 0, 0, true, false, false);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(sprite, body.getPosition().x, body.getPosition().y,
                sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM);
    }

    public void dispose() {
        texture.dispose();
    }

}
