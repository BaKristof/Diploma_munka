package com.example.myapplication;

public class Point{
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Point(GameObj gameObj) {
        float[] foo = MyGLRenderer.whereisyourmidle(gameObj);
        this.x = foo[0];
        this.y = foo[1];
    }

    public float x;
    public float y;
    public float distance(Point other){
        return (float) Math.sqrt(Math.pow(other.x-x,2)+Math.pow(other.y-y,2));
    }
}
