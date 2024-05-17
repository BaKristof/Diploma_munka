package com.example.myapplication;

import android.opengl.Matrix;

public class FlyingFire extends EnemyCharacter {
    public float momentumX=0;
    public float momentumY=0;
    public FlyingFire(float[] startingmatrix) {
        super(startingmatrix);
        setSpriteSheets(R.drawable.no_1_enemy_spritesheet, 64, 64);
    }
    @Override
    public void move() {
        float[] dxdy =this.dxdy(Game.getInstance().getPlayer());
        momentumX+=dxdy[0]*0.0004f;
        momentumY+=dxdy[1]*0.0004f;
        Matrix.translateM(ownPositionM,0,momentumX,momentumY,0);
    }
    @Override
    public String getName() {
        return "Flying Fire";
    }
}
