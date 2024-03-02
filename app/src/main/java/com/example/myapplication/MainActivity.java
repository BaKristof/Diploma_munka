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
public class MainActivity extends AppCompatActivity implements JoystickListener {

    private static Context context ;
    private MyGLSurfaceView myGLSurfaceView;
    private JoysticView leftJoystickView;
    private JoysticView rightJoystickView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        // Find references to views in the layout
        myGLSurfaceView = findViewById(R.id.gameView);
        leftJoystickView = findViewById(R.id.leftJoystickView);
        rightJoystickView = findViewById(R.id.rightJoystickView);

        // Set JoystickListeners
        leftJoystickView.setJoystickListener(this);
        rightJoystickView.setJoystickListener(this);
    }

    // Implement JoystickListener methods
    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int source) {
        // Handle joystick movement events here
        if (source == leftJoystickView.getId()) {
            // Left joystick moved
            // Example: gameView.movePlayer(xPercent, yPercent);
        } else if (source == rightJoystickView.getId()) {
            // Right joystick moved
            // Example: gameView.rotateCamera(xPercent, yPercent);
        }
    }
    public static Context getContext() {
        return MainActivity.context;
    }
}