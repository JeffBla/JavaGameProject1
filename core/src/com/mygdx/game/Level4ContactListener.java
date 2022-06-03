package com.mygdx.game;

import character.interActorObject.Cast_magic.Cast_magicObject_fire;
import character.interActorObject.Gear.GearActor;
import character.interActorObject.Gear.GearActor_fire;
import character.interActorObject.Laser.LaserLine;
import com.badlogic.gdx.physics.box2d.*;

import character.enemy.robot.Enemy_robot;
import character.interActorObject.WallObject;
import character.interActorObject.Cannon.CannonLine;
import character.interActorObject.Cannon.CannonBase;
import kit.FlipAnimation;

public class Level4ContactListener implements ContactListener {

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

        //System.out.println("A: "+classA);
        //System.out.println("B: "+classB);

        {
            if(classA.equalsIgnoreCase("character.mainCharacter.MainCharacterAttackDetectRegion")
                    && classB.equalsIgnoreCase("character.interActorObject.Cannon.CannonBase")){
                CannonBase base = (CannonBase) tmpB;
                base.setBeAttacked(true);
            }
            else if(classA.equalsIgnoreCase("character.interActorObject.Cannon.CannonBase")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacterAttackDetectRegion")){
                CannonBase base = (CannonBase) tmpA;
                base.setBeAttacked(true);
            }
        }

        {
            if(classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")
                    && classB.equalsIgnoreCase("character.interActorObject.Cannon.CannonLine")){
                CannonLine line = (CannonLine) tmpB;
                if(line.getAttack()==true) {
                    HUD.hpdecrease();
                }
            }
            else if(classA.equalsIgnoreCase("character.interActorObject.Cannon.CannonLine")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")){
                CannonLine line = (CannonLine) tmpA;
                if(line.getAttack()==true) {
                    HUD.hpdecrease();
                }
            }
        }

        {
            if(classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")
                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserLine")){
                HUD.hpdecrease();
            }
            else if(classA.equalsIgnoreCase("character.interActorObject.Laser.LaserLine")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")){
                HUD.hpdecrease();
            }
        }

