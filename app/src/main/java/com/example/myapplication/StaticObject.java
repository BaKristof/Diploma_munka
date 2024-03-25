package com.example.myapplication;

public class StaticObject extends Drawable{

    public StaticObject(int resourceID,int width,int height,float[] positionM) {
        setSpriteSheets(resourceID,width,height);
        this.ownPositionM = positionM;
    }

    public void hit (Specifications specifications){

    }
}
