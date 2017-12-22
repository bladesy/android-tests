package dblades01.qub.ac.uk.tests;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsTest extends AppCompatActivity {
    public String loadTextFile(InputStream textFileData) throws IOException {
        int bytesRead = 0;
        byte[] bytes = new byte[4096];
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();

        while((bytesRead = textFileData.read(bytes)) > 0)
            bytesOut.write(bytes, 0, bytesRead);

        return new String(bytesOut.toByteArray(), "UTF8");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView output = new TextView(this);
        AssetManager assetManager = this.getAssets();
        InputStream textFileData = null;
        String outputText;

        super.onCreate(savedInstanceState);

        try {
            textFileData = assetManager.open("text.txt");
            outputText = loadTextFile(textFileData);
            output.setText(outputText);
        }
        catch(IOException e) {
            output.setText("Could not load file.");
        }
        finally {
            if(textFileData != null) {
                try {
                    textFileData.close();
                }
                catch(IOException e) {
                    output.setText("Could not close file.");
                }
            }
        }

        setContentView(output);
    }
}
