package character.interActerObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class TestObject {
    String picturePath= "wallSample.png";

    public Rectangle rigid_body;
    public Texture texture;

    public TestObject(float x, float y){
        rigid_body = new Rectangle();
        texture = new Texture(Gdx.files.internal(picturePath));

        rigid_body.x=x;
        rigid_body.y=y;
        rigid_body.width=texture.getWidth();
        rigid_body.height=texture.getHeight();

    }


    public void dispose(){
        texture.dispose();
    }

}
