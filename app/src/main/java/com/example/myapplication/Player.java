package com.example.myapplication;

import android.opengl.GLES20;

public class Player extends  Character{

    public Player() {
        super();
        setSpriteSheets(R.drawable.spritesheet_main_charater,64,64);
    }

    @Override
    public String getName(){return "Playes";}

    @Override
    public float[] getScreenPositionM() {
        return ownPositionM;
    }
}
