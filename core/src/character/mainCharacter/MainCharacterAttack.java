package character.mainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class MainCharacterAttack {
    private final Texture textureSheet1 = new Texture(Gdx.files.internal("Adventurer-1.5/adventurer_attack1.png"));
    private final Texture textureSheet2 = new Texture(Gdx.files.internal("Adventurer-1.5/adventurer_attack2.png"));
    private final Texture textureSheet3 = new Texture(Gdx.files.internal("Adventurer-1.5/adventurer_attack3.png"));
    private final Texture textureSheetCast = new Texture(Gdx.files.internal("Adventurer-1.5/adventurer_cast.png"));
    protected ArrayList<Animation<TextureRegion>> attackAnims;
    protected Animation<TextureRegion> attackAnim1;
    protected Animation<TextureRegion> attackAnim2;
    protected Animation<TextureRegion> attackAnim3;
    protected Animation<TextureRegion> attackAnimCast;

    public MainCharacterAttack() {
        attackAnim1 = createAnim(1, 6, true, 0.1f, textureSheet1);
        attackAnim2 = createAnim(1, 4, true, 0.1f, textureSheet2);
        attackAnim3 = createAnim(1, 6, true, 0.1f, textureSheet3);
        attackAnimCast = createAnim(1, 6, true, 0.1f, textureSheetCast);

        attackAnims =new ArrayList<>(); // useless now
        attackAnims.add(attackAnim1);
        attackAnims.add(attackAnim2);
        attackAnims.add(attackAnim3);
        attackAnims.add(attackAnimCast);
    }

    private Animation<TextureRegion> createAnim(final int numOfRow, final int numOfCol,
                                                final boolean isLoop, final float frameDuration,
                                                Texture textureSheet) {
        int pixelPerRow = textureSheet.getHeight() / numOfRow;
        int pixelPerCol = textureSheet.getWidth() / numOfCol;

        TextureRegion textureRegionSheet = new TextureRegion(textureSheet);
        TextureRegion[][] textureAnim = textureRegionSheet.split(pixelPerCol, pixelPerRow);

        Animation<TextureRegion> attackAnimTemp = new Animation<TextureRegion>(frameDuration, textureAnim[0]);
        if (isLoop)
            attackAnimTemp.setPlayMode(Animation.PlayMode.LOOP);

        return attackAnimTemp;
    }

    public void dispose() {
        textureSheet1.dispose();
        textureSheet2.dispose();
    }

    public ArrayList<Animation<TextureRegion>> getAttackAnims(){
        return attackAnims;
    };

}
