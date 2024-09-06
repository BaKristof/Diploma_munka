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
package com.example.myapplication.MainClasses;

        import androidx.appcompat.app.AppCompatActivity;

        import android.graphics.Color;
        import android.graphics.drawable.ClipDrawable;
        import android.graphics.drawable.GradientDrawable;
        import android.graphics.drawable.LayerDrawable;
        import android.os.Bundle;
        import android.content.Context;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.util.TypedValue;
        import android.view.Gravity;
        import android.view.MotionEvent;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.ProgressBar;

        import com.example.myapplication.GUI.Joystick;
        import com.example.myapplication.GUI.JoystickListener;
        import com.example.myapplication.GUI.PlayerInformationGUI;
        import com.example.myapplication.Player.PlayerActions;
        import com.example.myapplication.Player.PlayerListener;
        import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity implements JoystickListener,GUIListener {

    private static Context context ;
    private MyGLSurfaceView myGLSurfaceView;
    private static Joystick right;
    private static Joystick left;
    private static ProgressBar progressBar;
    private static int progress = 100;

    private static  ImageView image ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myGLSurfaceView = new MyGLSurfaceView(this);
        image = new ImageView(this);
        image.setImageResource(R.drawable.place_holder);
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

        sizeInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics());

        FrameLayout.LayoutParams imageViewParam = new FrameLayout.LayoutParams(sizeInDp, sizeInDp, Gravity.CENTER_HORIZONTAL | Gravity.END);
        frameLayout.addView(image,imageViewParam);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        FrameLayout.LayoutParams healthBarParams = new FrameLayout.LayoutParams(150,50 ,Gravity.TOP |Gravity.LEFT);
        // Set maximum progress and initial progress

        progressBar.setMax(100);
        progressBar.setProgress(progress);

        // Create a background drawable for the progress bar
        GradientDrawable backgroundDrawable = new GradientDrawable();
        backgroundDrawable.setColor(Color.LTGRAY); // Set the background color
        backgroundDrawable.setCornerRadius(20); // Set rounded corners

        // Create a progress drawable with a gradient
        GradientDrawable progressDrawable = new GradientDrawable();
        progressDrawable.setColor(Color.RED); // Progress color
        progressDrawable.setCornerRadius(20); // Rounded corners

        // Wrap the progress drawable in a ClipDrawable to control the width
        ClipDrawable clipDrawable = new ClipDrawable(progressDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);

        // Create a LayerDrawable to combine the background and progress drawable
        LayerDrawable layerDrawable = new LayerDrawable(new android.graphics.drawable.Drawable[]{backgroundDrawable, clipDrawable});
        layerDrawable.setId(0, android.R.id.background); // ID for background
        layerDrawable.setId(1, android.R.id.progress); // ID for progress

        // Set the custom drawable to the ProgressBar
        progressBar.setProgressDrawable(layerDrawable);
        frameLayout.addView(progressBar,healthBarParams);

        setContentView(frameLayout);
        MainActivity.context = getApplicationContext();
        Game.setGuiListener(this);

    }
    @Override
    public void onJoystickMoved(float angle, Joystick joystick, MotionEvent event) {
        if (joystick == right && event.getAction() == MotionEvent.ACTION_UP){
            Game.getInstance().addBullet(angle,Game.getInstance().getPlayer());
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


    @Override
    public void setHealthBar(int health) {
        progressBar.setProgress(health);
    }
}