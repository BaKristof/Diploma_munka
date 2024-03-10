package com.example.myapplication;

import android.opengl.Matrix;

import androidx.annotation.NonNull;

public class BGBlock extends Specifications {

    public BGBlock() {
        Matrix.setIdentityM(ownPositionM,0);
    }
    public void sizechnage(float valami){
        Matrix.scaleM(ownPositionM,0,valami,valami,0);
    }

    @NonNull
    @Override
    public String toString() {
        return "BGBlock{" +
                "position=" + MyGLRenderer.whereisyourmidle(this) +
                '}';
    }

    @Override
    public String getName() {
        return "BGBlock";
    }

}
