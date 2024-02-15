package com.example.myapplication;

import android.opengl.Matrix;

public class EnemyCharacter extends Character{

    public EnemyCharacter(float[] point) {
        super();
        Matrix.setIdentityM(matrix,0);
        Matrix.translateM(matrix,0,GameObj.blocksize*2,GameObj.blocksize*2,0);
        setMatrix(matrix);

    }

    @Override
    public void draw(float[]mvpMatrix) {
        float[] localmatrix = new float[16];
        Matrix.multiplyMM(localmatrix,0,matrix,0,mvpMatrix,0);
        super.draw(localmatrix);
    }

    public void move(float dx,float dy){
        irany = Game.whatisirany(dx,dy);
        Matrix.translateM(matrix,0,dx*0.004f,dy*0.004f,0);
    }
    //TODO enemy characters
    // enemy movement by Pathfinding
    // attacks
    // Damage


}
