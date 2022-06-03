package character.interActorObject.Cast_magic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Cast_magic_FireAnim {
    private final Texture flySheetTexture;
    private final Texture spawnSheetTexture;
    private final Texture hitSheetTexture;

    private Animation<TextureRegion> flyAnimation;
    private Animation<TextureRegion> spawnAnimation;
    private Animation<TextureRegion> hitAnimation;

    private Array<Animation<TextureRegion>> actorAnimation;

    public Cast_magic_FireAnim() {
        flySheetTexture = new Texture(Gdx.files.internal("Adventurer-1.5/cast/cast_magic_fire_fly.png"));
        spawnSheetTexture = new Texture(Gdx.files.internal("Adventurer-1.5/cast/cast_magic_fire_spawn_fix.png"));
        hitSheetTexture = new Texture(Gdx.files.internal("Adventurer-1.5/cast/cast_magic_fire_hit.png"));
        int fly_frameCols = 3;
        int fly_frameRows = 1;
        int spawn_frameCols = 3;
        int spawn_frameRows = 1;
        int hit_frameCols = 4;
        int hit_frameRows = 1;

        int fly_perCellWidth = flySheetTexture.getWidth() / fly_frameCols;
        int fly_perCellHeight = flySheetTexture.getHeight() / fly_frameRows;
        TextureRegion[][] cellRegion = TextureRegion.split(flySheetTexture, fly_perCellWidth, fly_perCellHeight);
        TextureRegion[] flyFrames = cellRegion[0]; /* Only when frameRows is 1  */

        flyAnimation = new Animation<TextureRegion>(0.2f, flyFrames);
        flyAnimation.setPlayMode(Animation.PlayMode.LOOP);

        int spawn_perCellWidth = spawnSheetTexture.getWidth() / spawn_frameCols;
        int spawn_perCellHeight = spawnSheetTexture.getHeight() / spawn_frameRows;
        cellRegion = TextureRegion.split(spawnSheetTexture, spawn_perCellWidth, spawn_perCellHeight);
        TextureRegion[] spawnFrames = cellRegion[0]; /* Only when frameRows is 1  */

        spawnAnimation = new Animation<TextureRegion>(0.1f, spawnFrames);
        spawnAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        int hit_perCellWidth = hitSheetTexture.getWidth() / hit_frameCols;
        int hit_perCellHeight = hitSheetTexture.getHeight() / hit_frameRows;
        cellRegion = TextureRegion.split(hitSheetTexture, hit_perCellWidth, hit_perCellHeight);
        TextureRegion[] hitFrames = cellRegion[0]; /* Only when frameRows is 1  */

        hitAnimation = new Animation<TextureRegion>(0.3f, hitFrames);
        hitAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        actorAnimation = new Array<>();
        actorAnimation.addAll(flyAnimation, spawnAnimation ,hitAnimation);
    }


    public void dispose() {
        flySheetTexture.dispose();
        spawnSheetTexture.dispose();
    }

    public Animation<TextureRegion> getFlyAnimation() {
        return flyAnimation;
    }

    public Animation<TextureRegion> getSpawnAnimation() {
        return spawnAnimation;
    }

    public Animation<TextureRegion> getHitAnimation() {
        return hitAnimation;
    }

    public Array<Animation<TextureRegion>> GetActorAnimation(){
        return actorAnimation;
    }

}