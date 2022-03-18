package character.mainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class MainCharacter {
    final MainCharacterWalk walk;
    final MainCharacterIdle idle;

    public Rectangle rigid_body;
    public TextureRegion currentFrame;

    public float stateTime = 0.0f;
    public int walkSpeed = 500;

    public MainCharacter() {
        walk = new MainCharacterWalk();
        idle = new MainCharacterIdle();

        rigid_body = new Rectangle();
        /* I don't know exactly. */
        rigid_body.height = 30;
        rigid_body.width = 103;
        rigid_body.x = 0;
        rigid_body.y = 0;
    }

    public void keyInput(float delta) {
        currentFrame = idle.idleAnimation.getKeyFrame(stateTime);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            flipTheAnimRight(walk.walkAnimation);
            flipTheAnimRight(idle.idleAnimation);
            rigid_body.x += walkSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            flipTheAnimLeft(walk.walkAnimation);
            flipTheAnimLeft(idle.idleAnimation);
            rigid_body.x -= walkSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            rigid_body.y += walkSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            currentFrame = walk.walkAnimation.getKeyFrame(stateTime);
            rigid_body.y -= walkSpeed * delta;
        }
    }

    private void flipTheAnimRight(Animation<TextureRegion> anim){
        for (TextureRegion textureRegion : anim.getKeyFrames())
            if (textureRegion.isFlipX())
                textureRegion.flip(true, false);
    }
    private void flipTheAnimLeft(Animation<TextureRegion> anim){
        for (TextureRegion textureRegion : anim.getKeyFrames())
            if (!textureRegion.isFlipX())
                textureRegion.flip(true, false);
    }

    public void dispose() {
        walk.dispose();
        idle.dispose();
    }
}
