package character.mainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.Array;
import kit.FlipAnimation;
import worldBuilding.BuildBody;

public class MainCharacter extends Actor {
    private final Array<Animation<TextureRegion>> actorAnimation;

    private final MainCharacterWalk walk;
    private final MainCharacterIdle idle;
    private final MainCharacterAttack attack;
    private final MainCharacterSoundEffect soundEffect;

    private final World gameWorld;
    public Body body;
    public Body attackDetectRight;
    public Body attackDetectLeft;
    public TextureRegion currentFrame;

    public Vector2 speed = new Vector2(0, 0);
    public float stateTime = 0.0f;
    private final float walkSpeed = 5;  // 5 for game. 10 for test.
    private final float width = 1.7f, height = 1.7f;

    private boolean isLeft = false;
    private boolean isAttack = false;

    public MainCharacter(World gameWorld, float x, float y) {
        this.gameWorld = gameWorld;

        walk = new MainCharacterWalk();
        idle = new MainCharacterIdle();
        attack = new MainCharacterAttack();
        soundEffect = new MainCharacterSoundEffect();

        actorAnimation = new Array<>();
        actorAnimation.addAll(walk.walkAnimation, idle.idleAnimation,
                attack.attackAnim1, attack.attackAnim2, attack.attackAnim3, attack.attackAnimCast);

        currentFrame = idle.idleAnimation.getKeyFrame(stateTime);

        body = BuildBody.createBox(gameWorld, x, y, 0.3f, 0.65f,
                new Vector2(width / 2, height / 2 - 0.2f), 0, 0, 0.2f,
                false, true, false);

        body.setUserData(this);
    }

    public Vector3 getPosition() {
        return new Vector3(body.getPosition(), 0);
    }

    public Vector3 getPosition(float offsetX, float offsetY) {
        return new Vector3(body.getPosition().x + offsetX, body.getPosition().y + offsetY, 0);
    }

    public boolean getIsLeft() {
        return isLeft;
    }

    public boolean getIsAttack() {
        return isAttack;
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        keyInput(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, body.getPosition().x, body.getPosition().y, width, height);
    }

    private final float[] attackDurations = {0, 0.6f, 0.4f, 0.6f};
    private final float attackComboTimeDuration = 1f;
    private float attackDurationCount = 0;
    private float attackComboTimeCount = 0;
    private float attacklevelCount = 1;
    private final float castDuration = 1.2f;
    private float castDurationCount = 0;

    private int attackDetectRightCount = 0;
    private int attackDetectLeftCount = 0;

    private void keyInput(float delta) {
        boolean pressLeft = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean pressRight = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean pressUp = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean pressDown = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean pressAttack = Gdx.input.isKeyPressed(Input.Keys.J);
        boolean pressCast = Gdx.input.isKeyPressed(Input.Keys.K);

        // idle
        currentFrame = idle.idleAnimation.getKeyFrame(stateTime);

        isAttack = false;
        // attack
        if (pressAttack || attackDurationCount != 0) {
            isAttack = true;
            attackComboTimeCount = 0;
            body.setLinearVelocity(0, 0);
            attackDurationCount += delta;

            if (attackComboTimeCount >= attackComboTimeDuration) {
                attacklevelCount = 1;
                attackComboTimeCount = 0;
            } else {
                attackComboTimeCount += delta;
            }

            // attack detect region
            if (!isLeft && attackDetectRightCount != 1) {
                attackDetectRight = BuildBody.createBox(gameWorld, 0, 0, 0.3f * 2 - 0.12f, 0.65f,
                        new Vector2(body.getPosition().x + width / 2 + 0.2f, body.getPosition().y + height / 2 - 0.2f),
                        0, 0, 0, false, true, true);
                attackDetectRight.setUserData(this);
                attackDetectRightCount++;
            } else if (isLeft && attackDetectLeftCount != 1) {
                attackDetectLeft = BuildBody.createBox(gameWorld, 0, 0, 0.3f * 2 - 0.12f, 0.65f,
                        new Vector2(body.getPosition().x + width / 2 - 0.2f, body.getPosition().y + height / 2 - 0.2f),
                        0, 0, 0, false, true, true);
                attackDetectLeft.setUserData(this);
                attackDetectLeftCount++;
            }

            if (attacklevelCount == 1 && attackDurationCount <= attackDurations[1]) {
                currentFrame = attack.attackAnim1.getKeyFrame(attackDurationCount);
                soundEffect.playSword_shoosh_sound();
                return;
            } else if (attacklevelCount == 2 && attackDurationCount <= attackDurations[2]) {
                currentFrame = attack.attackAnim2.getKeyFrame(attackDurationCount);
                soundEffect.playSword_shoosh_sound();
                return;
            } else if (attacklevelCount == 3 && attackDurationCount <= attackDurations[3]) {
                currentFrame = attack.attackAnim3.getKeyFrame(attackDurationCount);
                soundEffect.playSword_cut_air_sound();
                return;
            }
        }
        if (attacklevelCount == 1 && attackDurationCount > attackDurations[1]) {
            attacklevelCount++;
            attackDurationCount = 0;

            DestoryAttackDetect();
        }
        if (attacklevelCount == 2 && attackDurationCount > attackDurations[2]) {
            attacklevelCount++;
            attackDurationCount = 0;

            DestoryAttackDetect();
        }
        if (attacklevelCount == 3 && attackDurationCount > attackDurations[3]) {
            attacklevelCount = 1;
            attackDurationCount = 0;

            DestoryAttackDetect();
        }

        // cast
        if (pressCast || castDurationCount != 0) {
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
        if (pressLeft || pressRight) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            if (pressRight) {
                isLeft = false;
                FlipAnimation.flipAnim_ArrayRight(actorAnimation);
                speed.x = walkSpeed;
            }
            if (pressLeft) {
                isLeft = true;
                FlipAnimation.flipAnim_ArrayLeft(actorAnimation);
                speed.x = -walkSpeed;
            }
        } else {
            speed.x = 0;
        }
        if (pressUp || pressDown) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            if (pressUp) {
                speed.y = walkSpeed;
            }
            if (pressDown) {
                speed.y = -walkSpeed;
            }
        } else {
            speed.y = 0;
        }

        // stop Runing Sound
        if (pressDown || pressRight || pressUp || pressLeft) {
            soundEffect.playRun_sound();
        } else {
            soundEffect.stopRun_sound();
        }
        if (pressAttack) {
            soundEffect.stopRun_sound();
        }
    }

    private void DestoryAttackDetect() {
        if (attackDetectLeftCount == 1) {
            gameWorld.destroyBody(attackDetectLeft);
            attackDetectLeftCount = 0;
        }
        if (attackDetectRightCount == 1) {
            gameWorld.destroyBody(attackDetectRight);
            attackDetectRightCount = 0;
        }
    }

    public void dispose() {
        walk.dispose();
        idle.dispose();
        attack.dispose();
    }
}
