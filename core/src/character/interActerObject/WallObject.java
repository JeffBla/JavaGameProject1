package character.interActerObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameMode;

public class WallObject {
    private Body body;

    private Sprite sprite;

    static private final Texture texture = new Texture("wallSample2.png");

    public WallObject(World gameWorld, float x, float y) {
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(x, y);
        body = gameWorld.createBody(bd);

        PolygonShape box = new PolygonShape();
        box.setAsBox(sprite.getWidth()/2f /GameMode.PPM, sprite.getHeight()/2f /GameMode.PPM,
                new Vector2(sprite.getWidth()/2f /GameMode.PPM, sprite.getHeight()/2f /GameMode.PPM), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;

        body.createFixture(fixtureDef);

        box.dispose();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(sprite, body.getPosition().x, body.getPosition().y,
                sprite.getWidth() /GameMode.PPM, sprite.getHeight() /GameMode.PPM);
    }

    public void dispose() {
        texture.dispose();
    }

}
