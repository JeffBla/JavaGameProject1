package kit;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class FlipAnimation {
    public static void flipTheAnimRight(Animation<TextureRegion> anim) {
        for (TextureRegion textureRegion : anim.getKeyFrames())
            if (textureRegion.isFlipX())
                textureRegion.flip(true, false);

    }

    public static void flipTheAnimLeft(Animation<TextureRegion> anim) {
        for (TextureRegion textureRegion : anim.getKeyFrames())
            if (!textureRegion.isFlipX())
                textureRegion.flip(true, false);
    }

    public static void flipAnim_ArrayRight(Array<Animation<TextureRegion>> anim_array){
        for(Animation<TextureRegion> anim : anim_array){
            flipTheAnimRight(anim);
        }
    }
    public static void flipAnim_ArrayLeft(Array<Animation<TextureRegion>> anim_array){
        for(Animation<TextureRegion> anim : anim_array){
            flipTheAnimLeft(anim);
        }
    }
}
