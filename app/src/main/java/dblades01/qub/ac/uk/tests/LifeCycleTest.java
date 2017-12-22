package dblades01.qub.ac.uk.tests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class LifeCycleTest extends AppCompatActivity {
    private TextView logOutput;

    private void log(String message) {
        Log.d("LifeCycleTest", message);
        logOutput.append(message + '\n');
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logOutput = new TextView(this);
        setContentView(logOutput);
        log("Created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("Resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("Paused");

        if(isFinishing())
            log("Finishing");
    }
}
