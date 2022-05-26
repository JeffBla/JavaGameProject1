package com.mygdx.game;

import character.enemy.robot.Enemy_robot;
import character.interActorObject.BoxObject;
import character.interActorObject.ButtonObject;
import character.interActorObject.WallObject;
import character.interActorObject.Laser.LaserObjectLine;
import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.physics.box2d.*;
import kit.FlipAnimation;

public class Level2ContactListener implements ContactListener {
    HUD HUDBatch=new HUD();
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
                HUD.hp--;
            }else if(classB.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")){
                HUD.hp--;
            }
        }
        // if the health point decrease
        {
            switch(HUD.hp) {
                case 3:
                    break;
                case 2:
                    HUDBatch.FullHp3.setPosition(100000, 100000);
                    break;
                case 1:
                    HUDBatch.FullHp2.setPosition(100000, 100000);
                    break;
                case 0:
                    HUDBatch.FullHp1.setPosition(100000, 100000);
                    break;
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
                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserObjectLine")){
                MainCharacter mainCharacter =(MainCharacter) tmpA;
                LaserObjectLine Laser = (LaserObjectLine) tmpB;
                HUD.hp--;
                System.out.println("die");
            }
        }
//        {
//        	if(classA.equalsIgnoreCase("character.interActorObject.BoxObject")
//                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserObject")){
//        		BoxObject Box =(BoxObject)(contact.getFixtureA().getBody().getUserData());
//        		LaserObject Laser = (LaserObject) tmpB;
//        		if(Laser.get_type().equals("leri")) {
//        			Laser.touch_leri(Box.getPosition_X(), Box.getPosition_Y());
//        		}
//        		else if(Laser.get_type().equals("doup")) {
//        			Laser.touch_doup(Box.getPosition_X(), Box.getPosition_X());
//        		}
//            }
//        	else if(classB.equalsIgnoreCase("character.interActorObject.BoxObject")
//                    && classA.equalsIgnoreCase("character.interActorObject.Laser.LaserObject")){
//        		BoxObject Box =(BoxObject)(contact.getFixtureB().getBody().getUserData());
//        		LaserObject Laser = (LaserObject) tmpA;
//        		if(Laser.get_type().equals("leri")) {
//        			Laser.touch_leri(Box.getPosition_X(), Box.getPosition_Y());
//        		}
//        		else if(Laser.get_type().equals("doup")) {
//        			Laser.touch_doup(Box.getPosition_X(), Box.getPosition_Y());
//        		}
//            }
//        }
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

//        {
//        	if(classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")
//                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserObject")){
//        		MainCharacter mainCharacter =(MainCharacter) tmpA;
//        		LaserObject Laser = (LaserObject) tmpB;
//        		if(Laser.get_type().equals("leri")) {
//        			System.out.println("leri");
//        			Laser.left();
//        		}
//        		else if(Laser.get_type().equals("doup")) {
//        			System.out.println("doup");
//        			Laser.left();
//        		}
//            }
//        }
//        {
//        	if(classA.equalsIgnoreCase("character.interActorObject.BoxObject")
//                    && classB.equalsIgnoreCase("character.interActorObject.Laser.LaserObject")){
//        		BoxObject Box =(BoxObject) tmpA;
//        		LaserObject Laser = (LaserObject) tmpB;
//        		if(Laser.get_type().equals("leri")) {
//            		Laser.left();
//        		}
//        		else if(Laser.get_type().equals("doup")) {
//        			Laser.left();
//        		}
//            }
//        	else if(classB.equalsIgnoreCase("character.interActorObject.BoxObject")
//                    && classA.equalsIgnoreCase("character.interActorObject.Laser.LaserObject")){
//        		BoxObject Box =(BoxObject) tmpB;
//        		LaserObject Laser = (LaserObject) tmpA;
//        		if(Laser.get_type().equals("leri")) {
//            		Laser.left();
//        		}
//        		else if(Laser.get_type().equals("doup")) {
//        			Laser.left();
//        		}
//            }
//        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}