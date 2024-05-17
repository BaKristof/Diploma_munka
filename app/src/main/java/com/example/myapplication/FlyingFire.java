package com.example.myapplication;

import android.opengl.Matrix;

public class FlyingFire extends EnemyCharacter {
    private float momentumX=0.0f;
    private float momentumY=0.0f;
    public FlyingFire(float[] startingmatrix) {
        super(startingmatrix);
        setSpriteSheets(R.drawable.no_1_enemy_spritesheet, 64, 64);
        Matrix.rotateM(rotateM,0,-45,0,0,1);

    }

    @Override
    public String getName() {
        return "Flying Fire";
    }

    @Override
    public void move() {
        float[] dxdy =this.dxdy(Game.getInstance().getPlayer());
        Matrix.rotateM(rotateM,0,0,0,0,0);
        momentumX+=dxdy[0]*0.002f;
        momentumY+=dxdy[0]*0.002f;
        Matrix.translateM(ownPositionM,0,momentumX,momentumY,0);
    }
}
