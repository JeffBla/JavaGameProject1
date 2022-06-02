package character.interActorObject.Gear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import kit.BodyEditorLoader;
import worldBuilding.BuildBody;

import java.util.HashMap;

public class GearActor extends Image {

    private final World world;
    private float angle;
    private Body body;
    private float gear_angularVelocity = 1;

    private BossAction bossAction;
    private GearActor_FireGun gearActor_fireGunLeftBottom;
    private GearActor_FireGun gearActor_fireGunRightBottom;
    private GearActor_FireGun gearActor_fireGunRightUp;
    private GearActor_FireGun gearActor_fireGunLeftUp;
    private GearActor_hp hp;
    private HashMap<String, GearActor_FireGun> gearActor_fireGunHashMap;

    private boolean isInjure = false;
    private float injureCounter = 0;
    private final float invincibleTime_afterInjure = 0.3f;
    private float shoot_FireBall_Counter = 0;
    private final float shoot_FireBall_interval = 5;
    private final float shoot_FireBall_Duration = 1;

    public GearActor(World aWorld, float pos_x, float pos_y, float aWidth, float aHeight) {
        super(new Texture("gear/gear.png"));
        this.setSize(aWidth, aHeight);
        this.setPosition(pos_x, pos_y);
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        world = aWorld;
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("gear/box2d_scene.json"));

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.KinematicBody;
        bd.position.x = this.getX();
        bd.position.y = this.getY();

        body = world.createBody(bd);


        // 2. Create a FixtureDef, as usual.
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        // 3. Create a Body, as usual.

        float scale = this.getWidth();
        loader.attachFixture(body, "gear", fd, scale);

        body.setAngularVelocity(gear_angularVelocity);
        body.setUserData(this);

        bossAction = new BossAction();

        gearActor_fireGunLeftBottom = new GearActor_FireGun(aWorld, 0.8f, 1f, this);
        gearActor_fireGunRightBottom = new GearActor_FireGun(aWorld, 0.8f, 1f, this);
        gearActor_fireGunRightUp = new GearActor_FireGun(aWorld, 0.8f, 1f, this);
        gearActor_fireGunLeftUp = new GearActor_FireGun(aWorld, 0.8f, 1f, this);
        hp = new GearActor_hp();
        gearActor_fireGunHashMap = new HashMap<>();
        gearActor_fireGunHashMap.put("LeftBottom", gearActor_fireGunLeftBottom);
        gearActor_fireGunHashMap.put("RightBottom", gearActor_fireGunRightBottom);
        gearActor_fireGunHashMap.put("RightUp", gearActor_fireGunRightUp);
        gearActor_fireGunHashMap.put("LeftUp", gearActor_fireGunLeftUp);

        BuildBody.createWeldJoint(aWorld, this.body, gearActor_fireGunLeftBottom.getBody(),
                0, aWidth / 2, -aHeight / 2, 0, 0, 0, false);
        BuildBody.createWeldJoint(aWorld, this.body, gearActor_fireGunRightBottom.getBody(), 0,
                aWidth / 2, aHeight / 2, 0, 0, 0, false);
        BuildBody.createWeldJoint(aWorld, this.body, gearActor_fireGunRightUp.getBody(), 0,
                -aWidth / 2, aHeight / 2, 0, 0, 0, false);
        BuildBody.createWeldJoint(aWorld, this.body, gearActor_fireGunLeftUp.getBody(), 0,
                -aWidth / 2, -aHeight / 2, 0, 0, 0, false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        body.setTransform(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, body.getAngle());

        if (isInjure) {
            injureCounter += delta;
            if (injureCounter >= invincibleTime_afterInjure) {
                injureCounter = 0;
                isInjure = false;
            }
        }
        hp.render(delta);
        if ((hp.getHp() <= 100 && hp.getHp() > 50) || (hp.getHp() <= 30 && hp.getHp() != 0)) {
            if (!this.hasActions()) {
                float X_right = 44, X_left = 31, Y_bottom = 4, Y_top = 12;

                this.addAction(bossAction.action1Move(X_right, X_left, Y_bottom, Y_top));
            } else if (hp.getHp() <= 30) {
                body.setAngularVelocity(gear_angularVelocity);

                this.removeAction(bossAction.getAction2());
            }
        } else if (hp.getHp() <= 50 && hp.getHp() == 0) {
            this.removeAction(bossAction.getAction1());
            body.setAngularVelocity(0);
            float pos_X = 37, pos_Y = 8;

            if (!this.hasActions()) {
                this.addAction(bossAction.action2Move(pos_X, pos_Y));
            }
        }

        for (Actor actor : gearActor_fireGunHashMap.values()) {
            actor.act(delta);
        }
    }

    public HashMap<String, GearActor_FireGun> getGearActor_fireGunHashMap() {
        return gearActor_fireGunHashMap;
    }

    public void shoot_FireBall(Stage gameStage, float delta) {
        shoot_FireBall_Counter += delta;
        if (shoot_FireBall_Counter >= shoot_FireBall_interval) {
            getGearActor_fireGunHashMap().get("LeftBottom").spawnFire(5, gameStage, delta);
            getGearActor_fireGunHashMap().get("RightBottom").spawnFire(5, gameStage, delta);
            getGearActor_fireGunHashMap().get("RightUp").spawnFire(5, gameStage, delta);
            getGearActor_fireGunHashMap().get("LeftUp").spawnFire(5, gameStage, delta);

            if (shoot_FireBall_Counter >= shoot_FireBall_interval + shoot_FireBall_Duration)
                shoot_FireBall_Counter = 0;
        }
    }

    public float getHp() {
        return hp.getHp();
    }

    public void deHp(int injure) {
        if (!isInjure) {
            hp.deHp(injure);
            isInjure = true;
        }
    }

}
