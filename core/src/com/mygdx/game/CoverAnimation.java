package com.mygdx.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class CoverAnimation extends Action {

    private Action action1;

    public CoverAnimation(){

    }

    public Action action1Scale(){
        ScaleToAction scaleToAction_Big = new ScaleToAction();
        scaleToAction_Big.setScale(0.9f);
        scaleToAction_Big.setDuration(1);
        scaleToAction_Big.setInterpolation(Interpolation.smooth);

        ScaleToAction scaleToAction_Small = new ScaleToAction();
        scaleToAction_Small.setScale(0.8f);
        scaleToAction_Small.setDuration(1);
        scaleToAction_Small.setInterpolation(Interpolation.smooth);

        SequenceAction overallSequence = new SequenceAction();
        overallSequence.addAction(scaleToAction_Big);
        overallSequence.addAction(scaleToAction_Small);

        RepeatAction infiniteLoop = new RepeatAction();
        infiniteLoop.setCount(RepeatAction.FOREVER);
        infiniteLoop.setAction(overallSequence);

        action1 = infiniteLoop;

        return infiniteLoop;
    }

    public Action getAction1() {
        return action1;
    }

    @Override
    public boolean act(float delta) {
        return false;
    }
}
