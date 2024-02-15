package com.example.myapplication;

import android.opengl.Matrix;

import java.util.ArrayList;

public class BoundingBox {
    private float xMin, xMax, yMin, yMax;

    public BoundingBox(GameObj obj) {
        this.xMin  = Float.MAX_VALUE;
        this.yMin =Float.MAX_VALUE;
        this.xMax = Float.MIN_VALUE;
        this.yMax = Float.MIN_VALUE;
        float[] realCoordinates = new float[4] ;
        float[] objMatrix = obj.getMatrix();
        float[] objSquareCords = obj.getSquareCoords();
        for (int i= 0 ;i<3; i++){
            realCoordinates[0] = objSquareCords[i];
            realCoordinates[1] = objSquareCords[i+1];
            realCoordinates[2] = objSquareCords[i+2];
            realCoordinates[3] = 0.0f;
            Matrix.multiplyMV(realCoordinates,0, objMatrix,0,objSquareCords,0);
            if(realCoordinates[0]>xMax) this.xMax=realCoordinates[0];
            if(realCoordinates[0]<xMin) this.xMin=realCoordinates[0];
            if(realCoordinates[1]>yMax) this.yMax=realCoordinates[1];
            if(realCoordinates[1]<yMin) this.yMin=realCoordinates[1];
        }

    }
    public BoundingBox(GameObj obj,float[] matrix) {
        this.xMin  = Float.MAX_VALUE;
        this.yMin =Float.MAX_VALUE;
        this.xMax = Float.MIN_VALUE;
        this.yMax = Float.MIN_VALUE;
        float[] realCoordinates = new float[4] ;
        float[] objMatrix = obj.getMatrix();
        float[] objSquareCords = obj.getSquareCoords();
        for (int i= 0 ;i<3; i++){
            realCoordinates[0] = objSquareCords[i];
            realCoordinates[1] = objSquareCords[i+1];
            realCoordinates[2] = objSquareCords[i+2];
            realCoordinates[3] = 0.0f;
            Matrix.multiplyMM(objMatrix, 0,objMatrix,0,matrix,0);
            Matrix.multiplyMV(realCoordinates,0, objMatrix,0,objSquareCords,0);
            if(realCoordinates[0]>xMax) this.xMax=realCoordinates[0];
            if(realCoordinates[0]<xMin) this.xMin=realCoordinates[0];
            if(realCoordinates[1]>yMax) this.yMax=realCoordinates[1];
            if(realCoordinates[1]<yMin) this.yMin=realCoordinates[1];
        }

    }

    public static BoundingBox[] valami(BGBlock[] blocks) {
        ArrayList<BoundingBox> playfield = new ArrayList<>();

        return playfield.toArray(new BoundingBox[0]);
    }

    public boolean intersects(BoundingBox other) {
        return !(xMax < other.xMin || xMin > other.xMax || yMax < other.yMin || yMin > other.yMax);
    }

    public static ArrayList<BoundingBox> ConnectBoundingBox(ArrayList<BoundingBox> boundingBoxes){

        //TODO this function to make smaler the boundingBoxes
        return boundingBoxes;
    }
}
