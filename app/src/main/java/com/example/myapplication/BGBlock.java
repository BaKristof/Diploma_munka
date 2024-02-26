package com.example.myapplication;

import android.opengl.Matrix;

import java.util.Arrays;

public class BGBlock extends Specifications {
    public BGBlock() {
        Matrix.setIdentityM(matrix,0);
    }
    public void sizechnage(float valami){
        Matrix.scaleM(matrix,0,valami,valami,0);
    }

    @Override
    public String toString() {
        return "BGBlock{" +
                "position=" + Arrays.toString(MyGLRenderer.whereisyourmidle(this)) +
                '}';
    }
}
