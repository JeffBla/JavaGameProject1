package character.interActorObject.Gear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kit.BodyEditorLoader;

public class GearActor extends Image {

    private Body body;
    private World world;
    private float angle;

    public GearActor(World aWorld, float pos_x, float pos_y, float aWidth, float aHeight) {
        super(new Texture("gear/gear.png"));
        this.setSize(aWidth, aHeight);
        this.setPosition(pos_x, pos_y);
        this.setOrigin(this.getWidth()/2,this.getHeight()/2);


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

        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        body.setAngularVelocity(1);
        body.setUserData(this);

        {
            float X_right = 44, X_left = 31, Y_bottom = 6, Y_top = 12;

            ParallelAction topLeftRightParallelAction = new ParallelAction();
            topLeftRightParallelAction.addAction(Actions.moveTo(X_right, Y_top, 1, Interpolation.elastic));
            topLeftRightParallelAction.addAction(Actions.scaleTo(2, 2, 1, Interpolation.elastic));

            MoveToAction moveBottomRightAction = new MoveToAction();
            moveBottomRightAction.setPosition(X_right, Y_bottom);
            moveBottomRightAction.setDuration(1);
            moveBottomRightAction.setInterpolation(Interpolation.smooth);


            ParallelAction bottomLeftRightParallelAction = new ParallelAction();
            bottomLeftRightParallelAction.addAction(Actions.moveTo(X_left, Y_bottom, 1, Interpolation.sineOut));
            bottomLeftRightParallelAction.addAction(Actions.scaleTo(1, 1, 1));

            ParallelAction leftBottomTopParallelAction = new ParallelAction();
            leftBottomTopParallelAction.addAction(Actions.moveTo(X_left, Y_top, 1, Interpolation.swingOut));
            leftBottomTopParallelAction.addAction(Actions.rotateBy(90, 1));

            SequenceAction overallSequence = new SequenceAction();
            overallSequence.addAction(topLeftRightParallelAction);
            overallSequence.addAction(moveBottomRightAction);
            overallSequence.addAction(bottomLeftRightParallelAction);
            overallSequence.addAction(leftBottomTopParallelAction);

            RepeatAction infiniteLoop = new RepeatAction();
            infiniteLoop.setCount(RepeatAction.FOREVER);
            infiniteLoop.setAction(overallSequence);

            this.addAction(infiniteLoop);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        /** first case: don't have below
         *  second case: have below
        */
//        this.setPosition(body.getPosition().x - this.getWidth() / 2, body.getPosition().y - this.getHeight() / 2);

    }
}
