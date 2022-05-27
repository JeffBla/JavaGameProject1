package character.interActorObject.Cannon;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameMode;
import worldBuilding.BuildBody;

public class Cannon extends Actor{
    private Body body_cannon;
    private Body body_mainCharactor;
    private boolean onAttack = false;
    private long start = 0;
    private final Texture texture = new Texture(Gdx.files.internal("Cannon/base.png"));
    private final Sprite sprite;
    private float speed ;
    private int stop =0 ;
    private float move_speed;
    private double angle = 0;
    private double angle_2 =0 ;
    private double angle_2_fix=0;
    double target_x;
    double target_y;
    float random_x = 0;
    float random_y = 0;
    double distance_x;
    double distance_y;
    double distance_x_fix = 0;
    double distance_x_2;
    double distance_y_2;
    double distance_y_fix = 0;
    double distance_thirdLine;
    double distance_thirdLine_2;
    double distance_thirdLine_fix = 0;
    private boolean target = false;
    Random rand = new Random();

    public Cannon(World gameWorld, Body body_mainCharactor, float x, float y, float weight, float height, float speed,
                  float fixBoxOrigin_constant, float fixBoxWeight_constant, float fixBoxHeight_constant) {
        this.body_mainCharactor = body_mainCharactor;
        this.move_speed = speed;
        sprite = new Sprite(texture);
        sprite.setPosition(x, y - height * GameMode.PPM/2);
        sprite.setSize(weight * GameMode.PPM, height * GameMode.PPM);
        body_cannon = BuildBody.createBox(gameWorld, x, y, weight / 2 + fixBoxWeight_constant,
                height / 2 + fixBoxHeight_constant, new Vector2(sprite.getWidth() / 2f / GameMode.PPM,
                        sprite.getHeight() / 2f / GameMode.PPM + fixBoxOrigin_constant),
                0, 0, 0, false, false, true);
        body_cannon.setUserData(this);
    }



    public String check_right_left() {
        //target原點，蜉蝣砲在第幾項縣
        //一
        if(body_cannon.getPosition().x >= target_x &&
                body_cannon.getPosition().y >= target_y) {
            return "1";
        }
        //二
        else if(body_cannon.getPosition().x <= target_x &&
                body_cannon.getPosition().y >= target_y) {
            return "2";
        }
        //三
        else if(body_cannon.getPosition().x <= target_x &&
                body_cannon.getPosition().y <= target_y) {
            return "3";
        }
        //四
        else if(body_cannon.getPosition().x >= target_x &&
                body_cannon.getPosition().y <= target_y) {
            return "4";
        }
        return "exception";
    }
    public String check_right_left_charactor() {
        //主角原點，蜉蝣砲在第幾項縣
        //一
        if(body_cannon.getPosition().x >= body_mainCharactor.getPosition().x &&
                body_cannon.getPosition().y >= body_mainCharactor.getPosition().y) {
            return "1";
        }
        //二
        else if(body_cannon.getPosition().x <= body_mainCharactor.getPosition().x &&
                body_cannon.getPosition().y >= body_mainCharactor.getPosition().y) {
            return "2";
        }
        //三
        else if(body_cannon.getPosition().x <= body_mainCharactor.getPosition().x &&
                body_cannon.getPosition().y <= body_mainCharactor.getPosition().y) {
            return "3";
        }
        //四
        else if(body_cannon.getPosition().x >= body_mainCharactor.getPosition().x &&
                body_cannon.getPosition().y <= body_mainCharactor.getPosition().y) {
            return "4";
        }
        return "exception";
    }

