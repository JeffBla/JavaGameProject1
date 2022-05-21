package character.interActorObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;
import worldBuilding.BuildBody;

import java.util.ArrayList;
import java.util.Random;

public class DotObject extends Actor {

    private Texture peachDotTexture;
    private Texture darkDotTexture;
    private Texture greenDotTexture;
    private Texture lightBlueDotTexture;
    private Texture pinkDotDotTexture;
    private ArrayList<Texture> textureArrayList;
    private Sprite sprite;
    private Body body;
    private Sound attackedSound;

    private Random random;

    private boolean isDelete;

    public DotObject(World gameWorld, float x, float y,
                     float weight, float height) {
        isDelete = false;

        peachDotTexture = new Texture(Gdx.files.internal("dots/peachDot.png"));
        darkDotTexture = new Texture(Gdx.files.internal("dots/darkDot.png"));
        greenDotTexture = new Texture(Gdx.files.internal("dots/greenDot.png"));
        lightBlueDotTexture = new Texture(Gdx.files.internal("dots/lightBlueDot.png"));
        pinkDotDotTexture = new Texture(Gdx.files.internal("dots/pinkDot.png"));

        textureArrayList = new ArrayList<>();
        textureArrayList.add(peachDotTexture);
        textureArrayList.add(darkDotTexture);
        textureArrayList.add(greenDotTexture);
        textureArrayList.add(lightBlueDotTexture);
        textureArrayList.add(pinkDotDotTexture);

        random = new Random();

        sprite = new Sprite(textureArrayList.get(random.nextInt(textureArrayList.size())));
        sprite.setSize(weight * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createCircle(gameWorld, x, y, weight / 2, 0, 0, 1,
                false, true, false);

        float randomNumX ;
        float randomNumY ;
        while(true){
            randomNumX = random.nextFloat(-7, 7);
            if (Math.abs(randomNumX) >= 4.0f) {
                break;
            }
        }while (true) {
            randomNumY = random.nextFloat(-7, 7);
            if (Math.abs(randomNumY) >= 4.0f) {
                break;
            }
        }

        body.setLinearVelocity(randomNumX, randomNumY);

        attackedSound = Gdx.audio.newSound(Gdx.files.internal("Sound/woodchop2.mp3"));

        body.setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, body.getPosition().x - sprite.getWidth() / 2 / GameMode.PPM,
                body.getPosition().y - sprite.getHeight() / 2 / GameMode.PPM,
                sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM);
    }

    @Override
    public void act(float delta) {
        if (isDelete) {
            body.setTransform(100, 100, 0);
        }
    }

    public void dispose() {
        peachDotTexture.dispose();
        darkDotTexture.dispose();
        greenDotTexture.dispose();
        lightBlueDotTexture.dispose();
        pinkDotDotTexture.dispose();
        attackedSound.dispose();
    }

    public void deleteFromStage() {
        body.setTransform(100, 100, 0);
    }

    public Body getBody() {
        return body;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void playAttackedSound(float volume) {
        attackedSound.play(volume);
    }
}
