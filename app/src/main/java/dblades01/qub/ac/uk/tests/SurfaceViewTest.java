package dblades01.qub.ac.uk.tests;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class SurfaceViewTest extends AppCompatActivity {
    private class FastRenderView extends SurfaceView implements Runnable {
        private volatile boolean running;
        private Thread renderThread;
        private SurfaceHolder surfaceHolder;

        public FastRenderView(Context context) {
            super(context);

            running = false;
            surfaceHolder = getHolder();
        }

        @Override
        public void run() {
            while(running) {
                if(!surfaceHolder.getSurface().isValid())
                    continue;

                Canvas surfaceCanvas = surfaceHolder.lockCanvas();
                surfaceCanvas.drawRGB(255, 0, 0);
                surfaceHolder.unlockCanvasAndPost(surfaceCanvas);
            }
        }

        public void resume() {
            running = true;
            renderThread = new Thread(this);
            renderThread.start();
        }

        public void pause() {
            running = false;

            while(true) {
                try {
                    renderThread.join();
                    return;
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private FastRenderView fastRenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        fastRenderView = new FastRenderView(this);
        setContentView(fastRenderView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fastRenderView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fastRenderView.pause();
    }
}
