package worldBuilding;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class BuildBody {
    private final static BodyDef bd = new BodyDef();
    private final static FixtureDef fixtureDef = new FixtureDef();
    private final static WeldJointDef weldJointDef = new WeldJointDef();
    private final static PrismaticJointDef prismaticJointDef = new PrismaticJointDef();

    public static Body createEdge(World world, float body_posX, float body_posY, float v1_X, float v1_Y,
                                  float v2_X, float v2_Y, float friction,
                                  boolean isStaticBody, boolean isDynamicBody, boolean isSensor) {
        Body edge;

        if (isStaticBody)
            bd.type = BodyDef.BodyType.StaticBody;
        else if (isDynamicBody)
            bd.type = BodyDef.BodyType.DynamicBody;
        else
            bd.type = BodyDef.BodyType.KinematicBody;

        bd.position.set(body_posX, body_posY);
        edge = world.createBody(bd);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(v1_X, v1_Y, v2_X, v2_Y);

        fixtureDef.shape = edgeShape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.friction = friction;
        edge.createFixture(fixtureDef);

        edgeShape.dispose();

        return edge;
    }

    public static Body createBox(World world, float body_posX, float body_posY, float halfWidth, float halfHeight,
                                 Vector2 center, float density, float angle, float fiction,
                                 boolean isStaticBody, boolean isDynamicBody, boolean isSensor) {
        Body box;
        if (isDynamicBody)
            bd.type = BodyDef.BodyType.DynamicBody;
        else if (isStaticBody)
            bd.type = BodyDef.BodyType.StaticBody;
        else
            bd.type = BodyDef.BodyType.KinematicBody;

        bd.position.set(body_posX, body_posY);
        box = world.createBody(bd);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(halfWidth, halfHeight, center, angle);

        fixtureDef.friction = fiction;
        fixtureDef.density = density;
        fixtureDef.isSensor = isSensor;
        fixtureDef.shape = polygonShape;
        box.createFixture(fixtureDef);

        polygonShape.dispose();

        return box;
    }

    public static Body createCircle(World world, float posX, float posY, float Radius, float friction, float density,
                                    boolean isStaticBody, boolean isDynamicBody, boolean isSensor) {
        Body ball;

        if (isStaticBody)
            bd.type = BodyDef.BodyType.StaticBody;
        else if (isDynamicBody)
            bd.type = BodyDef.BodyType.DynamicBody;
        else
            bd.type = BodyDef.BodyType.KinematicBody;

        bd.position.set(posX, posY);
        ball = world.createBody(bd);

        CircleShape circle = new CircleShape();
        circle.setRadius(Radius);

        fixtureDef.shape = circle;
        fixtureDef.friction = friction;
        fixtureDef.isSensor = isSensor;
        fixtureDef.density = density;
        ball.createFixture(fixtureDef);

        circle.dispose();

        return ball;
    }

    /**
     * Ax, Ay, Bx, By is the offset of the origin point
     */
    public static WeldJoint createWeldJoint(World world, Body bodyA, Body bodyB, float frequency,
                                               float Ax, float Ay, float Bx, float By,
                                               float damping, Boolean isCollide) {
        weldJointDef.bodyA = bodyA;
        weldJointDef.bodyB = bodyB;
        weldJointDef.frequencyHz = frequency;
        weldJointDef.dampingRatio = damping;
        weldJointDef.localAnchorA.set(Ax, Ay);
        weldJointDef.localAnchorB.set(Bx, By);
        weldJointDef.collideConnected = isCollide;
        return (WeldJoint) world.createJoint(weldJointDef);
    }

    public static PrismaticJoint createPrismaticJoint(World world, Body bodyA, Body bodyB,
                                                         float Ax, float Ay, float Bx, float By,
                                                         Boolean isCollide, Boolean isEnableLimit,
                                                         float lowerTranslation, float upperTranslation) {
        prismaticJointDef.bodyA = bodyA;
        prismaticJointDef.bodyB = bodyB;
        prismaticJointDef.localAnchorA.set(Ax, Ay);
        prismaticJointDef.localAnchorB.set(Bx, By);
        prismaticJointDef.enableLimit = isEnableLimit;
        prismaticJointDef.lowerTranslation = lowerTranslation;
        prismaticJointDef.upperTranslation = upperTranslation;
        prismaticJointDef.collideConnected = isCollide;
        return (PrismaticJoint) world.createJoint(prismaticJointDef);
    }
}
