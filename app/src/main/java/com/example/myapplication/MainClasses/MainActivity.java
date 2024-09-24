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

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GUI.Joystick;
import com.example.myapplication.GUI.JoystickListener;
import com.example.myapplication.Objects.Exit;
import com.example.myapplication.R;

import java.util.Optional;

public class MainActivity extends AppCompatActivity implements JoystickListener, GUIListener {

    private static Context context;
    private MyGLSurfaceView myGLSurfaceView;
    private static Joystick right;
    private static Joystick left;
    private static ProgressBar progressBar;
    private static int progress = 100;
    private static SpriteSheets revolverBaySpriteSheet;
    private ImageView imageViewRevolverBay;

    //private static  ImageView image ;
    public static ImageView[] emptyKeyImages = new ImageView[3];

    private int keyCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myGLSurfaceView = new MyGLSurfaceView(this);

       /* image = new ImageView(this);
        image.setImageResource(R.drawable.place_holder);*/
        revolverBaySpriteSheet = new SpriteSheets(R.drawable.revolver_bay, 64, 64, 1);
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

        /*FrameLayout.LayoutParams imageViewParam = new FrameLayout.LayoutParams(sizeInDp, sizeInDp, Gravity.CENTER_HORIZONTAL | Gravity.END);
        frameLayout.addView(image,imageViewParam);*/

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
        frameLayout.addView(progressBar, healthBarParams);


        LinearLayout imageLayout = new LinearLayout(this);
        imageLayout.setOrientation(LinearLayout.HORIZONTAL);
        imageLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

      /*  LinearLayout keyAndRevolverBayLayout = new LinearLayout(this);
        imageLayout.setOrientation(LinearLayout.VERTICAL);
        imageLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        */
        // Replace with actual drawable resource
        ImageView imageView1 = new ImageView(this);
        imageView1.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // Width and height of each image
        imageView1.setBackgroundColor(Color.parseColor("#FFFFFF")); // Placeholder color (replace with your image)
        imageView1.setImageResource(R.drawable.emptykey);
        ImageView imageView2 = new ImageView(this);
        imageView2.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // Width and height of each image
        imageView2.setBackgroundColor(Color.parseColor("#FFFFFF")); // Placeholder color (replace with your image)
        imageView2.setImageResource(R.drawable.emptykey); // Replace with actual drawable resource
        ImageView imageView3 = new ImageView(this);
        imageView3.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // Width and height of each image
        imageView3.setBackgroundColor(Color.parseColor("#FFFFFF")); // Placeholder color (replace with your image)
        imageView3.setImageResource(R.drawable.emptykey); // Replace with actual drawable resource
        emptyKeyImages[0]= imageView1;
        emptyKeyImages[1]= imageView2;
        emptyKeyImages[2]= imageView3;
        imageLayout.addView(imageView1);
        imageLayout.addView(imageView2);
        imageLayout.addView(imageView3);


        FrameLayout.LayoutParams keyViewParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.TOP);
        keyViewParam.setMargins(20, 20, 20, 20);

        frameLayout.addView(imageLayout, keyViewParam);


        imageViewRevolverBay = new ImageView(this);
        imageViewRevolverBay.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // Width and height of each image
        imageViewRevolverBay.setBackgroundColor(Color.parseColor("#FFFFFF")); // Placeholder color (replace with your image)
        imageViewRevolverBay.setImageBitmap(revolverBaySpriteSheet.nextBitmapFrame());

        FrameLayout.LayoutParams revolverViewParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER);
        frameLayout.addView(imageViewRevolverBay, revolverViewParam);


        setContentView(frameLayout);

        MainActivity.context = getApplicationContext();
        Game.setGuiListener(this);

    }
    @Override
    public void onJoystickMoved(float angle, Joystick joystick, MotionEvent event) {
        if (joystick == right && event.getAction() == MotionEvent.ACTION_UP){
            Game.getInstance().addBullet(angle,Game.getInstance().getPlayer());
            //  Log.e("valami"," lefut");
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
    @Override
    public void keyCollect() {
        emptyKeyImages[keyCount++].setImageResource(R.drawable.key);
        if(keyCount==emptyKeyImages.length) {
            Optional<Exit> exit = Game.getInstance().getStaticObjects().stream().filter(i -> i instanceof Exit).map(i -> (Exit) i).findFirst();
            if (exit.isPresent()) {
                exit.get().setIsunlocked();
                exit.get().setSpriteSheets(new SpriteSheets(R.drawable.enemy_place_holder,64,64,4));
                keyCount=0;

            }
        }
    }

    @Override
    public void resetGUI() {
        for (ImageView im : emptyKeyImages) {
            im.setImageResource(R.drawable.emptykey);
        }
    }

    @Override
    public void shoot() {
        imageViewRevolverBay.setImageBitmap(revolverBaySpriteSheet.nextBitmapFrame());
    }
}