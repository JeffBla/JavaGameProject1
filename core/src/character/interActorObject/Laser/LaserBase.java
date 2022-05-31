package character.interActorObject.Laser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;

public class LaserBase extends Actor{

    private final Texture base;
    private Sprite spriteBase;
    private float originX;
    private float originY;
    private String type;
    private Body body;

    public LaserBase(Body body,String texture,String type,float offsetX ,float offsetY,float width , float height){
        base = new Texture(Gdx.files.internal(texture));
        this.type = type;
        this.body = body;
        this.originX = body.getPosition().x;
        this.originY = body.getPosition().y;
        spriteBase = new Sprite(base);
        spriteBase.setPosition(offsetX, offsetY);
        spriteBase.setSize(width * GameMode.PPM, height * GameMode.PPM);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(type.equals("rile")||type.equals("leri")) {
            batch.draw(base, spriteBase.getX()+originX, body.getPosition().y+spriteBase.getY(), spriteBase.getWidth() / GameMode.PPM, spriteBase.getHeight() / GameMode.PPM);
        }
        else {
            batch.draw(base, body.getPosition().x+spriteBase.getX(), spriteBase.getY()+originY, spriteBase.getWidth() / GameMode.PPM, spriteBase.getHeight() / GameMode.PPM);
        }
    }

    public void dispose() {
        base.dispose();
    }

}
