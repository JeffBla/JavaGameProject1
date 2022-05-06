package character.mainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;

public class MainCharacterSoundEffect {

    private Sound run;
    private Sound sword_cut_air;
    private Sound sword_shoosh;

    public MainCharacterSoundEffect() {
        run = Gdx.audio.newSound(Gdx.files.internal("Sound/character_run.mp3"));
        sword_cut_air = Gdx.audio.newSound(Gdx.files.internal("Sound/sword_cut_air.mp3"));
        sword_shoosh = Gdx.audio.newSound(Gdx.files.internal("Sound/sword_shoosh.mp3"));
    }

    private static float run_Timer = 0;
    private static boolean isRunPlayed = false;

    public void playRun_sound() {
        if (!isRunPlayed) {
            isRunPlayed = true;
            run.play();
        }
        if (run_Timer >= 3.5f) {
            isRunPlayed = false;
            run_Timer = 0;
        }
        run_Timer += 0.02f;
    }

    public void stopRun_sound() {
        isRunPlayed=false;
        run_Timer=0;
        run.stop();
    }

    private static float sword_cut_air_Timer = 0;

    public void playSword_cut_air_sound() {
        if (sword_cut_air_Timer >= 0.7f) {
            sword_cut_air_Timer = -0.03f; // this sound can have many time to wait
        }
        if (sword_cut_air_Timer <= 0) {
            sword_cut_air.play();
        }

        sword_cut_air_Timer += 0.02; // delta is roughly equal to 0.02f
    }

    private static float sword_shoosh_Timer = 0f;
    private static boolean isStart = false;
    public void playSword_shoosh_sound() {
        if(isStart) {
            isStart = true;
            sword_shoosh.play();
            sword_shoosh_Timer = TimeUtils.nanoTime();
        }
        if (TimeUtils.nanoTime() - sword_shoosh_Timer >= 600000000) {
            sword_shoosh_Timer = 0;
            isStart = false;
        }
    }
}
