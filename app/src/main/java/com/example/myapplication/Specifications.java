package com.example.myapplication;

import android.opengl.Matrix;

public class Specifications {
    protected float[] ownPositionM = new float[16];
    protected Animation animation= new Animation();
    protected static final float size =0.5f;
    protected final float[] screenPositionM = new float[16];
    protected static float[] squareCoords = {
            -0.15f*size,  0.15f*size, 0.0f,   // left top
            -0.15f*size, -0.15f*size, 0.0f,   // left bottom
            0.15f*size, -0.15f*size, 0.0f,    // right bottom
            0.15f*size,  0.15f*size, 0.0f     // right top
    };
    public static final float blocksize = squareCoords[1]-squareCoords[7];
    protected static float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };
    public void setSingleTexture(int a) {
        animation.setSingletextur(a);
    }
    public int getAnimation() {
        return animation.NextFrame(0);
    }
    public void setAnimation(int[] backward,int[] left,int[] forward,int[] right) {
        animation = new Animation(backward,left,forward,right);
    }
    public float[] getOwnPositionM() {
        return ownPositionM;
    }

    public float[] getScreenPositionM() {
        Matrix.multiplyMM(screenPositionM,0, ownPositionM,0,Game.getMove(), 0);
        return screenPositionM;
    }

    public void setMatrix(float x, float y) {
        Matrix.translateM(this.ownPositionM,0,x,y,0);
    }
    public void setOwnPositionM(float[] ownPositionM){
        System.arraycopy(ownPositionM,0, this.ownPositionM,0, this.ownPositionM.length);
    }
    public static float[] getSquareCoords() {
        return squareCoords;
    }
    public String getName(){
        return "Specific";
    }


}
