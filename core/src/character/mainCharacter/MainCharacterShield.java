package character.mainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;
import com.mygdx.game.HUD;
import worldBuilding.BuildBody;

public class MainCharacterShield extends Actor {

    private Body body;
    private Texture shieldTexture = new Texture(Gdx.files.internal("Adventurer-1.5/adventurer_shield3.png"));
    private Sprite sprite;
    private Actor attachedActor;

    public static float invincibleTime = 3;
    private boolean isShow;

    public MainCharacterShield(World gameWorld, float pos_x, float pos_y, float width, float height, Actor attachedActor) {
        this.isShow = false;
        this.attachedActor = attachedActor;

        sprite = new Sprite(shieldTexture);
        sprite.setBounds(pos_x, pos_y, width * GameMode.PPM, height * GameMode.PPM);

        body = BuildBody.createCircle(gameWorld, pos_x, pos_y, width / 2, 0, 0, 0,
                false, true, true);

        body.setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isShow) {
            batch.draw(sprite, body.getPosition().x - sprite.getWidth() / 2 / GameMode.PPM,
                    body.getPosition().y - sprite.getHeight() / 2 / GameMode.PPM,
                    sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM);
        }
    }

    @Override
    public void act(float delta) {
        body.setTransform(attachedActor.getX() + attachedActor.getWidth() / 2,
                attachedActor.getY() + attachedActor.getHeight() / 2 - 0.2f /* little adjust */, 0);
        if (HUD.isHpDecrease) {
            showShield();
        } else {
            hideShield();
        }
    }

    public void dispose() {
        shieldTexture.dispose();
    }

    public void hideShield() {
        isShow = false;
    }

    public void showShield() {
        isShow = true;
    }


}
