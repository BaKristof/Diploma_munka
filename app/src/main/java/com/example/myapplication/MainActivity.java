/*ackage com.example.myapplication;

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
}*/
package com.example.myapplication;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.content.Context;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.util.TypedValue;
        import android.view.Gravity;
        import android.view.MotionEvent;
        import android.widget.FrameLayout;

        import java.lang.reflect.Field;
        import java.time.Instant;
        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements JoystickListener {

    private static Context context ;
    private MyGLSurfaceView myGLSurfaceView;
    private static Joystick right;
    private static Joystick left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myGLSurfaceView = new MyGLSurfaceView(this);

        right = new Joystick(this);
        right.setCenter(dpToPx(this,75), dpToPx(this,75));
        right.setBaseRadius(dpToPx(this,150));
        right.setJoystickListener(this);

        left = new Joystick(this);
        left.setCenter(dpToPx(this,75), dpToPx(this,75));
        left.setBaseRadius(dpToPx(this,150));
        left.setJoystickListener(this);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(myGLSurfaceView);


        int sizeInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());

        FrameLayout.LayoutParams rightjoystickParams = new FrameLayout.LayoutParams(sizeInDp, sizeInDp, Gravity.BOTTOM | Gravity.END);
        frameLayout.addView(right, rightjoystickParams);

        FrameLayout.LayoutParams leftjoystickParams = new FrameLayout.LayoutParams(sizeInDp, sizeInDp, Gravity.BOTTOM | Gravity.START);
        frameLayout.addView(left, leftjoystickParams);

        setContentView(frameLayout);
        MainActivity.context = getApplicationContext();

    }
    @Override
    public void onJoystickMoved(float angle, Joystick joystick, MotionEvent event) {
        if (joystick == right && event.getAction() == MotionEvent.ACTION_UP){
            Game.addCount();
            Log.e("valami"," lefut");
        }
    }
    public static Context getContext() {
        return MainActivity.context;
    }
    public static float dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp * (displayMetrics.densityDpi / 160f);
    }

    public static Joystick getRight() {
        return right;
    }

    public static Joystick getLeft() {
        return left;
    }

}