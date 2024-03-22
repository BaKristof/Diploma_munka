package com.example.myapplication;

import android.opengl.Matrix;

public class Specifications {
    protected float[] ownPositionM = new float[16];
    protected SpriteSheets spriteSheets;
    protected static final float size =0.25f;
    protected final float[] screenPositionM = new float[16];
    protected static float[] squareCoords = {
            -0.15f*size,  0.15f*size, 0.0f,   // left top
            -0.15f*size, -0.15f*size, 0.0f,   // left bottom
            0.15f*size, -0.15f*size, 0.0f,    // right bottom
            0.15f*size,  0.15f*size, 0.0f     // right top
    };
    public static final float blocksize = squareCoords[1]-squareCoords[7]; // Todo: átalakitani hogy dinamikus legyen az értéke mert a scaleel nem fog müködni ez
    protected static float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };
    public float getBlocksize(){
        float[] a =MyGLRenderer.allCoordinates(this);
        return a[1]-a[7];
    }

    public float distance(Specifications other2){
        float[] local= MyGLRenderer.midelCoordinate(this);
        float[] other = MyGLRenderer.midelCoordinate(other2);
        return (float) Math.sqrt(Math.pow(other[0]-local[0],2)+Math.pow(other[1]-local[1],2));

    }
    public float[] dxdy (Specifications other2){
        float[] local= MyGLRenderer.midelCoordinate(this);
        float[] other = MyGLRenderer.midelCoordinate(other2);
        float angle =(float) Math.atan2(other[1] - local[1],(double) other[0] - local[0]);
        float dx = (float) Math.cos(angle);
        float dy = (float) Math.sin(angle);
        return new float[]{dx,dy};
    }
    public boolean near(Specifications other,float distance){
        return this.distance(other)<distance;
    }

    public void setSpriteSheets(int resourceID,int width,int height) {
        this.spriteSheets = new SpriteSheets(resourceID,width,height);
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
