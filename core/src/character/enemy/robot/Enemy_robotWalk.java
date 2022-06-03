package character.enemy.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy_robotWalk {
    private final Texture walkSheetTexture;

    public Animation<TextureRegion> walkAnimation;

    public Enemy_robotWalk() {
        walkSheetTexture = new Texture(Gdx.files.internal("enemy_robot/enemy_robotWalk.png"));
        int frameCols = 4;
        int frameRows = 1;

        int perCellWidth = walkSheetTexture.getWidth() / frameCols;
        int perCellHeight = walkSheetTexture.getHeight() / frameRows;
        TextureRegion[][] cellRegion = TextureRegion.split(walkSheetTexture, perCellWidth, perCellHeight);
        TextureRegion[] walkFrames = cellRegion[0]; /* Only when frameRows is 1  */

        walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void dispose() {
        walkSheetTexture.dispose();
    }


}
