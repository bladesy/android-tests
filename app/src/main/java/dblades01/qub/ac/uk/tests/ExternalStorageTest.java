package dblades01.qub.ac.uk.tests;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ExternalStorageTest extends AppCompatActivity {
    private final int READ_WRITE_EXTERNAL_STORAGE = 0;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        output = new TextView(this);
        setContentView(output);

        if((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
            &&
            (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    READ_WRITE_EXTERNAL_STORAGE);
        }
        else {
            readExternalStorage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
        int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == READ_WRITE_EXTERNAL_STORAGE) {
            if(Arrays.equals(permissions, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE })
                &&
                Arrays.equals(grantResults, new int[]{PackageManager.PERMISSION_GRANTED,
                PackageManager.PERMISSION_GRANTED})) {
                readExternalStorage();
            }
            else {
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readExternalStorage() {
        String state = Environment.getExternalStorageState();

        if(state.equals(Environment.MEDIA_MOUNTED)) {
            File extDirectory = Environment.getExternalStorageDirectory();
            File textFile = new File(extDirectory.getAbsolutePath() + File.separator
                + "readWriteText.txt");

            try {
                writeTextFile(textFile, "Writing text to the text file test.");
                output.setText(readTextFile(textFile));

                if(!textFile.delete())
                    output.setText("Could not delete text file.");
            }
            catch(IOException e) {
                output.setText("IOException occurred: " + e.getMessage());
            }
        }
        else {
            output.setText("No external storage mounted.");
        }
    }

    private void writeTextFile(File file, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(text);
        writer.close();
    }

    private String readTextFile(File file) throws IOException {
        String line;
        StringBuilder textBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while((line = reader.readLine()) != null) {
            textBuilder.append(line);
            textBuilder.append('\n');
        }
        reader.close();

        return textBuilder.toString();
    }
}
