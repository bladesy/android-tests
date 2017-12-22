package dblades01.qub.ac.uk.tests;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import java.io.IOException;

public class SoundPoolTest extends AppCompatActivity implements OnTouchListener {
    int soundId = -1;
    SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView output;

        super.onCreate(savedInstanceState);

        output = new TextView(this);
        output.setOnTouchListener(this);
        setContentView(output);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        try {
            soundId = soundPool.load(getAssets().openFd("sound.mp3"), 1);
        }
        catch(IOException e) {
            output.setText("Sound could not be loaded: " + e.getMessage());
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent touch) {
        if((touch.getAction() == MotionEvent.ACTION_UP) && (soundId != -1))
            soundPool.play(soundId, 1, 1, 0, 0, 1);

        return true;
    }
}
