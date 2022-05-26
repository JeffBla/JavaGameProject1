package character.interActorObject.Gear;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import worldBuilding.BuildBody;

public class GearActor_FireGun extends Actor {

    private World gameWorld;
    private Body body;
    private float fire_weight, fire_height;
    private Actor attachedActor;

    public GearActor_FireGun(World gameWorld, float fire_weight, float fire_height, Actor attachedActor) {
        this.gameWorld = gameWorld;
        this.fire_weight = fire_weight;
        this.fire_height = fire_height;
        this.attachedActor = attachedActor;

        body = BuildBody.createCircle(gameWorld, attachedActor.getX(), attachedActor.getY(),
                0.5f, 0, 0, 0,
                false, true, true);

    }

    @Override
    public void act(float delta) {
    }

    public void dispose() {

    }

    public Body getBody() {
        return body;
    }

    public void spawnFire(int fire_num, Stage gamestage, float delta) {
        int degCount = -20;

        Vector2 vtr2 = new Vector2(body.getPosition().x - attachedActor.getX() - attachedActor.getOriginX(),
                body.getPosition().y - attachedActor.getY() - attachedActor.getOriginY());

        for (int i = 0; i < fire_num; i++) {
            GearActor_fire fire = new GearActor_fire(gameWorld, body.getPosition().x,
                    body.getPosition().y, fire_weight, fire_height);

            fire.getBody().setLinearVelocity(vtr2.setLength(8).cpy().rotateDeg(degCount));

            gamestage.addActor(fire);

            if (degCount >= 20) {
                degCount = -20;
            } else {
                degCount += i * 3;
            }
        }
    }

}
