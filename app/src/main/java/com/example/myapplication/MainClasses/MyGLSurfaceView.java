package com.example.myapplication.MainClasses;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.myapplication.MainClasses.MyGLRenderer;

public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context){
        super(context);

        setEGLContextClientVersion(2);
        renderer = new MyGLRenderer(this);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }
}