    @Override
    public void act(float delta) {
        // move
        if(onAttack == false) {
            while(target==false) {
//				random_x = rand.nextFloat(-7f, 7f);
//				random_y = rand.nextFloat(-7f, 7f);
//				if(Math.sqrt(Math.pow(random_x, 2)+Math.pow(random_y, 2))>6 &&
//						Math.sqrt(Math.pow(random_x, 2)+Math.pow(random_y, 2))<7) {
//					target = true;
//					target_x = body_mainCharactor.getPosition().x + random_x;
//					target_y = body_mainCharactor.getPosition().y + random_y;
//					distance_x = Math.abs(body_cannon.getPosition().x - target_x);
//					distance_y = Math.abs(body_cannon.getPosition().y - target_y);
//					angle = Math.atan(distance_y/distance_x);
//				}
                target = true;
                target_x = body_mainCharactor.getPosition().x - 5f;
                target_y = body_mainCharactor.getPosition().y + 5f;
                distance_x = Math.abs(body_cannon.getPosition().x - target_x);
                distance_y = Math.abs(body_cannon.getPosition().y - target_y);
                distance_thirdLine = get_long(distance_x,distance_y);
                angle = Math.atan(distance_y/distance_x);
            }
            if(target==true) {
                distance_x_2 = Math.abs(body_cannon.getPosition().x - body_mainCharactor.getPosition().x);
                distance_y_2 = Math.abs(body_cannon.getPosition().y - body_mainCharactor.getPosition().y);
                distance_thirdLine_2 = get_long(distance_x_2,distance_y_2);

                angle_2 = Math.atan(distance_y_2/distance_x_2);
//				{	distance_x_fix = Math.sqrt(Math.pow( body_mainCharactor.getPosition().x - body_dot.getPosition().x ,2 ));
//					distance_y_fix = Math.sqrt(Math.pow( body_mainCharactor.getPosition().y - body_dot.getPosition().y ,2 ));
//					distance_thirdLine_fix = get_long(distance_x_fix,distance_y_fix);
//					angle_2_fix = Math.acos((Math.pow(1.5, 2)+Math.pow(distance_thirdLine_2, 2)-Math.pow(distance_thirdLine_fix, 2)) / (2*1.5*distance_thirdLine_2));
//				}
//				System.out.println(distance_x_fix);
//				System.out.println(distance_y_fix);
//				System.out.println(distance_thirdLine_fix);
//				System.out.println(Math.toDegrees(angle_2_fix));
                {
                    //distance_x_fix = Math.sqrt(Math.pow( body_mainCharactor.getPosition().x - body_dot.getPosition().x ,2 ));
                    //distance_y_fix = Math.sqrt(Math.pow( body_mainCharactor.getPosition().y - body_dot.getPosition().y ,2 ));
                    //angle_2_fix = Math.atan(distance_y_fix/distance_x_fix);
                    //System.out.println(Math.toDegrees(angle_2_fix));
                    //angle_2 = angle_2_fix;
                }
                float speed_x =0;
                float speed_y =0;
                if(check_right_left().equals("1")) {
                    speed_x = (float) (-speed*Math.cos(angle));
                    speed_y = (float) (-speed*Math.sin(angle));
                }
                else if(check_right_left().equals("2")) {
                    speed_x = (float) (speed*Math.cos(angle));
                    speed_y = (float) (-speed*Math.sin(angle));
                }
                else if(check_right_left().equals("3")) {
                    speed_x = (float) (speed*Math.cos(angle));
                    speed_y = (float) (speed*Math.sin(angle));
                }
                else if(check_right_left().equals("4")) {
                    speed_x = (float) (-speed*Math.cos(angle));
                    speed_y = (float) (speed*Math.sin(angle));
                }


                if(check_right_left_charactor().equals("1")) {
                    angle_2 = (float) Math.toRadians(Math.toDegrees(angle_2)+180);
                }
                else if(check_right_left_charactor().equals("2")) {
                    angle_2 = -(float) Math.toRadians( Math.toDegrees(angle_2));
                }
                else if(check_right_left_charactor().equals("3")) {
                    angle_2 = (float) Math.toRadians(Math.toDegrees(angle_2));
                }
                else if(check_right_left_charactor().equals("4")) {
                    angle_2 = -(float) Math.toRadians(Math.toDegrees(angle_2)+180);
                }
                body_cannon.setTransform(body_cannon.getPosition().x + speed_x,body_cannon.getPosition().y + speed_y , (float) angle_2);
                if ( ( (body_cannon.getPosition().x >= target_x - 0.3f)&&(body_cannon.getPosition().x <= target_x + 0.3f) )
                        && ( (body_cannon.getPosition().y >= target_y - 0.3f)&&(body_cannon.getPosition().y <= target_y + 0.3f) ) )  {
                    target=false;
                    speed=stop;
                }
            }
        }
        else if(onAttack == true) {
            speed = move_speed;
        }
        //body_dot.setTransform((float)( body_cannon.getPosition().x + 1.0*Math.cos(body_cannon.getAngle()+Math.toRadians(33.69))), (float)( body_cannon.getPosition().y + 1.0*Math.sin(body_cannon.getAngle()+Math.toRadians(33.69))), 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, body_cannon.getPosition().x, body_cannon.getPosition().y, 0, 0, sprite.getWidth() / GameMode.PPM, sprite.getHeight() / GameMode.PPM, 1, 1, (float)Math.toDegrees(angle_2));
    }

    public Body get_body() {
        return body_cannon;
    }

    public long get_start() {
        return this.start;
    }

    public void set_startTime() {
        start = TimeUtils.nanoTime();
    }

    public boolean get_attack() {
        return onAttack;
    }

    public void set_attack(boolean condition) {
        onAttack = condition;
    }

    public boolean get_target() {
        return target;
    }

    public void set_target(boolean condition) {
        target = condition;
    }

    public double get_long(double a,double b) {
        return Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
    }

    public void dispose() {
        texture.dispose();
    }

}
