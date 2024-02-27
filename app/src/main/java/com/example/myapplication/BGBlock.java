package com.example.myapplication;

import android.opengl.Matrix;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class BGBlock extends Specifications {
    public BGBlock() {
        Matrix.setIdentityM(matrix,0);
    }
    public void sizechnage(float valami){
        Matrix.scaleM(matrix,0,valami,valami,0);
    }

    @NonNull
    @Override
    public String toString() {
        return "BGBlock{" +
                "position=" + MyGLRenderer.whereisyourmidle(this) +
                '}';
    }
}
