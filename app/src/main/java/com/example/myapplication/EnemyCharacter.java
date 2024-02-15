package com.example.myapplication;

import android.opengl.Matrix;

public class EnemyCharacter extends Character{

    public EnemyCharacter(float[] startingmatrix) {
        super();
        Matrix.translateM(startingmatrix,0,GameObj.blocksize*2,GameObj.blocksize*-2,0);
        matrix = startingmatrix;
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
    public void MovetoCordinat(float x, float y){
        Matrix.translateM(matrix,0,x,y,0);

    }
    //TODO enemy characters
    // enemy movement by Pathfinding
    // attacks
    // Damage


}
