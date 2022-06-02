package character.interActorObject.Gear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;
import worldBuilding.BuildBody;

public class GearActor_fire extends Actor {

    private final World gameWorld;
    private final Texture fireTexture= new Texture(Gdx.files.internal("gear/fireGun_fire.png"));
    private final Sprite sprite;
    private Body body;

    private boolean isDelete;

    public GearActor_fire(World gameWorld, float x, float y,
                     float weight, float height) {
        this.gameWorld = gameWorld;
        isDelete = false;

        sprite = new Sprite(fireTexture);
        sprite.setSize(weight * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createCircle(gameWorld, x, y, weight / 2, 0, 0, 1,
                false, true, true);

        body.setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, body.getPosition().x - sprite.getWidth() / 2 / GameMode.PPM,
                body.getPosition().y - sprite.getHeight() / 2 / GameMode.PPM,
                sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM);
    }

    @Override
    public void act(float delta) {
        if (isDelete) {
            body.setTransform(100,100,0);
            body.setLinearVelocity(0,0);
            body.setAwake(true);
            body.destroyFixture(body.getFixtureList().first());
            dispose();
            isDelete=false;
        }
    }

    public void dispose() {
        fireTexture.dispose();
    }


    public Body getBody() {
        return body;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public boolean getIsDelete() {
        return isDelete;
    }
}
