package character.interActorObject.Cannon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameMode;
import worldBuilding.BuildBody;

public class CannonBase extends Actor{
    private final Body bodyCannon;
    private final Body bodyMainCharacter;
    private boolean target = false;
    private boolean move = false;
    private boolean beAttacked = false;
    private boolean onRecover = false;
    private final Texture texture = new Texture(Gdx.files.internal("Cannon/base.png"));
    private final Sprite sprite;
    private float speed ;
    private final int stop = 0 ;
    private final float moveSpeed;
    private double angle = 0;
    private double angle2 =0 ;
    double targetX;
    double targetY;
    double distanceX;
    double distanceY;
    double distanceX2;
    double distanceY2;

    public CannonBase(World gameWorld, Body bodyMainCharacter, float x, float y, float width, float height, float speed,
                      float fixBoxOrigin_constant, float fixBoxWidth_constant, float fixBoxHeight_constant) {
        this.bodyMainCharacter = bodyMainCharacter;
        this.moveSpeed = speed;
        sprite = new Sprite(texture);
        sprite.setPosition(x, y - height * GameMode.PPM/2);
        sprite.setSize(width * GameMode.PPM, height * GameMode.PPM);
        bodyCannon = BuildBody.createBox(gameWorld, x, y, width / 2 + fixBoxWidth_constant,
                height / 2 + fixBoxHeight_constant, new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
                        sprite.getHeight() / 2f / GameMode.PPM + fixBoxOrigin_constant),
                0, 0, 0, false, false, true);
        bodyCannon.setUserData(this);
    }



    public String checkQuadrant() {
        if(bodyCannon.getPosition().x >= targetX &&
                bodyCannon.getPosition().y >= targetY) {
            return "1";
        }
        else if(bodyCannon.getPosition().x <= targetX &&
                bodyCannon.getPosition().y >= targetY) {
            return "2";
        }
        else if(bodyCannon.getPosition().x <= targetX &&
                bodyCannon.getPosition().y <= targetY) {
            return "3";
        }
        else if(bodyCannon.getPosition().x >= targetX &&
                bodyCannon.getPosition().y <= targetY) {
            return "4";
        }
        return "exception";
    }
    public String checkQuadrantCharacter() {
        if(bodyCannon.getPosition().x >= bodyMainCharacter.getPosition().x &&
                bodyCannon.getPosition().y >= bodyMainCharacter.getPosition().y) {
            return "1";
        }
        else if(bodyCannon.getPosition().x <= bodyMainCharacter.getPosition().x &&
                bodyCannon.getPosition().y >= bodyMainCharacter.getPosition().y) {
            return "2";
        }
        else if(bodyCannon.getPosition().x <= bodyMainCharacter.getPosition().x &&
                bodyCannon.getPosition().y <= bodyMainCharacter.getPosition().y) {
            return "3";
        }
        else if(bodyCannon.getPosition().x >= bodyMainCharacter.getPosition().x &&
                bodyCannon.getPosition().y <= bodyMainCharacter.getPosition().y) {
            return "4";
        }
        return "exception";
    }

    @Override
    public void act(float delta) {
        if(target==false) {
            speed = moveSpeed;
            targetX = bodyMainCharacter.getPosition().x - 5f;
            targetY = bodyMainCharacter.getPosition().y + 5f;
            distanceX = Math.abs(bodyCannon.getPosition().x - targetX);
            distanceY = Math.abs(bodyCannon.getPosition().y - targetY);
            angle = Math.atan(distanceY/distanceX);
            target = true;
            move = true;
        }
        if(move==true) {
            distanceX2 = Math.abs(bodyCannon.getPosition().x - bodyMainCharacter.getPosition().x);
            distanceY2 = Math.abs(bodyCannon.getPosition().y - bodyMainCharacter.getPosition().y);
            angle2 = Math.atan(distanceY2/distanceX2);
            float speedX =0;
            float speedY =0;
            if(checkQuadrant().equals("1")) {
                speedX = (float) (-speed*Math.cos(angle));
                speedY = (float) (-speed*Math.sin(angle));
            }
            else if(checkQuadrant().equals("2")) {
                speedX = (float) (speed*Math.cos(angle));
                speedY = (float) (-speed*Math.sin(angle));
            }
            else if(checkQuadrant().equals("3")) {
                speedX = (float) (speed*Math.cos(angle));
                speedY = (float) (speed*Math.sin(angle));
            }
            else if(checkQuadrant().equals("4")) {
                speedX = (float) (-speed*Math.cos(angle));
                speedY = (float) (speed*Math.sin(angle));
            }

            if(checkQuadrantCharacter().equals("1")) {
                angle2 = (float) Math.toRadians(Math.toDegrees(angle2)+180);
            }
            else if(checkQuadrantCharacter().equals("2")) {
                angle2 = -(float) Math.toRadians( Math.toDegrees(angle2));
            }
            else if(checkQuadrantCharacter().equals("3")) {
                angle2 = (float) Math.toRadians(Math.toDegrees(angle2));
            }
            else if(checkQuadrantCharacter().equals("4")) {
                angle2 = -(float) Math.toRadians(Math.toDegrees(angle2)+180);
            }

            bodyCannon.setTransform(bodyCannon.getPosition().x + speedX,bodyCannon.getPosition().y + speedY , (float) angle2);

            if ( ( (bodyCannon.getPosition().x >= targetX - 0.3f)&&(bodyCannon.getPosition().x <= targetX + 0.3f) )
                    && ( (bodyCannon.getPosition().y >= targetY - 0.3f)&&(bodyCannon.getPosition().y <= targetY + 0.3f) ) )  {
                speed=stop;
                move = false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, bodyCannon.getPosition().x, bodyCannon.getPosition().y, 0, 0, sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM, 1, 1, (float)Math.toDegrees(angle2));
    }

    public Body getBody() {
        return bodyCannon;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean getTarget() {
        return target;
    }

    public void setTarget(boolean condition) {
        target = condition;
    }

    public void dispose() {
        texture.dispose();
    }
    public boolean getMove() {
        return move;
    }

    public void setMove(boolean condition) {
        move = condition;
    }

    public void setBeAttacked(boolean hit) {
        beAttacked = hit;
    }

    public boolean getBeAttacked() {
        return beAttacked;
    }

    public void setOnRecover(boolean condition) {
        onRecover = condition;
    }

    public boolean getOnRecover() {
        return onRecover;
    }
}

