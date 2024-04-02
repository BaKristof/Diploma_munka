package com.example.myapplication;

import android.view.MotionEvent;

public interface JoystickListener {

    void onJoystickMoved(float angle, Joystick joystick, MotionEvent event);

}
