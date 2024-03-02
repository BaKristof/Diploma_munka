package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;


public class JoysticView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private float displace;
    private double angle;
    private JoystickListener joystickListener;

    float centerX=(float) getWidth() / 2;
    float centerY=(float) getHeight() / 2;
    float baseRadius=(float) Math.min(getWidth(), getHeight()) / 3;
    float hatRadius =(float) Math.min(getWidth(), getHeight()) / 5;
    private JoystickListener joysticlistenerCallback;
    public JoysticView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joysticlistenerCallback=(JoystickListener) context;
    }

    public JoysticView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joysticlistenerCallback=(JoystickListener) context;

    }

    public JoysticView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
            joysticlistenerCallback=(JoystickListener) context;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
    public void setJoystickListener(JoystickListener listener) {
        this.joystickListener = listener;
    }
    private void drawJoystick(float newX,float newY){
        if(getHolder().getSurface().isValid()){
            Canvas mycanvas = this.getHolder().lockCanvas();
            Paint color = new Paint();
            mycanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            color.setARGB(50,50,50,50);
            mycanvas.drawCircle(centerX,centerY,baseRadius,color);
            color.setARGB(255,0,0,255);
            mycanvas.drawCircle(newX,newY,hatRadius,color);
            getHolder().unlockCanvasAndPost(mycanvas);
        }


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.equals(this)){

            if(event.getAction() != MotionEvent.ACTION_UP ) {
                drawJoystick(event.getX(), event.getY());
                displace = (float) Math.sqrt(Math.pow(event.getX()-centerX,2)+Math.pow(event.getY()-centerY,2));
                if(displace<baseRadius){
                    drawJoystick(event.getX(), event.getY());
                    joysticlistenerCallback.onJoystickMoved((event.getX()-centerX)/baseRadius,(event.getY()-centerY)/baseRadius,getId());
                    angle =Math.atan2((double) event.getX() - centerX,(double) event.getY() - centerY);
                }
                else {
                    float ratio =baseRadius/displace;
                    float constrainX =centerX+(event.getX()-centerX)*ratio;
                    float constrainY =centerY+(event.getY()-centerY)*ratio;
                    angle =Math.atan2((double) constrainX - centerX,(double) constrainY - centerY);
                    drawJoystick(constrainX,constrainY);

                    joysticlistenerCallback.onJoystickMoved((constrainX-centerX)/baseRadius,(constrainY-centerY)/baseRadius,getId());

                }
            }
        }
            else {
            drawJoystick(centerX, centerY);
            joysticlistenerCallback.onJoystickMoved(0,0,getId());
        }
        return true;
    }
}
