package character.mainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameMode;

public class MainCharacter extends Actor {
    final MainCharacterWalk walk;
    final MainCharacterIdle idle;

    public Body body;
    public TextureRegion currentFrame;

    public Vector2 speed = new Vector2(0, 0);
    public float stateTime = 0.0f;
    private final float walkSpeed = 5;  // 5 for game. 10 for test.
    private final float width = 1.7f, height = 1.7f;

    public MainCharacter(World gameWorld, float x, float y) {
        walk = new MainCharacterWalk();
        idle = new MainCharacterIdle();

        currentFrame = idle.idleAnimation.getKeyFrame(stateTime);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(0, 0);
        body = gameWorld.createBody(bd);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.44f, 0.1f, new Vector2(x+width/2, y), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;

        body.createFixture(fixtureDef);

        box.dispose();
    }

    public Vector3 getPosition() {
        return new Vector3(body.getPosition(), 0);
    }

    public Vector3 getPosition(float offsetX, float offsetY) {
        return new Vector3(body.getPosition().x + offsetX, body.getPosition().y + offsetY, 0);
    }

    @Override
    public void act(float delta) {
        keyInput(delta);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, body.getPosition().x, body.getPosition().y, width, height);
    }

    private void keyInput(float delta) {
        boolean isLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean isRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean isUp = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean isDown = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        currentFrame = idle.idleAnimation.getKeyFrame(stateTime);
        body.setLinearVelocity(speed);
        if (isLeft || isRight) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            if (isRight) {
                flipTheAnimRight(walk.walkAnimation);
                flipTheAnimRight(idle.idleAnimation);

                speed.x = walkSpeed;
            }
            if (isLeft) {
                flipTheAnimLeft(walk.walkAnimation);
                flipTheAnimLeft(idle.idleAnimation);
                speed.x = -walkSpeed;
            }
        } else {
            speed.x = 0;
        }
        if (isUp || isDown) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            if (isUp) {
                speed.y = walkSpeed;
            }
            if (isDown) {
                speed.y = -walkSpeed;
            }
        } else {
            speed.y = 0;
        }
    }

    private void flipTheAnimRight(Animation<TextureRegion> anim) {
        for (TextureRegion textureRegion : anim.getKeyFrames())
            if (textureRegion.isFlipX())
                textureRegion.flip(true, false);
    }

    private void flipTheAnimLeft(Animation<TextureRegion> anim) {
        for (TextureRegion textureRegion : anim.getKeyFrames())
            if (!textureRegion.isFlipX())
                textureRegion.flip(true, false);
    }

    public void dispose() {
        walk.dispose();
        idle.dispose();
    }
}
