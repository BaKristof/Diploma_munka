package com.example.myapplication;

import android.opengl.Matrix;

public class BGBlock extends GameObj {
    private int textureID;
    private int originalID;

    public BGBlock() {
        Matrix.setIdentityM(matrix,0);
    }
    public void setTextureID(int textureID, int originalID) {
        this.textureID = textureID;
        this.originalID = originalID;
    }
    public int getOriginalID() {
        return originalID;
    }
    public void setMatrix(float x, float y, float z) {
        Matrix.translateM(matrix,0,x,y,z);
    }
    public float[] getMatrix() {
        return matrix;
    }
    public int getTextureID() {
        return textureID;
    }
}
