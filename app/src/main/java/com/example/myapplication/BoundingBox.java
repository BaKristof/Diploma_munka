package com.example.myapplication;

import android.opengl.Matrix;

import java.util.ArrayList;

public class BoundingBox {
    private float xMin= Float.MAX_VALUE, xMax= Float.MIN_VALUE, yMin= Float.MAX_VALUE, yMax= Float.MIN_VALUE;
    private final float[] realCoordinates = new float[4] ;
    public BoundingBox(GameObj obj) {

        float[] objMatrix = obj.getMatrix();
        float[] objSquareCords = obj.getSquareCoords();
        for (int i= 0 ;i<3; i++){
            Matrix.multiplyMV(realCoordinates,0, objMatrix,0,objSquareCords,0);
            if(realCoordinates[0]>xMax) this.xMax=realCoordinates[0];
            if(realCoordinates[0]<xMin) this.xMin=realCoordinates[0];
            if(realCoordinates[1]>yMax) this.yMax=realCoordinates[1];
            if(realCoordinates[1]<yMin) this.yMin=realCoordinates[1];
        }
    }
    public BoundingBox(GameObj obj,float[] matrix) {
        float[] objMatrix = obj.getMatrix();
        float[] objSquareCords = obj.getSquareCoords();
        for (int i= 0 ;i<3; i++){
            Matrix.multiplyMM(objMatrix, 0,objMatrix,0,matrix,0);
            Matrix.multiplyMV(realCoordinates,0, objMatrix,0,objSquareCords,0);
            if(realCoordinates[0]>xMax) this.xMax=realCoordinates[0];
            if(realCoordinates[0]<xMin) this.xMin=realCoordinates[0];
            if(realCoordinates[1]>yMax) this.yMax=realCoordinates[1];
            if(realCoordinates[1]<yMin) this.yMin=realCoordinates[1];
        }
    }
    public BoundingBox(BGBlock block) {
        float[] objMatrix = block.getMatrix();
        float[] objSquareCords = GameObj.squareCoords;
        for (int i= 0 ;i<3; i++){
            Matrix.multiplyMM(objMatrix, 0,objMatrix,0,Game.getInstance().getMatrix(), 0);
            Matrix.multiplyMV(realCoordinates,0, objMatrix,0,objSquareCords,0);
            if(realCoordinates[0]>xMax) this.xMax=realCoordinates[0];
            if(realCoordinates[0]<xMin) this.xMin=realCoordinates[0];
            if(realCoordinates[1]>yMax) this.yMax=realCoordinates[1];
            if(realCoordinates[1]<yMin) this.yMin=realCoordinates[1];
        }
    }
    public static BoundingBox[] BGBlocktoBoundingBox(BGBlock[] blocks) {
        ArrayList<BoundingBox> playfield = new ArrayList<>();
        for (BGBlock bb : blocks) {
            switch (bb.getOriginalID()){
                case 10:
                case 11:
                    playfield.add(new BoundingBox(new GameObj(),Game.getInstance().getMatrix()));
                    break;
            }
        }
        return playfield.toArray(new BoundingBox[0]);
    }
    public void movebounds(){
    }
    public boolean intersects(BoundingBox other) {
        return !(xMax < other.xMin || xMin > other.xMax || yMax < other.yMin || yMin > other.yMax);
    }
    public static ArrayList<BoundingBox> ConnectBoundingBox(ArrayList<BoundingBox> boundingBoxes){

        //TODO this function to make smaler the boundingBoxes
        return boundingBoxes;
    }
}
