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
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kit.BodyEditorLoader;
import worldBuilding.BuildBody;

import java.util.HashMap;

public class GearActor extends Image {

    private World world;
    private float angle;
    private Body body;
    private float gear_angularVelocity = 1;

    private BossAction bossAction;
    private GearActor_FireGun gearActor_fireGunLeftBottom;
    private GearActor_FireGun gearActor_fireGunRightBottom;
    private GearActor_FireGun gearActor_fireGunRightUp;
    private GearActor_FireGun gearActor_fireGunLeftUp;
    private float hp=100;
    private Texture healthBar_frame;
    private Texture healthBar_blood;
    private HashMap<String, GearActor_FireGun> gearActor_fireGunHashMap;

    public GearActor(World aWorld, float pos_x, float pos_y, float aWidth, float aHeight) {
        super(new Texture("gear/gear.png"));
        this.setSize(aWidth, aHeight);
        this.setPosition(pos_x, pos_y);
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        healthBar_frame = new Texture(Gdx.files.internal("healthBar/healthBar_frame.png"));
        healthBar_blood = new Texture(Gdx.files.internal("healthBar/healthBar_blood.png"));

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

        float X_right = 44, X_left = 31, Y_bottom = 6, Y_top = 12;
        bossAction = new BossAction(X_right, X_left, Y_bottom, Y_top);

        this.addAction(bossAction.action1Move(X_right, X_left, Y_bottom, Y_top));

        gearActor_fireGunLeftBottom = new GearActor_FireGun(aWorld, 0.8f, 1f, this);
        gearActor_fireGunRightBottom = new GearActor_FireGun(aWorld, 0.8f, 1f, this);
        gearActor_fireGunRightUp = new GearActor_FireGun(aWorld, 0.8f, 1f, this);
        gearActor_fireGunLeftUp = new GearActor_FireGun(aWorld, 0.8f, 1f, this);

        gearActor_fireGunHashMap = new HashMap<>();
        gearActor_fireGunHashMap.put("LeftBottom", gearActor_fireGunLeftBottom);
        gearActor_fireGunHashMap.put("RightBottom", gearActor_fireGunRightBottom);
        gearActor_fireGunHashMap.put("RightUp", gearActor_fireGunRightUp);
        gearActor_fireGunHashMap.put("LeftUp", gearActor_fireGunLeftUp);

        BuildBody.createWeldJoint(aWorld, this.body, gearActor_fireGunLeftBottom.getBody(),
                0, aWidth/2, -aHeight/2, 0, 0, 0, false);
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
        batch.draw(healthBar_frame, 2, 1.35f, 47, 1);
        batch.draw(healthBar_blood, 2, 1.35f, (hp/100)*47, 1);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        body.setTransform(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, body.getAngle());

        for (Actor actor : gearActor_fireGunHashMap.values()) {
            actor.act(delta);
        }
    }

    public HashMap<String, GearActor_FireGun> getGearActor_fireGunHashMap() {
        return gearActor_fireGunHashMap;
    }
}
