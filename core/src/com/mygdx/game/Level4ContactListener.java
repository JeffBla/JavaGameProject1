package com.mygdx.game;

import character.interActorObject.Cast_magic.Cast_magicObject_fire;
import com.badlogic.gdx.physics.box2d.*;

import character.enemy.robot.Enemy_robot;
import character.interActorObject.WallObject;
import character.interActorObject.Laser.LaserObjectLine;
import character.mainCharacter.MainCharacter;
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
        	if(classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")
                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserObjectLine")){
        		MainCharacter mainCharacter =(MainCharacter) tmpA;
        		LaserObjectLine laser = (LaserObjectLine) tmpB;
            }
        	else if(classA.equalsIgnoreCase("character.interActorObject.Laser.LaserObjectLine")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")){
        		LaserObjectLine laser = (LaserObjectLine) tmpA;
        		MainCharacter mainCharacter =(MainCharacter) tmpB;
            }
        }

        {
        	if(classA.equalsIgnoreCase("character.interActorObject.WallObject")
                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserObjectLine")){
        		WallObject wall=(WallObject) tmpA;
        		LaserObjectLine laser = (LaserObjectLine) tmpB;
            }
        	else if(classA.equalsIgnoreCase("character.interActorObject.Laser.LaserObjectLine")
                    && classB.equalsIgnoreCase("character.interActorObject.WallObject")){
        		LaserObjectLine laser = (LaserObjectLine) tmpA;
        		WallObject wall=(WallObject) tmpB;
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
                System.out.println("DIE");
            }else if(classB.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")){
                System.out.println("DIE");
            }
        }
        // if Cast_magic_FireAnim hit object
        {
            if (classA.equalsIgnoreCase("character.interActorObject.Cast_magic.Cast_magicObject_fire")
                    && classB.equalsIgnoreCase("character.interActorObject.WallObject")){
                Cast_magicObject_fire magicObject_fire = (Cast_magicObject_fire) tmpA;

                magicObject_fire.setIsHit(true);
            }else if(classB.equalsIgnoreCase("character.interActorObject.Cast_magic.Cast_magicObject_fire")
                    && classA.equalsIgnoreCase("character.interActorObject.WallObject")){
                Cast_magicObject_fire magicObject_fire = (Cast_magicObject_fire) tmpB;

                magicObject_fire.setIsHit(true);
            }
        }
}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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