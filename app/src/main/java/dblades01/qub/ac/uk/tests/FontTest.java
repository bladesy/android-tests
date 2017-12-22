package dblades01.qub.ac.uk.tests;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class FontTest extends AppCompatActivity {
    private class RenderView extends View {
        private Typeface font;
        private String testText;
        private Paint paint = new Paint();
        private Rect textBounds = new Rect(), destination = new Rect();
        private Bitmap bitmap;
        private Canvas bitmapCanvas;

        public RenderView(Context context) {
            super(context);

            font = Typeface.createFromAsset(context.getAssets(), "font.ttf");

            testText = "test text";
            paint.setTypeface(font);
            paint.setColor(Color.WHITE);
            paint.setTextSize(112);
            paint.getTextBounds(testText, 0, testText.length(), textBounds);

            bitmap = Bitmap.createBitmap(textBounds.width(), textBounds.height(), Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmap);
            bitmapCanvas.drawText(testText, 0f, textBounds.height(), paint);
            destination.set(0, textBounds.height(), textBounds.width(), textBounds.height() * 2);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(0, 0, 0);

            canvas.drawText(testText, 0f, textBounds.height(), paint);
            canvas.drawBitmap(bitmap, null, destination, null);

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
