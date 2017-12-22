package dblades01.qub.ac.uk.tests;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

public class BitmapTest extends AppCompatActivity {
    private class RenderView extends View {
        Bitmap ARGB8888, RGB888;
        Rect destination = new Rect();

        public RenderView(Context context) {
            super(context);

            AssetManager assetManager;
            InputStream bitmapInput;
            BitmapFactory.Options options;

            assetManager = context.getAssets();
            options = new BitmapFactory.Options();

            try {
                bitmapInput = assetManager.open("ARGB8888.png");
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                ARGB8888 = BitmapFactory.decodeStream(bitmapInput, null, options);
                bitmapInput.close();
                Log.d("BitmapTest", "ARGB8888 Config: " + ARGB8888.getConfig());

                bitmapInput = assetManager.open("RGB888.png");
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                RGB888 = BitmapFactory.decodeStream(bitmapInput, null, options);
                bitmapInput.close();
                Log.d("BitmapTest", "RGB888 Config: " + RGB888.getConfig());
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(0, 0, 0);

            destination.set(0, 0, ARGB8888.getWidth(), ARGB8888.getHeight());
            canvas.drawBitmap(ARGB8888, null, destination, null);

            canvas.drawBitmap(RGB888, 0f, ARGB8888.getHeight(), null);

            invalidate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(new RenderView(this));
    }
}
