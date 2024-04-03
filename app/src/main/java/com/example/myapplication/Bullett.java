package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Bullett extends Projectile{

    public Bullett(float angle,Character character) {
        super(angle,character);
        setSpriteSheets(R.drawable.bulett_32x32,32,32);
        Matrix.invertM(ownPositionM,0,Game.getMove().clone(),0);

    }

    public Bullett(float dx, float dy) {
        super(dx, dy);
        setSpriteSheets(R.drawable.bulett_32x32,32,32);
    }

    @Override
    public float[] getScreenPositionM() {
        return ownPositionM;
    }
}
