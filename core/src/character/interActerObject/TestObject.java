package character.interActerObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TestObject extends Image{
    Body body;

    static private final Texture texture = new Texture("wallSample.png");

    public TestObject(World gameWorld, float x, float y) {
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(x, y);
        body = gameWorld.createBody(bd);

        PolygonShape box = new PolygonShape();
        box.setAsBox(texture.getWidth()/2f, texture.getHeight()/2f,
                new Vector2(texture.getWidth()/2f, texture.getHeight()/2f), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;

        body.createFixture(fixtureDef);

        box.dispose();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x, body.getPosition().y);
    }

    public void dispose() {
        texture.dispose();
    }

}
