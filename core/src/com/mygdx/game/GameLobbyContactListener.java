package com.mygdx.game;

import character.interActorObject.ButtonObject;
import character.interActorObject.DoorObject;
import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.physics.box2d.*;

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
            if (classB.equalsIgnoreCase("character.interActorObject.BoxObject")
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
        // if mainCharacter is pesssed the button
        {
            if (classA.equalsIgnoreCase("character.interActorObject.ButtonObject")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                ButtonObject button = (ButtonObject) tmpA;
                button.OnButtonPressed();
            }
            if (classB.equalsIgnoreCase("character.interActorObject.ButtonObject")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                ButtonObject button = (ButtonObject) tmpB;
                button.OnButtonPressed();

                GameLobby.isTheDoorOpen =true;

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
