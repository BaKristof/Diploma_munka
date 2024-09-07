package com.example.myapplication.SuperClasses;

import android.opengl.Matrix;

import com.example.myapplication.HitBoxes.BoundingBox;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.MainClasses.MyGLRenderer;
import com.example.myapplication.MainClasses.SpriteSheets;

import java.util.Arrays;
import java.util.Objects;

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
    protected static float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };
    public float getHeight(){
        float[] a = MyGLRenderer.allCoordinates(this);
        return (float)Math.abs( a[1]-a[7]);
    }
    public float distance(Specifications other2){
        float[] local= MyGLRenderer.midleCoordinate(this);
        float[] other = MyGLRenderer.midleCoordinate(other2);
        return (float) Math.sqrt(Math.pow(other[0]-local[0],2)+Math.pow(other[1]-local[1],2));

    }
    public float[] dxdy (Specifications other2){
        float[] local= MyGLRenderer.midleCoordinate(this);
        float[] other = MyGLRenderer.midleCoordinate(other2);
        float angle =(float) Math.atan2(other[1] - local[1],(double) other[0] - local[0]);
        float dx = (float) Math.cos(angle);
        float dy = (float) Math.sin(angle);
        return new float[]{dx,dy};
    }
    public boolean near(Specifications other,float distance){
        return this.distance(other)<distance;
    }

    public void setSpriteSheets(int resourceID,int width,int height) {
        this.spriteSheets = new SpriteSheets(resourceID,width,height,4);
    }
    public void setSpriteSheets(SpriteSheets spriteSheets){
        this.spriteSheets = spriteSheets;
    }
    public float[] getOwnPositionM() {
        return ownPositionM;
    }

    public void setMatrix(float x, float y) {
        Matrix.translateM(this.ownPositionM,0,x,y,0);
    }
    public Specifications setOwnPositionM(float[] ownPositionM){
        System.arraycopy(ownPositionM,0, this.ownPositionM,0, this.ownPositionM.length);
        return this;
    }
    public static float[] getSquareCoords() {
        return squareCoords;
    }
    public String getName(){
        return "Specific";
    }
    public static float degree(float degree){
        if(degree>0) return degree;
        else return (float) 180+(180-Math.abs(degree));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Specifications cordinat : {");
        sb.append(Arrays.toString(ownPositionM));
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specifications that = (Specifications) o;
        return Arrays.equals(ownPositionM, that.ownPositionM);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(ownPositionM);
    }
}
