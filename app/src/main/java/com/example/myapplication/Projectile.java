package com.example.myapplication;

import android.opengl.Matrix;

public class Projectile extends Drawable {
    //Todo meg kéne ezt csinálni
    float projectileSpeed;
    float dx;
    float dy;
    public Projectile(float dx,float dy) {
        setSpriteSheets(R.drawable.bulett_32x32,32,32);
        this.dx =dx;
        this.dy =dy;
    }
    public void hit (){

    }
    public void move (float constantMoveNumber){
        Matrix.translateM(ownPositionM, 0, dx*projectileSpeed*constantMoveNumber, dy * projectileSpeed*constantMoveNumber, 0);
    }
}

