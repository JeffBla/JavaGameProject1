package character.interActorObject.Laser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;

public class LaserObjectBase extends Actor{

    private final Texture base;
    private Sprite sprite_base;
    private float origin_x;
    private float origin_y;
    private String type;
    private Body body;

    public LaserObjectBase(Body body,String texture2,String type,float weight2 , float height2 ,float offset_x,float offset_y){
        base = new Texture(Gdx.files.internal(texture2));
        this.type = type;
        this.body = body;
        this.origin_x = body.getPosition().x;
        this.origin_y = body.getPosition().y;
        sprite_base = new Sprite(base);
        sprite_base.setPosition(offset_x, offset_y);
        sprite_base.setSize(weight2 * GameMode.PPM, height2 * GameMode.PPM);
    }
    public void draw(Batch batch, float parentAlpha) {
        if(type.equals("rile")||type.equals("leri")) {
            batch.draw(base, sprite_base.getX()+origin_x, body.getPosition().y+sprite_base.getY(), sprite_base.getWidth() / GameMode.PPM, sprite_base.getHeight() / GameMode.PPM);
        }
        else {
            batch.draw(base, body.getPosition().x+sprite_base.getX(), sprite_base.getY()+origin_y, sprite_base.getWidth() / GameMode.PPM, sprite_base.getHeight() / GameMode.PPM);
        }
    }
    public void dispose() {
        base.dispose();
    }

}
