package dblades01.qub.ac.uk.tests;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    private String[] tests = {
            "LifeCycleTest",
            "SingleTouchTest",
            "MultiTouchTest",
            "KeyTest",
            "AccelerometerTest",
            "AssetsTest",
            "ExternalStorageTest",
            "SoundPoolTest",
            "MediaPlayerTest",
            "FullScreenTest",
            "RenderViewTest",
            "ShapeTest",
            "BitmapTest",
            "FontTest",
            "SurfaceViewTest"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tests));
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        String testName = tests[position];

        super.onListItemClick(list, view, position, id);

        try {
            Class testClass = Class.forName("dblades01.qub.ac.uk.tests." + testName);
            Intent testIntent = new Intent(this, testClass);
            startActivity(testIntent);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
