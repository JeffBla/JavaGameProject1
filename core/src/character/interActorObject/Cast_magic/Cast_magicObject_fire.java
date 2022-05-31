package character.interActorObject.Cast_magic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import worldBuilding.BuildBody;

public class Cast_magicObject_fire extends Actor {

    private final World gameWord;
    private final Cast_magic_FireAnim animFly;
    private Body body;

    private TextureRegion currentFrame;
    private final float weight, height, radius;
    private float stateTime = 0.0f;
    private float speed = 10;
    private boolean isSpawn;
    private boolean isHit;

    public Cast_magicObject_fire(World gameWorld, float x, float y,
                                 float weight, float height) {
        this.gameWord = gameWorld;

        this.weight = weight;
        this.height = height;
        this.radius = height;
        isSpawn = true;
        isHit = false;

        animFly = new Cast_magic_FireAnim();

        body = BuildBody.createCircle(gameWorld, x, y, radius / 2, 0, 0, 0,
                false, true, true);

        body.setUserData(this);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (isSpawn || !animFly.getSpawnAnimation().isAnimationFinished(stateTime)) {
            currentFrame = animFly.getSpawnAnimation().getKeyFrame(stateTime);
            isSpawn = false;
        } else if (isHit) {
            currentFrame = animFly.getHitAnimation().getKeyFrame(stateTime);
        } else {
            currentFrame = animFly.getFlyAnimation().getKeyFrame(stateTime);
        }

        batch.draw(currentFrame, body.getPosition().x - radius,
                body.getPosition().y - radius / 2,
                weight, height);

    }

    @Override
    public void act(float delta) {
        stateTime += delta;

        if (animFly.getSpawnAnimation().isAnimationFinished(stateTime)) {
            body.setLinearVelocity(speed, 0);
        }
        if (isHit) {
            body.setLinearVelocity(0,0);
            if(animFly.getHitAnimation().isAnimationFinished(stateTime)) {
                body.setTransform(100, 100, 0);
                body.setLinearVelocity(0, 0);
                body.destroyFixture(body.getFixtureList().first());
                isHit = false;
                dispose();
            }
        }
    }

    public void dispose() {
        animFly.dispose();
    }

    public Body getBody() {
        return body;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setToLeftSpeed() {
        speed = -speed;
    }

    public Cast_magic_FireAnim getAnimFly() {
        return animFly;
    }
}
