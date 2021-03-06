package character.mainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainCharacterIdle {
    private final Texture idleSheetTexture;

    public Animation<TextureRegion> idleAnimation;

    public MainCharacterIdle() {
        idleSheetTexture = new Texture(Gdx.files.internal("Adventurer-1.5/adventurer_idle.png"));
        int frameCols = 4;
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