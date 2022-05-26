package character.interActorObject.Gear;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class BossAction extends Action {


    public BossAction(float X_right, float X_left, float Y_bottom, float Y_top) {

    }

    public Action action1Move(float X_right, float X_left, float Y_bottom, float Y_top){
        MoveToAction moveTopLeftRightAction = new MoveToAction();
        moveTopLeftRightAction.setPosition(X_right, Y_top);
        moveTopLeftRightAction.setDuration(1);
        moveTopLeftRightAction.setInterpolation(Interpolation.elastic);

        MoveToAction moveBottomRightAction = new MoveToAction();
        moveBottomRightAction.setPosition(X_right, Y_bottom);
        moveBottomRightAction.setDuration(1);
        moveBottomRightAction.setInterpolation(Interpolation.smooth);


        MoveToAction moveBottomLeftRightAction = new MoveToAction();
        moveBottomLeftRightAction.setPosition(X_left, Y_bottom);
        moveBottomLeftRightAction.setDuration(1);
        moveBottomLeftRightAction.setInterpolation(Interpolation.sineOut);

        ParallelAction leftBottomTopParallelAction = new ParallelAction();
        leftBottomTopParallelAction.addAction(Actions.moveTo(X_left, Y_top, 1, Interpolation.swingOut));
        leftBottomTopParallelAction.addAction(Actions.rotateBy(90, 1));

        SequenceAction overallSequence = new SequenceAction();
        overallSequence.addAction(moveTopLeftRightAction);
        overallSequence.addAction(moveBottomRightAction);
        overallSequence.addAction(moveBottomLeftRightAction);
        overallSequence.addAction(leftBottomTopParallelAction);

        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(overallSequence);

        return infiniteLoop;
    }

    @Override
    public boolean act(float delta) {
        return false;
    }
}
