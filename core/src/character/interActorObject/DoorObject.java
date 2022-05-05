package character.interActorObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;
import worldBuilding.BuildBody;

public class DoorObject extends Actor {

    private final Texture doorLeftTexture;
    private final Texture doorRightTexture;

    private Sprite spriteLeft;
    private Sprite spriteRight;

    private Body bodyLeft;
    private Body bodyRight;
    private PrismaticJoint prismaticJoint;

    public DoorObject(World gameWorld, String textureLeftPath, String textureRightPath, float x, float y,
                      float weight, float height, float lowerTranslation, float upperTranslation,
                      float fixBoxOrigin_constant, float fixBoxWeight_constant, float fixBoxHeight_constant) {
        doorLeftTexture = new Texture(Gdx.files.internal(textureLeftPath));
        doorRightTexture = new Texture(Gdx.files.internal(textureRightPath));

        spriteLeft = new Sprite(doorLeftTexture);
        spriteLeft.setBounds(x, y, weight * GameMode.PPM, height * GameMode.PPM);

        spriteRight = new Sprite(doorRightTexture);
        spriteRight.setBounds(x, y, weight * GameMode.PPM, height * GameMode.PPM);

        bodyLeft = BuildBody.createBox(gameWorld, x, y,
                spriteLeft.getWidth() / GameMode.PPM / 2 + fixBoxWeight_constant,
                spriteLeft.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(spriteLeft.getWidth() / GameMode.PPM / 2, spriteLeft.getHeight() / GameMode.PPM / 2),
                0, 0, 0, false, true, false);

        bodyRight = BuildBody.createBox(gameWorld, x, y,
                spriteRight.getWidth() / GameMode.PPM / 2 + fixBoxWeight_constant,
                spriteRight.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(spriteRight.getWidth() / GameMode.PPM / 2, spriteRight.getHeight() / GameMode.PPM / 2),
                0, 0, 0, false, true, false);

        prismaticJoint = BuildBody.createPrismaticJoint(gameWorld, bodyLeft, bodyRight, 1.5f, 0, -1.5f, 0,
                true, true, lowerTranslation, upperTranslation);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(spriteLeft, bodyLeft.getPosition().x, bodyLeft.getPosition().y,
                spriteLeft.getWidth() / GameMode.PPM, spriteLeft.getHeight() / GameMode.PPM);

        batch.draw(spriteRight, bodyRight.getPosition().x, bodyRight.getPosition().y,
                spriteRight.getWidth() / GameMode.PPM, spriteRight.getHeight() / GameMode.PPM);
    }

    public void dispose() {
        doorLeftTexture.dispose();
        doorRightTexture.dispose();
    }

    public PrismaticJoint getPrismaticJoint() {
        return prismaticJoint;
    }
}
