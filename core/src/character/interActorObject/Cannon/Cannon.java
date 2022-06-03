package character.interActorObject.Cannon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

public class Cannon extends Actor{
    private final CannonBase base;
    private final CannonLine line;
    private final CannonWarningLine warningLine;
    private long start=0;
    public Cannon(World gameWorld, Body bodyMainCharacter, float x, float y, float width, float height, float speed) {
        base = new CannonBase(gameWorld, bodyMainCharacter , x, y, width, height, speed, 0, 0, 0f);
        line = new CannonLine(gameWorld, base.getBody(), 40f, 1f, 0.04f, 0.5f, 0f, -0.2f);
        warningLine = new CannonWarningLine(gameWorld, base.getBody(), 40f, 1f, 0.1f, 0.5f, 0f, -0.2f);
    }

    @Override
    public void act(float delta) {
        base.act(delta);
        if(base.getMove()==false&&base.getTarget()==true) {
            warningLine.setAim(true);
        }
        warningLine.act(delta);
        line.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        base.draw(batch, parentAlpha);
        if(warningLine.getAim()==true) {
            warningLine.draw(batch,parentAlpha);
        }
        if(line.getAttack()==true) {
            line.draw(batch, parentAlpha);
        }
    }
    public long getStart() {
        return this.start;
    }

    public void setStartTime(long time) {
        start = time;
    }

    public CannonBase getBase() {
        return base;
    }

    public CannonWarningLine getWarningLine() {
        return warningLine;
    }

    public void aim() {
        base.setMove(false);
        warningLine.awake();
        warningLine.setAim(true);
    }

    public void attack() {
        warningLine.setAim(false);
        warningLine.sleep();
        warningLine.setVisible(false);
        line.awake();
        line.setAttack(true);
        line.setVisible(true);
    }

    public void reDestination() {
        line.setAttack(false);
        line.setVisible(false);
        line.sleep();
        base.setTarget(false);
        start = TimeUtils.nanoTime();
    }

    public void BeAttacked() {
        base.getBody().setTransform(100, 100, 0);
        base.setVisible(false);
        line.setVisible(false);
        warningLine.setVisible(false);
        line.setAttack(false);
        base.setOnRecover(true);
        start = TimeUtils.nanoTime();
    }

    public void reset() {
        base.getBody().setTransform(15,15,0);
        base.setVisible(true);
        line.setVisible(true);
        warningLine.setVisible(true);
        base.setTarget(false);
        base.setMove(false);
        line.setAttack(false);
        warningLine.setAim(false);
        base.setOnRecover(false);
        start = TimeUtils.nanoTime();
    }

    public void dispose() {
        line.dispose();
        base.dispose();
        warningLine.dispose();
    }
}
