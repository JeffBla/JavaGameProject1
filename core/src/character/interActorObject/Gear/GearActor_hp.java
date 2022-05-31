package character.interActorObject.Gear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GearActor_hp {
    private Texture healthBarFrame;
    private Texture healthBarBlood;
    private SpriteBatch batch;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    private BitmapFont font;
    private float hp=100;
    public GearActor_hp() {
        batch = new SpriteBatch();
        healthBarFrame = new Texture(Gdx.files.internal("healthBar/healthBar_frame.png"));
        healthBarBlood = new Texture(Gdx.files.internal("healthBar/healthBar_blood.png"));
        {
            generator = new FreeTypeFontGenerator(Gdx.files.internal("msjh.ttc"));
            parameter = new FreeTypeFontParameter();
            parameter.size = 165 ;
            parameter.padRight = 20 ;
            parameter.padLeft = 20 ;
            parameter.padTop = 100 ;
            parameter.padBottom = 100;
            parameter.color = Color.BLACK;
            parameter.characters = "1234567890/%";
            font = generator.generateFont(parameter);
            font.getData().setScale(0.2f,0.15f);
            font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            generator.dispose();
        }
    }

    public void render(float delta) {
        batch.begin();
        batch.draw(healthBarFrame, 250, 50f, 1350, 40);
        batch.draw(healthBarBlood, 250, 50f, (float)13.5*hp, 40);
        if(hp>=10)
            font.draw(batch, forhp(), 150+(float)13.5*hp, 95);
        else
            font.draw(batch, forhp(), 270, 95);
        batch.end();
    }

    public String forhp() {
        return Integer.toString((int)hp) + "%";
    }

    public float getHp(){
        return hp;
    }

    public void deHp(int injure) {
        hp-=injure;
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        healthBarFrame.dispose();
        healthBarBlood.dispose();
    }
}
