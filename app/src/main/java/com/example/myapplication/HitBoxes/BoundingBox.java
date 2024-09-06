package com.example.myapplication.HitBoxes;

import android.util.Log;

import com.example.myapplication.BackGround.BGBlock;
import com.example.myapplication.MainClasses.MyGLRenderer;
import com.example.myapplication.BackGround.Room;
import com.example.myapplication.SuperClasses.Specifications;

import java.util.Arrays;
import java.util.Comparator;

public class BoundingBox {
    public float xMax=Float.NEGATIVE_INFINITY,yMax=Float.NEGATIVE_INFINITY;
    public float xMin=Float.POSITIVE_INFINITY,yMin=Float.POSITIVE_INFINITY;
    public BoundingBox(Specifications specific) {
        float[] valami = MyGLRenderer.allCoordinates(specific);
        for (int i = 0; i < valami.length; i+=3) {
            if(valami[i]>xMax)      xMax=valami[i];
            if(valami[i]<xMin)      xMin=valami[i];
            if(valami[i+1]<yMin)    yMin=valami[i+1];
            if(valami[i+1]>yMax)    yMax=valami[i+1];
        }
    }

    public BoundingBox(Room room) {
       /* float[]  valami0 = room.getCourners();
        float[] valami1 = MyGLRenderer.midleCoordinate(new float[]{valami0[0],valami0[1]},room.getMatrix() );
        float[] valami2 = MyGLRenderer.midleCoordinate(new float[]{valami0[2],valami0[3]},room.getMatrix());
        xMax=valami2[0];
        xMin=valami1[0];
        yMax=valami1[1];
        yMin=valami2[1];*/
        for (BGBlock foo: room.getWalls()) {
            if(foo.getOwnPositionM()[0]>xMax) xMax=foo.getOwnPositionM()[0];
            if(foo.getOwnPositionM()[0]<xMin) xMin=foo.getOwnPositionM()[0];
            if(foo.getOwnPositionM()[1]>yMax) yMax=foo.getOwnPositionM()[1];
            if(foo.getOwnPositionM()[1]<yMin) yMin=foo.getOwnPositionM()[1];
        }


    }

    public boolean intersects(BoundingBox other) {
        return !(other.xMax < this.xMin || other.xMin > this.xMax || other.yMax < this.yMin || other.yMin > this.yMax);
    }

    public boolean intersects(BoundingCircle circle) {
        // Find the closest point on the bounding box to the circle's center
        float closestX = Math.max(xMin, Math.min(xMax, circle.getX()));
        float closestY = Math.max(yMin, Math.min(yMax, circle.getY()));

        // Calculate the distance between the closest point and the circle's center
        float distanceSquared = (circle.getX() - closestX) * (circle.getX() - closestX) + (circle.getY() - closestY) * (circle.getY() - closestY);

        // Check if the distance is less than or equal to the circle's radius
        return distanceSquared <= circle.getRadius() * circle.getRadius();
    }




    /*public boolean Lineintersect(Point start, Point end) {
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
    }*/

    public boolean doesLineIntersect(Specifications start, Specifications end) {
        float[] stratPoint = MyGLRenderer.midleCoordinate(start);
        float[] endPoint = MyGLRenderer.midleCoordinate(end);

        // If the line segment is vertical
        if (stratPoint[0] == endPoint[0]) {
            return stratPoint[0] >= xMin && stratPoint[0] <= xMax &&
                    Math.max(stratPoint[1], endPoint[1]) >= yMin &&
                    Math.min(stratPoint[1], endPoint[1]) <= yMax;
        }

        // Calculate slope and intercept
        float slope = (endPoint[1] - stratPoint[1]) / (endPoint[0] - stratPoint[0]);
        float yIntercept = stratPoint[1] - slope * stratPoint[0];

        // Calculate possible intersection points with the box edges
        float[] intersectionY = new float[2];
        float[] intersectionX = new float[2];

        // Intersection with vertical boundaries
        intersectionY[0] = slope * xMin + yIntercept; // y at xMin
        intersectionY[1] = slope * xMax + yIntercept; // y at xMax

        // Intersection with horizontal boundaries
        intersectionX[0] = (yMin - yIntercept) / slope; // x at yMin
        intersectionX[1] = (yMax - yIntercept) / slope; // x at yMax

        // Check if any of the intersection points lie within the bounding box and the segment

        return (intersectionY[0] >= yMin && intersectionY[0] <= yMax && isBetween(stratPoint[0], endPoint[0], xMin)) ||
                (intersectionY[1] >= yMin && intersectionY[1] <= yMax && isBetween(stratPoint[0], endPoint[0], xMax)) ||
                (intersectionX[0] >= xMin && intersectionX[0] <= xMax && isBetween(stratPoint[1], endPoint[1], yMin)) ||
                (intersectionX[1] >= xMin && intersectionX[1] <= xMax && isBetween(stratPoint[1], endPoint[1], yMax));
    }
    private boolean isBetween(float start, float end, float point) {
        return point >= Math.min(start, end) && point <= Math.max(start, end);
    }
    public boolean contains(BoundingBox other) {
            return xMin <= other.xMin && xMax >= other.xMax && yMin <= other.yMin && yMax >= other.yMax;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BoundingBox{");
        sb.append("xMax=").append(xMax);
        sb.append(", yMax=").append(yMax);
        sb.append(", xMin=").append(xMin);
        sb.append(", yMin=").append(yMin);
        sb.append('}');
        return sb.toString();
    }
}
