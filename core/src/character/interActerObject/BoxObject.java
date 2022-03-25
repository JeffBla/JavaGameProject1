package character.interActerObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameMode;

public class BoxObject {
    private Body body;
    private Fixture fixture;

    private Sprite sprite;

    private final Texture texture = new Texture("box.png");

    public BoxObject(World gameWorld, float x, float y) {
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x, y);
        body = gameWorld.createBody(bd);

        PolygonShape box = new PolygonShape();
        box.setAsBox(sprite.getWidth() / 2f /GameMode.PPM, sprite.getHeight() / 2f /GameMode.PPM,
                new Vector2(sprite.getWidth() / 2f /GameMode.PPM, sprite.getHeight() / 2f /GameMode.PPM), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.friction = 15f;

        fixture = body.createFixture(fixtureDef);

        box.dispose();
    }

    public void act(float delta) {
        if(!body.getLinearVelocity().epsilonEquals(0, 0)){
            if(body.getLinearVelocity().x>0){
                body.applyForceToCenter(-fixture.getFriction(), 0, true);
            }if(body.getLinearVelocity().x<0){
                body.applyForceToCenter(fixture.getFriction(), 0, true);
            }if(body.getLinearVelocity().y>0){
                body.applyForceToCenter(0, -fixture.getFriction(), true);
            }if(body.getLinearVelocity().y<0){
                body.applyForceToCenter(0, fixture.getFriction(), true);
            }
            if(body.getLinearVelocity().x <0.5f &&  body.getLinearVelocity().x >-0.5f){
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }if(body.getLinearVelocity().y <0.5f &&  body.getLinearVelocity().y >-0.5f){
                body.setLinearVelocity(body.getLinearVelocity().x, 0);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(sprite, body.getPosition().x, body.getPosition().y, sprite.getWidth() /GameMode.PPM,
                sprite.getHeight() /GameMode.PPM);
    }

    public void dispose() {
        texture.dispose();
    }

}
