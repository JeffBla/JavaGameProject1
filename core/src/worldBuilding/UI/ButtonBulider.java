package worldBuilding.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonBulider {
    private static Texture Button_Up;
    private static Texture Button_Down;
    private static Texture Button_Check;

    public static ImageButton createImgButton(Texture up, Texture down, Texture checked,
                                              float posX, float posY,EventListener listener) {
        Drawable ButtonUpDrawable = new TextureRegionDrawable(up);
        Drawable ButtonDownDrawable = new TextureRegionDrawable(down);
        Drawable ButtonCheckedDrawable = new TextureRegionDrawable(checked);
        ImageButton imgButton = new ImageButton(ButtonUpDrawable, ButtonDownDrawable, ButtonCheckedDrawable);
        imgButton.setPosition(posX, posY);
        imgButton.addListener(listener);

        return imgButton;
    }
    public static ImageButton createImgButton(Texture up, Texture down, Texture checked,
                                              float posX, float posY, float width, float height,
                                              EventListener listener) {
        Drawable ButtonUpDrawable = new TextureRegionDrawable(up);
        Drawable ButtonDownDrawable = new TextureRegionDrawable(down);
        Drawable ButtonCheckedDrawable = new TextureRegionDrawable(checked);
        ImageButton imgButton = new ImageButton(ButtonUpDrawable, ButtonDownDrawable, ButtonCheckedDrawable);
        imgButton.setHeight(height);
        imgButton.setWidth(width);
        imgButton.setPosition(posX, posY);
        imgButton.addListener(listener);

        return imgButton;
    }
    public static ImageButton createImgButton(String up, String down, String checked,
                                              float posX, float posY,EventListener listener) {
        Button_Up = new Texture(Gdx.files.internal(up));
        Button_Down = new Texture(Gdx.files.internal(down));
        Button_Check = new Texture(Gdx.files.internal(checked));

        Drawable ButtonUpDrawable = new TextureRegionDrawable(Button_Up);
        Drawable ButtonDownDrawable = new TextureRegionDrawable(Button_Down);
        Drawable ButtonCheckedDrawable = new TextureRegionDrawable(Button_Check);
        ImageButton imgButton = new ImageButton(ButtonUpDrawable, ButtonDownDrawable, ButtonCheckedDrawable);
        imgButton.setPosition(posX, posY);
        imgButton.addListener(listener);

        return imgButton;
    }

    public static ImageButton createImgButton(String up, String down, String checked,
                                              float posX, float posY, float width, float height,
                                              EventListener listener) {
        Button_Up = new Texture(Gdx.files.internal(up));
        Button_Down = new Texture(Gdx.files.internal(down));
        Button_Check = new Texture(Gdx.files.internal(checked));

        Drawable ButtonUpDrawable = new TextureRegionDrawable(Button_Up);
        Drawable ButtonDownDrawable = new TextureRegionDrawable(Button_Down);
        Drawable ButtonCheckedDrawable = new TextureRegionDrawable(Button_Check);
        ImageButton imgButton = new ImageButton(ButtonUpDrawable, ButtonDownDrawable, ButtonCheckedDrawable);
        imgButton.setHeight(height);
        imgButton.setWidth(width);
        imgButton.setPosition(posX, posY);
        imgButton.addListener(listener);

        return imgButton;
    }

    /** If use this class, you dispose the resources */
    public static void dispose(){
        Button_Up.dispose();
        Button_Down.dispose();
        Button_Check.dispose();
    }
}


