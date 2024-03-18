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
    public float[] dxdy (Point other){
        float angle =(float) Math.atan2(other.y - this.y,(double) other.x - this.x);
        float dx = (float) Math.cos(angle);
        float dy = (float) Math.sin(angle);
        return new float[]{dx,dy};
    }
    public boolean near(Point other,float distance){
        return this.distance(other)<distance;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
