package com.example.myapplication;

import android.opengl.Matrix;

public class BGBlock {
    protected int textureID;
    private float PositionX =0;
    private float PositionY =0;
    private final float[] matrix = new float[16];

    public BGBlock() {
        Matrix.setIdentityM(matrix,0);
    }
    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public void setMatrix(float x, float y,float z) {
        PositionX+=x;
        PositionY+=y;
        Matrix.translateM(this.matrix,0,PositionX,PositionY,z);
    }

    public int getTextureID() {
        return textureID;
    }
}
