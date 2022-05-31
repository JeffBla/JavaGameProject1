package character.mainCharacter;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import worldBuilding.BuildBody;

public class MainCharacterAttackDetectRegion {
    private Body detectRegion;

    public MainCharacterAttackDetectRegion(World world, float body_posX, float body_posY, float halfWidth, float halfHeight,
                                           Vector2 center, float density, float angle, float fiction,
                                           boolean isStaticBody, boolean isDynamicBody, boolean isSensor) {
        detectRegion = BuildBody.createBox(world, body_posX, body_posY, halfWidth,halfHeight,
                center, density, angle, fiction, isStaticBody, isDynamicBody, isSensor);

        detectRegion.setUserData(this);
    }

    public Body getDetectRegion(){
        return detectRegion;
    }

}
