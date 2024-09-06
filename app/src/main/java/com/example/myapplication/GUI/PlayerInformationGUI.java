package com.example.myapplication.GUI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.myapplication.BackGround.Key;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.MainClasses.MyGLSurfaceView;
import com.example.myapplication.SuperClasses.Drawable;

public class PlayerInformationGUI {
    private static int keycount= 1;
    public static void setKeycount() {
        keycount--;
    }
    private static Joystick right;
    private static Joystick left;
    private static MyGLSurfaceView myGLSurfaceView;

    private static ProgressBar progressBar;
    private static int progress = 40;

    public static FrameLayout playerGUI(Context context){

        myGLSurfaceView = new MyGLSurfaceView(context);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.addView(myGLSurfaceView);
        // Create the ProgressBar programmatically
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
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



        return frameLayout;

    }

    public static FrameLayout StartMenu(Context context){
        return new FrameLayout(context);
    }

    public static float dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp * (displayMetrics.densityDpi / 160f);
    }
}
