package com.example.myapplication;

public class BoundingCircle {
    protected float x, y;
    protected float radius;

    public BoundingCircle(Specifications specifications, float radius) {
        float[] local = MyGLRenderer.allCoordinates(specifications);
        this.x = local[0];
        this.y = local[1];
        this.radius = radius;
    }

    public BoundingCircle(Specifications specifications) {
        float[] local = MyGLRenderer.allCoordinates(specifications);
        this.x = local[0];
        this.y = local[1];
        this.radius = specifications.getHeight()/2;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public boolean intersects(BoundingCircle circle1) {
        float distanceX = Math.abs(circle1.getX() - this.x);
        float distanceY = Math.abs(circle1.getY() - this.y);

        float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        return distance <= circle1.getRadius() + this.getRadius();
    }
}
