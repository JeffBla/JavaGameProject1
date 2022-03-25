package character.mainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameMode;

public class MainCharacter extends Actor {
    private final MainCharacterWalk walk;
    private final MainCharacterIdle idle;
    private final MainCharacterAttack attack;

    public Body body;
    public TextureRegion currentFrame;

    public Vector2 speed = new Vector2(0, 0);
    public float stateTime = 0.0f;
    private final float walkSpeed = 5;  // 5 for game. 10 for test.
    private final float width = 1.7f, height = 1.7f;

    public MainCharacter(World gameWorld, float x, float y) {
        walk = new MainCharacterWalk();
        idle = new MainCharacterIdle();
        attack = new MainCharacterAttack();

        currentFrame = idle.idleAnimation.getKeyFrame(stateTime);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(0, 0);
        body = gameWorld.createBody(bd);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.44f, 0.1f, new Vector2(x + width / 2, y), 0);

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

    private final float[] attackDurations = {0, 0.6f, 0.4f, 0.6f};
    private final float attackComboTimeDuration = 1f;
    private float attackDurationCount = 0;
    private float attackComboTimeCount = 0;
    private float attacklevelCount = 1;
    private final float castDuration = 1.2f;
    private float castDurationCount = 0;

    private void keyInput(float delta) {
        boolean isLeft = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean isRight = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean isUp = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean isDown = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean isAttack = Gdx.input.isKeyPressed(Input.Keys.J);
        boolean isCast = Gdx.input.isKeyPressed(Input.Keys.K);

        // idle
        currentFrame = idle.idleAnimation.getKeyFrame(stateTime);

        // attack
        if (isAttack || attackDurationCount != 0) {
            attackComboTimeCount = 0;
            body.setLinearVelocity(0, 0);
            attackDurationCount += delta;
            if (attacklevelCount == 1 && attackDurationCount <= attackDurations[1]) {
                currentFrame = attack.attackAnim1.getKeyFrame(attackDurationCount);
            } else if (attacklevelCount == 2 && attackDurationCount <= attackDurations[2]) {
                currentFrame = attack.attackAnim2.getKeyFrame(attackDurationCount);
            } else if (attacklevelCount == 3 && attackDurationCount <= attackDurations[3]) {
                currentFrame = attack.attackAnim3.getKeyFrame(attackDurationCount);
            }
        }
        if (attackComboTimeCount >= attackComboTimeDuration) {
            attacklevelCount = 1;
            attackComboTimeCount = 0;
        } else {
            attackComboTimeCount += delta;
        }
        if (attacklevelCount == 1 && attackDurationCount > attackDurations[1]) {
            attacklevelCount++;
            attackDurationCount = 0;
        }
        if (attacklevelCount == 2 && attackDurationCount > attackDurations[2]) {
            attacklevelCount++;
            attackDurationCount = 0;
        }
        if (attacklevelCount == 3 && attackDurationCount > attackDurations[3]) {
            attacklevelCount = 1;
            attackDurationCount = 0;
        }

        // cast
        if (isCast || castDurationCount != 0) {
            body.setLinearVelocity(0, 0);
            castDurationCount += delta;
            if (castDurationCount <= castDuration) {
                currentFrame = attack.attackAnimCast.getKeyFrame(castDurationCount);
                return;
            }
        }
        if (castDurationCount > castDuration) castDurationCount = 0;

        // move
        body.setLinearVelocity(speed);
        if (isLeft || isRight) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            if (isRight) {
                flipTheAnimRight(walk.walkAnimation);
                flipTheAnimRight(idle.idleAnimation);
                flipTheAnimRight(attack.attackAnim1);
                flipTheAnimRight(attack.attackAnim2);
                speed.x = walkSpeed;
            }
            if (isLeft) {
                flipTheAnimLeft(walk.walkAnimation);
                flipTheAnimLeft(idle.idleAnimation);
                flipTheAnimLeft(attack.attackAnim1);
                flipTheAnimLeft(attack.attackAnim2);
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