        {
            if (classA.equalsIgnoreCase("character.interActorObject.WallObject")
                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserLine")) {
                WallObject wall = (WallObject) tmpA;
                LaserLine laserline = (LaserLine) tmpB;
                if (laserline.getType().equals("rile")) {
                    laserline.setTouch(true);
                    laserline.setTransform(wall.getBody().getPosition().x + wall.getWallWeight(), laserline.getEndX());
                }
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
                robot.eyeSightTexture.flip(true, false);
                robot.setSpeed(-robot.getSpeed().x, -robot.getSpeed().y);
            } else if (classB.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classA.equalsIgnoreCase("character.interActorObject.WallObject")) {
                Enemy_robot robot = (Enemy_robot) tmpB;
                if (robot.getIsLeft()) {
                    FlipAnimation.flipAnim_ArrayRight(robot.getActorAnimation());
                    robot.setIsLeft(false);
                } else {
                    FlipAnimation.flipAnim_ArrayLeft(robot.getActorAnimation());
                    robot.setIsLeft(true);
                }
                robot.eyeSightTexture.flip(true, false);
                robot.setSpeed(-robot.getSpeed().x, -robot.getSpeed().y);

            }
        }
        // if mainCharacter enter the sight zone or enter the enemy robot's body
        {
            if (classA.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                Enemy_robot robot=(Enemy_robot) tmpA;
                if(robot.getIsWarning() == false) {
                    HUD.hpdecrease();
                }

            } else if (classB.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                Enemy_robot robot=(Enemy_robot) tmpB;
                if(robot.getIsWarning() == false) {
                    HUD.hpdecrease();
                }
            }
        }
        // if Cast_magic_FireAnim hit object except enemy_robot, and attack boss and fireBall
        // will clear fireBall
        {
            if (classA.equalsIgnoreCase("character.interActorObject.Cast_magic.Cast_magicObject_fire")
                    && (!classB.equalsIgnoreCase("character.enemy.robot.Enemy_robot") &&
                    !classB.equalsIgnoreCase("character.interActorObject.Gear.GearActor_fire"))) {
                Cast_magicObject_fire magicObject_fire = (Cast_magicObject_fire) tmpA;

                magicObject_fire.setIsHit(true);

                if (classB.equalsIgnoreCase("character.interActorObject.Gear.GearActor")) {
                    ((GearActor) tmpB).deHp(5);
                }
            } else if (classB.equalsIgnoreCase("character.interActorObject.Cast_magic.Cast_magicObject_fire")
                    && (!classA.equalsIgnoreCase("character.enemy.robot.Enemy_robot") &&
                    !classA.equalsIgnoreCase("character.interActorObject.Gear.GearActor_fire"))) {
                Cast_magicObject_fire magicObject_fire = (Cast_magicObject_fire) tmpB;

                magicObject_fire.setIsHit(true);

                if (classA.equalsIgnoreCase("character.interActorObject.Gear.GearActor")) {
                    ((GearActor) tmpA).deHp(5);
                }
            }
        }
        // if fire hit the Cast_magicObject_fire
        {
            if (classA.equalsIgnoreCase("character.interActorObject.Gear.GearActor_fire")
                    && classB.equalsIgnoreCase("character.interActorObject.Cast_magic.Cast_magicObject_fire")) {
                ((GearActor_fire) tmpA).setIsDelete(true);

            } else if (classB.equalsIgnoreCase("character.interActorObject.Gear.GearActor_fire")
                    && classA.equalsIgnoreCase("character.interActorObject.Cast_magic.Cast_magicObject_fire")) {
                ((GearActor_fire) tmpB).setIsDelete(true);
            }
        }// if fire hit the wall
        {
            if (classA.equalsIgnoreCase("character.interActorObject.Gear.GearActor_fire")
                    && classB.equalsIgnoreCase("character.interActorObject.WallObject")) {
                ((GearActor_fire) tmpA).setIsDelete(true);

            } else if (classB.equalsIgnoreCase("character.interActorObject.Gear.GearActor_fire")
                    && classA.equalsIgnoreCase("character.interActorObject.WallObject")) {
                ((GearActor_fire) tmpB).setIsDelete(true);
            }
        }
        // if fire hit the mainCharacter
        {
            if (classA.equalsIgnoreCase("character.interActorObject.Gear.GearActor_fire")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                HUD.hpdecrease();

            } else if (classB.equalsIgnoreCase("character.interActorObject.Gear.GearActor_fire")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                HUD.hpdecrease();

            }
        }
        // if mainCharacter attack boss
        {
            if (classA.equalsIgnoreCase("character.mainCharacter.MainCharacterAttackDetectRegion")
                    && classB.equalsIgnoreCase("character.interActorObject.Gear.GearActor")) {
                ((GearActor) tmpB).deHp(15);

            } else if (classB.equalsIgnoreCase("character.mainCharacter.MainCharacterAttackDetectRegion")
                    && classA.equalsIgnoreCase("character.interActorObject.Gear.GearActor")) {
                ((GearActor) tmpA).deHp(15);

            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object tmpA, tmpB;
        String classA, classB;
        if ((tmpA = contact.getFixtureA().getBody().getUserData()) != null)
            classA = tmpA.getClass().getName();
        else return;
        if ((tmpB = contact.getFixtureB().getBody().getUserData()) != null)
            classB = tmpB.getClass().getName();
        else return;

        if (classA.equalsIgnoreCase("character.interActorObject.WallObject")
                && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserLine")) {
            LaserLine laserline = (LaserLine) tmpB;
            if (laserline.getType().equals("rile")) {
                laserline.setLeave(true);
                laserline.setTransform(laserline.getOriginX(), laserline.getEndX());
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub

    }
}