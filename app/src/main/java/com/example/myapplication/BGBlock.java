package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class BGBlock {
    private int textureID;
    private int originalID;
    private final float[] matrix = new float[16];

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
    public void setvPMatrixHandle(int Prog) {

        int vPMatrixHandle = GLES20.glGetUniformLocation(Prog, "uMVPMatrix");
        float[] foo = new float[16];
        Matrix.multiplyMM(foo,0,matrix,0,MyGLRenderer.getProjectionMatrix(),0);
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, foo, 0);
    }
    public float[] getMatrix() {
        return matrix;
    }
    public int getTextureID() {
        return textureID;
    }
}
