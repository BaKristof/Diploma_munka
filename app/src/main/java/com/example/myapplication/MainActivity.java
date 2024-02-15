package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;


public class MainActivity extends AppCompatActivity {
    private GLSurfaceView gLView;
    private static Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gLView = new MyGLSurfaceView(this);
        setContentView(gLView);
        MainActivity.context = getApplicationContext();
    }
    public static Context getContext() {
        return MainActivity.context;
    }
}


class MyGLSurfaceView extends GLSurfaceView {
    private float previousX;
    private float previousY;
    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
     }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int irany=0;
        float currentX = 550.0f;
        float currentY = 1100.0f;
        //float currentX = event.getX();
        //float currentY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("valami","courrenx:  "+ event.getX()+"    and y :  "+ event.getY());
                break;
            case MotionEvent.ACTION_MOVE:

                double foo =Math.atan2((double) event.getY() - currentY,(double) event.getX() - currentX);
                float dx = (float) Math.cos(foo);
                float dy = (float) Math.sin(foo*-1);
                //0 =fel
                //1=le
                //2=jobb
                //3=bal
                //            android:screenOrientation="landscape"
                Game.getInstance().move(dx, dy);
                Game.getInstance().setPlayerirany(dx,dy);
                requestRender();
                break;
        }
        return true;
    }

}
