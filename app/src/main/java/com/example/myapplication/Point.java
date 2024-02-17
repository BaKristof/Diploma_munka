package com.example.myapplication;

public class Point{
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Point(GameObj gameObj) {
        float[] realpoints = MyGLRenderer.whereareyou(gameObj);
        this.x = realpoints[0]+(GameObj.blocksize/2);
        this.y = realpoints[1]-(GameObj.blocksize/2);
    }

    public float x;
    public float y;
    public float distance(Point other){
        return (float) Math.sqrt(Math.pow(other.x-x,2)+Math.pow(other.y-y,2));
    }
}
