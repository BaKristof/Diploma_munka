package com.example.myapplication;

import android.opengl.Matrix;

public class BoundingBox {
    private float[] MaxMin = new float[]{Float.MIN_VALUE,Float.MIN_VALUE,0,Float.MAX_VALUE,Float.MAX_VALUE,0};

    public BoundingBox() {
        float[] realCoordinates = Specifications.getSquareCoords();

        for (int i = 0; i < realCoordinates.length; i+=3) {
            if(realCoordinates[i]>MaxMin[0]) this.MaxMin[0]=realCoordinates[i];      //xMax
            if(realCoordinates[i]<MaxMin[3]) this.MaxMin[3]=realCoordinates[i];      //xMin
            if(realCoordinates[i+1]>MaxMin[1]) this.MaxMin[1]=realCoordinates[i+1];  //yMax
            if(realCoordinates[i+1]<MaxMin[4]) this.MaxMin[4]=realCoordinates[i+1];  //yMin
        }
    }
    /*
     if(obj instanceof Player) objMatrix = Game.getInstance().getPlayerMatrix();
     else objMatrix = obj.getMatrix();*/
    public boolean intersects(float[] matrix,BoundingBox other) {
        float[] foo = new float[MaxMin.length];
        Matrix.multiplyMV(foo,0,matrix,0,MaxMin,0);
        float[] other2 = other.getMaxMin(matrix);
        return !(foo[0] < other2[3] || foo[3] > other2[0] || foo[1] < other2[4] || foo[4] > other2[1]);
    }
    private float[] getMaxMin(float[] matrix) {
        float[] soulution = new float[MaxMin.length];
        Matrix.multiplyMV(soulution,0,matrix,0,MaxMin,0);
        return soulution;
    }

    public boolean Lineintersect(Point start, Point end) {
        float[] lineStart = new float[]{start.x, start.y};
        float[] lineEnd = new float[]{end.x, end.y};

        float[] boxMin = new float[]{MaxMin[3],MaxMin[4]};
        float[] boxMax = new float[]{MaxMin[0],MaxMin[1]};

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
