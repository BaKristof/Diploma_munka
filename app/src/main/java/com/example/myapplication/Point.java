package com.example.myapplication;

import android.util.Log;

public class Point{
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Point(Specifications specific) {
        Point a =  MyGLRenderer.whereisyourmidle(specific);
        this.x = a.x;
        this.y = a.y;
    }
    public float x;
    public float y;
    public float distance(Point other){
        return (float) Math.sqrt(Math.pow(other.x-x,2)+Math.pow(other.y-y,2));

    }
    public float[] pointToCordinates(){
        float top =y+Specifications.blocksize/2;
        float botom=y-Specifications.blocksize/2;
        float left=x-Specifications.blocksize/2;
        float right=x+Specifications.blocksize/2;
        return new float[]{top,botom,right,left};
    }
    public int getX() {
        return Math.round(x);
    }

    public int getY() {
        return Math.round(y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
