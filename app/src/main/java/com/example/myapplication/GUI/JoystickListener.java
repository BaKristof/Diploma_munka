package com.example.myapplication.GUI;

import android.view.MotionEvent;

public interface JoystickListener {

    void onJoystickMoved(float angle, Joystick joystick, MotionEvent event);

}
