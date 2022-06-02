package com.mygdx.game;

import character.enemy.robot.Enemy_robot;
import character.interActorObject.ButtonObject;
import character.interActorObject.WallObject;
import character.interActorObject.Laser.LaserLine;
import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.physics.box2d.*;
import kit.FlipAnimation;

public class Level2ContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object tmpA, tmpB;
        String classA, classB;
        if ((tmpA = contact.getFixtureA().getBody().getUserData()) != null)
            classA = tmpA.getClass().getName();
        else return;
        if ((tmpB = contact.getFixtureB().getBody().getUserData()) != null)
            classB = tmpB.getClass().getName();
        else return;

//        System.out.println("A: "+classA);
//        System.out.println("B: "+classB);

        // if mainCharacter is pressed the button
        {
            if (classA.equalsIgnoreCase("character.interActorObject.ButtonObject")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                ButtonObject button = (ButtonObject) tmpA;
                button.OnButtonPressed();

                Level2.isTheDoorOpen = true;
            }
            else if (classB.equalsIgnoreCase("character.interActorObject.ButtonObject")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                ButtonObject button = (ButtonObject) tmpB;
                button.OnButtonPressed();

                Level2.isTheDoorOpen = true;

            }
        }
        // if enemy_robot collides with Wall collision
        {
            if (classA.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classB.equalsIgnoreCase("character.interActorObject.WallObject")) {
                Enemy_robot robot = (Enemy_robot) tmpA;
                if (robot.getIsLeft()) {
                    FlipAnimation.flipAnim_ArrayRight(robot.getActorAnimation());
                    robot.setIsLeft(false);
                } else {
                    FlipAnimation.flipAnim_ArrayLeft(robot.getActorAnimation());
                    robot.setIsLeft(true);
                }
                robot.eyeSightTexture.flip(true,false);
                robot.setSpeed(-robot.getSpeed().x, -robot.getSpeed().y);
            }
            else if (classB.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classA.equalsIgnoreCase("character.interActorObject.WallObject")) {
                Enemy_robot robot = (Enemy_robot) tmpB;
                if (robot.getIsLeft()) {
                    FlipAnimation.flipAnim_ArrayRight(robot.getActorAnimation());
                    robot.setIsLeft(false);
                } else {
                    FlipAnimation.flipAnim_ArrayLeft(robot.getActorAnimation());
                    robot.setIsLeft(true);
                }
                robot.eyeSightTexture.flip(true,false);
                robot.setSpeed(-robot.getSpeed().x, -robot.getSpeed().y);

            }
        }
        // if mainCharacter enter the sight zone or enter the enemy robot's body
        {
            if(classA.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")){
                HUD.hpdecrease();

            }else if(classB.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")){
                HUD.hpdecrease();

            }
        }
        // if mainCharacter enter the door and touch the bound
        {
            if(classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")
                    && classB.equalsIgnoreCase("character.interActorObject.WallObject")){
                WallObject bound =(WallObject) tmpB;
                MainCharacter mainCharacter =(MainCharacter) tmpA;
                if(bound.getType().equals("Bound")){
                    mainCharacter.setIsBound(true);
                }
            }else if(classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")
                    && classA.equalsIgnoreCase("character.interActorObject.WallObject")){
                WallObject bound =(WallObject) tmpA;
                MainCharacter mainCharacter =(MainCharacter) tmpB;
                if(bound.getType().equals("Bound")){
                    mainCharacter.setIsBound(true);
                }
            }
        }
        {
            if(classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")
                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserLine")){
                MainCharacter mainCharacter =(MainCharacter) tmpA;
                LaserLine Laserline = (LaserLine) tmpB;
                HUD.hpdecrease();

            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}