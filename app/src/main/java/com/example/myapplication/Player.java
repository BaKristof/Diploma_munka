package com.example.myapplication;

import android.opengl.GLES20;

import java.util.LinkedList;
import java.util.Queue;

public class Player extends  Character{

    Queue<Bullett> bulletts = new LinkedList<>();
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

    public void addBulletts(Bullett bullett) {
        bulletts.add(bullett);
    }
}
