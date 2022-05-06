package character.enemy.robot;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import worldBuilding.BuildBody;

public class Enemy_robot extends Actor {
    private final Array<Animation<TextureRegion>> actorAnimation;

    private Enemy_robotIdle idle;
    private Enemy_robotWalk walk;

    private World gameWorld;
    public Body body;
    public TextureRegion currentFrame;

    public float stateTime = 0.0f;

    public final float init_walkSpeed = 5;  // 5 for game. 10 for test.
    public Vector2 speed = new Vector2(init_walkSpeed, 0);
    private final float width = 2.5f, height = 3f;

    private boolean isLeft = false;
    private boolean isAttack = false;
    private boolean isWalk = true;

    public Enemy_robot(World gameWorld, float x, float y) {
        this.gameWorld = gameWorld;

        idle = new Enemy_robotIdle();
        walk = new Enemy_robotWalk();

        actorAnimation = new Array<>();
        actorAnimation.addAll(idle.idleAnimation, walk.walkAnimation);

        currentFrame = idle.idleAnimation.getKeyFrame(stateTime);

        body = BuildBody.createBox(gameWorld, x, y, width / 2 - 0.3f, height / 2 - 0.3f,
                new Vector2(width / 2, height / 2 - 0.3f), 0, 0, 0.2f,
                false, true, true);

        body.setUserData(this);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        if (!isWalk) {
            // idle
            currentFrame = idle.idleAnimation.getKeyFrame(stateTime);
        } else {
            // move
            body.setLinearVelocity(speed);
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, body.getPosition().x, body.getPosition().y, width, height);
    }

    public void dispose() {
        idle.dispose();

    }

    public Array<Animation<TextureRegion>> getActorAnimation() {
        return actorAnimation;
    }

    public void setIsLeft(boolean left) {
        isLeft = left;
    }

    public boolean getIsLeft() {
        return isLeft;
    }

    public void setSpeed(float x, float y) {
        speed = new Vector2(x, y);
    }
    public Vector2 getSpeed() {
        return speed;
    }
}
