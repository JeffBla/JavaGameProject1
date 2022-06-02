package com.mygdx.game;

import com.badlogic.gdx.Screen;
public class HUDScreen {

    protected Screen analyzeCurrentLevel_new(Screen current_screen, GameMode gameMode) {
        String class_name = current_screen.getClass().getName();

        if (class_name.equalsIgnoreCase("com.mygdx.game.GameLobby")) {
            return new GameLobby(gameMode);
        } else if (class_name.equalsIgnoreCase("com.mygdx.game.Level2")) {
            return new Level2(gameMode);
        } else if (class_name.equalsIgnoreCase("com.mygdx.game.Level3")) {
            return new Level3(gameMode);
        } else if (class_name.equalsIgnoreCase("com.mygdx.game.Level4")) {
            return new Level4(gameMode);
        } else {
            return null;
        }
    }

    protected Screen nextLevel_new(Screen current_screen, GameMode gameMode) {
        String class_name = current_screen.getClass().getName();

        if (class_name.equalsIgnoreCase("com.mygdx.game.GameLobby")) {
            return new Level2(gameMode);
        } else if (class_name.equalsIgnoreCase("com.mygdx.game.Level2")) {
            return new Level3(gameMode);
        } else if (class_name.equalsIgnoreCase("com.mygdx.game.Level3")) {
            return new Level4(gameMode);
        } else {
            return null;
        }

    }
}
