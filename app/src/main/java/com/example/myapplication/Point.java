package com.example.myapplication;

public class Point{
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Point(Specifications specific) {
        float[] foo = MyGLRenderer.whereisyourmidle(specific);
        this.x = foo[0];
        this.y = foo[1];
    }
    public float x;
    public float y;
    public float distance(Point other){
        return (float) Math.sqrt(Math.pow(other.x-x,2)+Math.pow(other.y-y,2));
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
