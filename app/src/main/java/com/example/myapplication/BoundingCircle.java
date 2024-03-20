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
    //TODO megkéne ezt csinálni aszem 2 fügvényt
    // collison with circle
    // collison with box
    // ja meg a radiusz automatikus beállitása
}
