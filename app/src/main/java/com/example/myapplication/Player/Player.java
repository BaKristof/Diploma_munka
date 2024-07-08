package com.example.myapplication.Player;

import android.opengl.Matrix;

import com.example.myapplication.Enemys.FlyingFire;
import com.example.myapplication.MainClasses.MyGLRenderer;
import com.example.myapplication.R;
import com.example.myapplication.SuperClasses.Character;
import com.example.myapplication.SuperClasses.Specifications;

import java.util.LinkedList;
import java.util.Queue;

public class Player extends Character {

    public float[] velocity;
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

    public float[] getVelocity() {
        return velocity;
    }

    public void setVelocity(float[] velocity) {
        this.velocity = velocity;
    }
    public float[] movemntprediction(FlyingFire enemy){
        float distance = this.distance(enemy);
        float[] dxdy = this.dxdy(enemy);
        float angle =(float) Math.atan2(velocity[1],velocity[0]);
        //todo ez itt nem lesz jó csak már fáradt vagyok mint atom

        return new float[]{0.0f,0.0f};
    }

}
