package com.example.myapplication.Player;

import android.opengl.Matrix;

import com.example.myapplication.R;
import com.example.myapplication.SuperClasses.Character;
import com.example.myapplication.SuperClasses.Projectile;

public class Bullett extends Projectile {

    public Bullett(float angle, Character character) {
        super(angle,character);
        Matrix.rotateM(rotateM,0,-180,0,0,1); //negatyv érték a balra forgatás+
        setSpriteSheets(R.drawable.bulett_32x32,32,32);

        Matrix.rotateM(rotateM,0,rotateM,0,degree((float) Math.toDegrees(angle)*-1),0,0,1);
    }

    public Bullett(float dx, float dy) {
        super(dx, dy);
        setSpriteSheets(R.drawable.bulett_32x32,32,32);
    }

}

