package com.mygdx.game;

import character.enemy.robot.Enemy_robot;
import character.interActorObject.ButtonObject;
import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.physics.box2d.*;
import kit.FlipAnimation;

public class GameLobbyContactListener implements ContactListener {
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

        // if mainCharacter is attack and encounter some mobs
        {
            if (classA.equalsIgnoreCase("character.interActorObject.BoxObject")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                boolean isLeft_mainCh;

                MainCharacter mainCharacter = (MainCharacter) (contact.getFixtureB().getBody().getUserData());
                isLeft_mainCh = mainCharacter.getIsLeft();
                if (mainCharacter.getIsAttack()) {
                    if (!isLeft_mainCh) {
                        if (mainCharacter.attackDetectRight.getLocalCenter().equals(contact.getFixtureB().getBody().getLocalCenter())) {
                            System.out.println("BR vs A");
                        }
                    } else {
                        if (mainCharacter.attackDetectLeft.getLocalCenter().equals(contact.getFixtureB().getBody().getLocalCenter())) {
                            System.out.println("BL vs A");
                        }
                    }
                }
            }
            else if (classB.equalsIgnoreCase("character.interActorObject.BoxObject")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                boolean isLeft_mainCh;

                MainCharacter mainCharacter = (MainCharacter) (contact.getFixtureA().getBody().getUserData());
                isLeft_mainCh = mainCharacter.getIsLeft();

                if (mainCharacter.getIsAttack()) {
                    if (!isLeft_mainCh) {
                        if (mainCharacter.attackDetectRight.getLocalCenter().equals(contact.getFixtureA().getBody().getLocalCenter())) {
                            System.out.println("AR vs B");
                        }
                    } else {
                        if (mainCharacter.attackDetectLeft.getLocalCenter().equals(contact.getFixtureA().getBody().getLocalCenter())) {
                            System.out.println("AL vs B");
                        }
                    }
                }
            }
        }
        // if mainCharacter is pressed the button
        {
            if (classA.equalsIgnoreCase("character.interActorObject.ButtonObject")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                ButtonObject button = (ButtonObject) tmpA;
                button.OnButtonPressed();

                GameLobby.isTheDoorOpen = true;
            }
            else if (classB.equalsIgnoreCase("character.interActorObject.ButtonObject")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                ButtonObject button = (ButtonObject) tmpB;
                button.OnButtonPressed();

                GameLobby.isTheDoorOpen = true;

            }
        }
        // if enemy_robot collides with static collision
        {
            if (classA.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classB.equalsIgnoreCase("character.interActorObject.WallObject")) {
                Enemy_robot robot = (Enemy_robot) tmpA;
                if (robot.getIsLeft()) {
                    FlipAnimation.flipAnim_ArrayRight(robot.getActorAnimation());
                } else {
                    FlipAnimation.flipAnim_ArrayLeft(robot.getActorAnimation());
                }
                robot.setSpeed(-robot.init_walkSpeed, 0);
            }
            else if (classB.equalsIgnoreCase("character.enemy.robot.Enemy_robot")
                    && classA.equalsIgnoreCase("character.interActorObject.WallObject")) {
                Enemy_robot robot = (Enemy_robot) tmpB;
                if (robot.getIsLeft()) {
                    FlipAnimation.flipAnim_ArrayRight(robot.getActorAnimation());
                } else {
                    FlipAnimation.flipAnim_ArrayLeft(robot.getActorAnimation());
                }
                robot.setSpeed(-robot.init_walkSpeed, 0);

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
