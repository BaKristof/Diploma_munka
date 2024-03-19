package com.example.myapplication;

public class BoundingCircle {
    private float x, y;
    private float radius;

    public BoundingCircle(Specifications specifications, float radius) {
        float[] local = MyGLRenderer.allCoordinates(specifications);
        this.x = local[0];
        this.y = local[1];
        this.radius = radius;
    }
}
