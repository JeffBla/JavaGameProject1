package com.mygdx.game;

import character.interActorObject.ButtonObject;
import character.interActorObject.Cast_magic.Cast_magicObject_fire;
import character.interActorObject.DotObject;
import character.interActorObject.WallObject;
import character.mainCharacter.MainCharacter;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

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

//        System.out.println("A: "+classA);
//        System.out.println("B: "+classB);

        // if mainCharacter is attack and encounter DotObject
        {
            if (classA.equalsIgnoreCase("character.interActorObject.DotObject")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                MainCharacter mainCharacter = (MainCharacter) tmpB;

                System.out.println("-1");
                DotObject dotObject= (DotObject)tmpA;
                dotObject.setIsDelete(true);
                dotObject.getBody().setLinearVelocity(0,0);

            } else if (classB.equalsIgnoreCase("character.interActorObject.DotObject")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                MainCharacter mainCharacter = (MainCharacter) tmpA;

                System.out.println("-1");
                DotObject dotObject= (DotObject)tmpB;
                dotObject.setIsDelete(true);
                dotObject.getBody().setLinearVelocity(0,0);
            }
        }
        // if mainCharacter's attackDetectRegion is attack and encounter DotObject
        {
            if (classA.equalsIgnoreCase("character.interActorObject.DotObject")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacterAttackDetectRegion")) {

                DotObject dotObject= (DotObject)tmpA;
                dotObject.playAttackedSound(1);
                dotObject.setIsDelete(true);
                dotObject.getBody().setLinearVelocity(0,0);

                System.out.println("dotDeleteAttack");
            }else if(classB.equalsIgnoreCase("character.interActorObject.DotObject")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacterAttackDetectRegion")){

                DotObject dotObject= (DotObject)tmpB;
                dotObject.playAttackedSound(0.5f);
                dotObject.setIsDelete(true);
                dotObject.getBody().setLinearVelocity(0,0);

                System.out.println("dotDeleteAttack");
            }
        }
        // if mainCharacter is pressed the button
        {
            if (classA.equalsIgnoreCase("character.interActorObject.ButtonObject")
                    && classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                ButtonObject button = (ButtonObject) tmpA;
                button.OnButtonPressed();

                Level3.isTheDoorOpen = true;
            } else if (classB.equalsIgnoreCase("character.interActorObject.ButtonObject")
                    && classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")) {
                ButtonObject button = (ButtonObject) tmpB;
                button.OnButtonPressed();

                Level3.isTheDoorOpen = true;

            }
        }
        // if mainCharacter enter the door and touch the bound
        {
            if (classA.equalsIgnoreCase("character.mainCharacter.MainCharacter")
                    && classB.equalsIgnoreCase("character.interActorObject.WallObject")) {
                WallObject bound = (WallObject) tmpB;
                MainCharacter mainCharacter = (MainCharacter) tmpA;
                if (bound.getType().equals("Bound")) {
                    mainCharacter.setIsBound(true);
                }
            } else if (classB.equalsIgnoreCase("character.mainCharacter.MainCharacter")
                    && classA.equalsIgnoreCase("character.interActorObject.WallObject")) {
                WallObject bound = (WallObject) tmpA;
                MainCharacter mainCharacter = (MainCharacter) tmpB;
                if (bound.getType().equals("Bound")) {
                    mainCharacter.setIsBound(true);
                }
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

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
