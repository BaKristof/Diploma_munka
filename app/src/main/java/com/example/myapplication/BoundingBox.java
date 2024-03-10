package com.example.myapplication;

import android.opengl.Matrix;

import java.util.ArrayList;

public class BoundingBox {
    private float[] cordinates = Specifications.getSquareCoords();
    public float xMax=Float.NEGATIVE_INFINITY,yMax=Float.NEGATIVE_INFINITY;
    public float xMin=Float.POSITIVE_INFINITY,yMin=Float.POSITIVE_INFINITY;


    public BoundingBox(Specifications specific) {
        Point p = MyGLRenderer.whereisyourmidle(specific);
        float[] valami = p.pointToCordinates();

        xMax=valami[0];
        xMin=valami[1];
        yMax=valami[2];
        yMin=valami[3];

        // Log.e("BoundingBox2",specific.getName()+"     xmax: "+xMax+" ymax: "+yMax+" xmin: "+xMin+" ymin: "+yMin);
    }
    public boolean intersects(BoundingBox other) {
        return !(other.xMax < this.xMin || other.xMin > this.xMax || other.yMax < this.yMin || other.yMin > this.yMax);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BoundingBox2{");
        sb.append("xMax=").append(xMax);
        sb.append(", yMax=").append(yMax);
        sb.append(", xMin=").append(xMin);
        sb.append(", yMin=").append(yMin);
        sb.append('}');
        return sb.toString();
    }

    public boolean Lineintersect(Point start, Point end) {
        float[] lineStart = new float[]{start.x, start.y};
        float[] lineEnd = new float[]{end.x, end.y};

        float[] boxMin = new float[]{xMin,yMin};
        float[] boxMax = new float[]{xMax,yMax};

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
