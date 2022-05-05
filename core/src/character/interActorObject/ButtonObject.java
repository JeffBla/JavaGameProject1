package character.interActorObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;
import worldBuilding.BuildBody;

public class ButtonObject extends Actor {
    private final Texture buttonUpImg;
    private final Texture buttonDownImg;
    private Sprite sprite;

    private Body body;
    private Sound pressedSound = Gdx.audio.newSound(Gdx.files.internal("Sound/ButtonPressed.mp3"));

    public ButtonObject(World gameWorld, String textureUpPath, String textureDownPath, float x, float y,
                        float weight, float height,
                        float fixBoxOrigin_constant, float fixBoxWeight_constant, float fixBoxHeight_constant) {
        buttonUpImg = new Texture(Gdx.files.internal(textureUpPath));
        buttonDownImg = new Texture(Gdx.files.internal(textureDownPath));

        sprite = new Sprite(buttonUpImg);
        sprite.setPosition(x, y);
        sprite.setSize(weight * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createBox(gameWorld, x, y,
                sprite.getWidth() / GameMode.PPM / 2 + fixBoxWeight_constant,
                sprite.getHeight() / GameMode.PPM / 2 + fixBoxHeight_constant,
                new Vector2(sprite.getWidth() / GameMode.PPM / 2, sprite.getHeight() / GameMode.PPM / 2),
                0, 0, 0, true, false, true);

        body.setUserData(this);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, body.getPosition().x, body.getPosition().y,
                sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM);
    }

    public void OnButtonPressed(){
        this.sprite.setTexture(buttonDownImg);
        pressedSound.play();
    }
    public void OnButtonRelease(){
        this.sprite.setTexture(buttonUpImg);
    }

    public void dispose() {
        buttonUpImg.dispose();
    }

    public void setPressedSound(Sound pressed) {
        this.pressedSound = pressed;
    }
}
