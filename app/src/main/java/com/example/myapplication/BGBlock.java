package com.example.myapplication;

import android.opengl.Matrix;

public class BGBlock extends Specifications {
    public BGBlock() {
        Matrix.setIdentityM(matrix,0);
    }
    public void sizechnage(float valami){
        Matrix.scaleM(matrix,0,valami,valami,0);
    }
}
