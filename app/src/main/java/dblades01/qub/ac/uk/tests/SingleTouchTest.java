package dblades01.qub.ac.uk.tests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class SingleTouchTest extends AppCompatActivity implements OnTouchListener {
    private TextView touchOutput;
    private StringBuilder outputBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        touchOutput = new TextView(this);
        outputBuilder = new StringBuilder();
        outputBuilder.append("Touch and drag with one finger only");
        touchOutput.setText(outputBuilder.toString());
        touchOutput.setOnTouchListener(this);
        setContentView(touchOutput);
    }

    @Override
    public boolean onTouch(View view, MotionEvent touch) {
        outputBuilder.setLength(0);

        switch(touch.getAction()) {
            case MotionEvent.ACTION_DOWN:
                outputBuilder.append("Down, ");
                break;
            case MotionEvent.ACTION_MOVE:
                outputBuilder.append("Move, ");
                break;
            case MotionEvent.ACTION_CANCEL:
                outputBuilder.append("Down, ");
                break;
            case MotionEvent.ACTION_UP:
                outputBuilder.append("Up, ");
                break;
        }

        outputBuilder.append(touch.getX() + ", " + touch.getY());
        touchOutput.setText(outputBuilder.toString());
        Log.d("TouchTest", outputBuilder.toString());

        return true;
    }
}
