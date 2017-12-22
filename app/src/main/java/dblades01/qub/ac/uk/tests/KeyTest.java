package dblades01.qub.ac.uk.tests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.TextView;

public class KeyTest extends AppCompatActivity implements OnKeyListener {
    private TextView output;
    private StringBuilder outputBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        output = new TextView(this);
        outputBuilder = new StringBuilder();

        output.setOnKeyListener(this);
        output.setText("Use the keyboard.");
        output.setFocusableInTouchMode(true);
        output.requestFocus();
        setContentView(output);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        outputBuilder.setLength(0);

        switch(event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                outputBuilder.append("Down, ");
                break;
            case KeyEvent.ACTION_UP:
                outputBuilder.append("Up, ");
                break;
        }

        outputBuilder.append(keyCode);
        outputBuilder.append(", ");
        outputBuilder.append((char) event.getUnicodeChar());

        Log.d("KeyTest", outputBuilder.toString());
        output.setText(outputBuilder.toString());

        return keyCode != KeyEvent.KEYCODE_BACK;
    }
}
