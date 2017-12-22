package dblades01.qub.ac.uk.tests;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.hardware.SensorEventListener;
import android.view.WindowManager;
import android.widget.TextView;

public class AccelerometerTest extends AppCompatActivity implements SensorEventListener {
    private int screenRotation;
    private TextView output;
    private StringBuilder outputBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean accelerometerPresent, accelerometerRegistered;
        SensorManager sensorManager;
        Sensor accelerometer;

        super.onCreate(savedInstanceState);

        output = new TextView(this);
        outputBuilder = new StringBuilder();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerPresent = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0;
        accelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        accelerometerRegistered = sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        if(!accelerometerPresent)
            output.setText("Accelerometer not present.");
        else if(!accelerometerRegistered)
            output.setText("Acceleromter could not register the activity as a listener.");

        setContentView(output);
    }

    @Override
    public void onResume() {
        WindowManager windowManager;

        super.onResume();

        windowManager = (WindowManager) getSystemService(Activity.WINDOW_SERVICE);
        screenRotation = windowManager.getDefaultDisplay().getRotation();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final int[][] ACCELEROMETER_SWAP_SETS = {
                {1, -1, 0, 1},
                {-1, -1, 1, 0},
                {-1, 1, 0, 1},
                {1, 1, 1, 0}
        };
        final int[] ACCELEROMETER_SWAP = ACCELEROMETER_SWAP_SETS[screenRotation];
        float accelerationX = (float) ACCELEROMETER_SWAP[0] * event.values[ACCELEROMETER_SWAP[2]],
                accelerationY = (float) ACCELEROMETER_SWAP[1] * event.values[ACCELEROMETER_SWAP[3]],
                accelerationZ = event.values[2];

        outputBuilder.setLength(0);
        outputBuilder.append("x ");
        outputBuilder.append(accelerationX);
        outputBuilder.append(", ");
        outputBuilder.append("y ");
        outputBuilder.append(accelerationY);
        outputBuilder.append(", ");
        outputBuilder.append("z ");
        outputBuilder.append(accelerationZ);
        output.setText(outputBuilder.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
