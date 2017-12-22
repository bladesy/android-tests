package dblades01.qub.ac.uk.tests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MultiTouchTest extends AppCompatActivity implements OnTouchListener {
    private final int MAX_POINTERS = 10;

    private int[] pointerIds = new int[MAX_POINTERS];
    private float[] pointerXValues = new float[MAX_POINTERS],
        pointerYValues = new float[MAX_POINTERS];
    private boolean[] pointerTouchValues = new boolean[MAX_POINTERS];

    private TextView output;
    private StringBuilder outputBuilder;

    private void updateOutput() {
        outputBuilder.setLength(0);

        for(int i = 0; i < MAX_POINTERS; ++i) {
            outputBuilder.append(pointerTouchValues[i]);
            outputBuilder.append(", ");
            outputBuilder.append(pointerIds[i]);
            outputBuilder.append(", ");
            outputBuilder.append(pointerXValues[i]);
            outputBuilder.append(", ");
            outputBuilder.append(pointerYValues[i]);
            outputBuilder.append("\n");
        }

        output.setText(outputBuilder.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        output = new TextView(this);
        outputBuilder = new StringBuilder();

        output.setOnTouchListener(this);
        output.setText("Touch and drag with multiple fingers.");
        setContentView(output);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action, pointerIndex, pointerCount;

        action = event.getAction() & MotionEvent.ACTION_MASK;
        pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        pointerCount = event.getPointerCount();

        for(int i = 0; i < MAX_POINTERS; ++i) {
            int pointerId;

            if(i >= pointerCount) {
                pointerIds[i] = -1;
                pointerTouchValues[i] = false;
                continue;
            }

            if(event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex)
                continue;

            pointerId = event.getPointerId(i);

            switch(action) {
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    pointerIds[i] = pointerId;
                    pointerTouchValues[i] = true;
                    pointerXValues[i] = event.getX(i);
                    pointerYValues[i] = event.getY(i);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_OUTSIDE:
                case MotionEvent.ACTION_CANCEL:
                    pointerIds[i] = -1;
                    pointerTouchValues[i] = false;
                    pointerXValues[i] = event.getX(i);
                    pointerYValues[i] = event.getY(i);
                    break;
            }
        }

        updateOutput();
        return true;
    }
}
