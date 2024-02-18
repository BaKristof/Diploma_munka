package com.example.myapplication;

import android.opengl.Matrix;

public class BoundingBox {
    private float xMin= Float.MAX_VALUE, xMax = Float.MIN_VALUE, yMin =Float.MAX_VALUE, yMax= Float.MIN_VALUE;
    public BoundingBox(GameObj obj) {
        float[] objMatrix;
        if(obj instanceof Player) objMatrix = Game.getInstance().getPlayerMatrix();
        else objMatrix = obj.getMatrix();
        float[] realCoordinates =new float[ obj.getSquareCoords().length];
        float[] objSquareCords = obj.getSquareCoords();
        Matrix.multiplyMV(realCoordinates,0, objMatrix,0,objSquareCords,0);

        for (int i = 0; i < realCoordinates.length; i+=3) {
            if(realCoordinates[i]>xMax) this.xMax=realCoordinates[i];
            if(realCoordinates[i]<xMin) this.xMin=realCoordinates[i];
            if(realCoordinates[i+1]>yMax) this.yMax=realCoordinates[i+1];
            if(realCoordinates[i+1]<yMin) this.yMin=realCoordinates[i+1];
        }
    }
    public boolean intersects(BoundingBox other) {
        return !(xMax < other.xMin || xMin > other.xMax || yMax < other.yMin || yMin > other.yMax);
    }

    public float[] getMin() {
        return new float[]{xMin,yMin};
    }
    public float[] getMax() {
        return new float[]{xMax,yMax};
    }
    public boolean Lineintersect( Point start, Point end) {
        float[] lineStart = new float[]{start.x, start.y};
        float[] lineEnd = new float[]{end.x, end.y};

        float[] boxMin = this.getMin();
        float[] boxMax = this.getMax();

        float[] rayDirection = {lineEnd[0] - lineStart[0], lineEnd[1] - lineStart[1]};
        float[] rayOrigin = {lineStart[0], lineStart[1]};

        float tMin = Float.NEGATIVE_INFINITY;
        float tMax = Float.POSITIVE_INFINITY;

        for (int i = 0; i < 2; i++) {
            float invDir = 1.0f / rayDirection[i];
            float tNear = (boxMin[i] - rayOrigin[i]) * invDir;
            float tFar = (boxMax[i] - rayOrigin[i]) * invDir;

            if (tNear > tFar) {
                float temp = tNear;
                tNear = tFar;
                tFar = temp;
            }

            tMin = Math.max(tNear, tMin);
            tMax = Math.min(tFar, tMax);

            if (tMin > tMax) {
                return false;
            }
        }
        return tMin <= 1.0f && tMax >= 0.0f;
    }
}
