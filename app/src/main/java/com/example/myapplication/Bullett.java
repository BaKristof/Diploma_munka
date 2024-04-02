package com.example.myapplication;

import android.opengl.GLES20;

public class Bullett extends Projectile{
    public Bullett(float angle) {
        super(angle);
        setSpriteSheets(R.drawable.bulett_32x32,32,32);
    }

    public Bullett(float dx, float dy) {
        super(dx, dy);
        setSpriteSheets(R.drawable.bulett_32x32,32,32);
    }


}
