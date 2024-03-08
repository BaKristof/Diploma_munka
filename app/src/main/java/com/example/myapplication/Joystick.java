
package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;


public class Joystick extends View {

    private static final int JOYSTICK_RADIUS = 150;
    private static final int JOYSTICK_HAT_RADIUS = 75;
    private static final int JOYSTICK_BASE_COLOR = Color.GRAY;
    private float baseRadius;
    private JoystickListener joystickListener;
    private float newX = 0.0f;
    private float newY = 0.0f;
    private Paint basePaint;
    private Paint hatPaint;
    private float centerX;
    private float centerY;
    private float displace;
    private double angle;

    public Joystick(Context context) {
        super(context);
        init();
    }

    public Joystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        basePaint = new Paint();
        basePaint.setColor(JOYSTICK_BASE_COLOR);
        basePaint.setAlpha(50);

        hatPaint = new Paint();
        hatPaint.setColor(JOYSTICK_BASE_COLOR);
        hatPaint.setAlpha(75);
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawCircle(centerX, centerY, JOYSTICK_RADIUS, basePaint);
        canvas.drawCircle(newX, newY, JOYSTICK_HAT_RADIUS, hatPaint);
    }

    public void setCenter(float newX, float newY) {
        centerX = newX;
        centerY = newY;
        this.newY= newY;
        this.newX = newX;
        invalidate();
    }
    public void setJoystickListener(JoystickListener listener) {
        this.joystickListener = listener;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP) {
            this.newX = event.getX();
            this.newY = event.getY();

            this.displace = (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
            //Log.e("valami","valami    "+displace+"  base "+ baseRadius+"  new  "+newX+"   newY  "+ newY+" fooo   "+foo);
            this.angle =Math.atan2((double) event.getY() - centerY,(double) event.getX() - centerX);

            if (displace > baseRadius/2) {
                this.newX = centerX + ((baseRadius/2)*(float) Math.cos(angle));
                this.newY = centerY + ((baseRadius/2)*(float) Math.sin(angle));

            }
            invalidate();
            joystickListener.onJoystickMoved((newX - centerX) / baseRadius, (newY - centerY) / baseRadius);

        } else {

            this.newX = centerX;
            this.newY = centerY;
            invalidate();
            joystickListener.onJoystickMoved(0, 0);
        }
        return true;
    }

    public void setBaseRadius(float baseRadius) {
        this.baseRadius = baseRadius;
    }

    public double getAngle() {
        return angle;
    }

    public float getDisplace() {
        return displace;
    }
}
