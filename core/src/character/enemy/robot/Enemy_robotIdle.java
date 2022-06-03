package character.enemy.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy_robotIdle {
    private final Texture idleSheetTexture;

    public Animation<TextureRegion> idleAnimation;

    public Enemy_robotIdle() {
        idleSheetTexture = new Texture(Gdx.files.internal("enemy_robot/enemy_robotIdle.png"));
        int frameCols = 6;
        int frameRows = 1;

        int perCellWidth = idleSheetTexture.getWidth() / frameCols;
        int perCellHeight = idleSheetTexture.getHeight() / frameRows;
        TextureRegion[][] cellRegion = TextureRegion.split(idleSheetTexture, perCellWidth, perCellHeight);
        TextureRegion[] walkFrames = cellRegion[0]; /* Only when frameRows is 1  */

        idleAnimation = new Animation<TextureRegion>(0.2f, walkFrames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void dispose() {
        idleSheetTexture.dispose();
    }


}