package dblades01.qub.ac.uk.tests;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

public class MediaPlayerTest extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView output;

        super.onCreate(savedInstanceState);

        output = new TextView(this);
        setContentView(output);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer = new MediaPlayer();

        try {
            AssetFileDescriptor musicAsset = getAssets().openFd("music.wav");
            mediaPlayer.setDataSource(musicAsset.getFileDescriptor(), musicAsset.getStartOffset(),
                musicAsset.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        }
        catch(IOException e) {
            mediaPlayer = null;
            output.setText("Music could not be loaded: " + e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mediaPlayer != null)
            mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mediaPlayer != null) {
            mediaPlayer.pause();

            if(isFinishing()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }
}
