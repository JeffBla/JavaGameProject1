package character.interActorObject.Laser;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

public class Laser extends Actor{

    private LaserBase base;
    private LaserLine line;
    private String type;
    private long start=0;
    private float speedX=0;
    private float speedY=0;
    private boolean onAttack = true;

    public Laser(World gameWorld, String textureLine,String textureBase,String type, boolean canVanish ,  float lineX, float lineY,
                 float lineWidth, float lineHeight, float animaDuration, float speedX, float speedY, float fixBoxOrigin_constant,
                 float fixBoxWidth_constant, float fixBoxHeight_constant,float offsetX , float offsetY ,float baseWidth,float baseHeight) {
        line = new LaserLine(gameWorld,textureLine,type,canVanish,lineX,lineY,lineWidth,lineHeight,
                animaDuration,fixBoxOrigin_constant,
                fixBoxWidth_constant,fixBoxHeight_constant);
        base = new LaserBase(line.getBody(),textureBase,type,offsetX,offsetY,baseWidth,baseHeight);
        this.type = type;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public void act(float delta) {
        if(onAttack == true) {
            line.act(delta);
        }
        base.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(onAttack == true) {
            line.draw(batch, parentAlpha);
        }
        base.draw(batch, parentAlpha);
    }
    public long getStart() {
        return this.start;
    }

    public void setStartTime() {
        start = TimeUtils.nanoTime();
    }

    public LaserBase getBase() {
        return base;
    }
    public LaserLine getLine() {
        return line;
    }


    public String getType() {
        return type;
    }

    public boolean getAttack() {
        return onAttack;
    }

    public void setAttack(boolean condition) {
        onAttack = condition;
    }

    public void moveX(float x0, float x1) {
        if (line.getBody().getPosition().x > x1 || line.getBody().getPosition().x < x0) {
            speedX = -speedX;
        }
        line.getBody().setTransform(line.getBody().getPosition().x - speedX, line.getBody().getPosition().y, 0);
    }

    public void moveY(float y0, float y1) {
        if (line.getBody().getPosition().y > y1 || line.getBody().getPosition().y < y0) {
            speedY = -speedY;
        }
        line.getBody().setTransform(line.getBody().getPosition().x, line.getBody().getPosition().y - speedY, 0);
    }

    public void dispose() {
        line.dispose();
        base.dispose();
    }
}
