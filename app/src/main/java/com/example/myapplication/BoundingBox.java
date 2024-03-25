package com.example.myapplication;

public class BoundingBox {
    public float xMax=Float.NEGATIVE_INFINITY,yMax=Float.NEGATIVE_INFINITY;
    public float xMin=Float.POSITIVE_INFINITY,yMin=Float.POSITIVE_INFINITY;


    public BoundingBox(Specifications specific) {
        float[] valami = MyGLRenderer.allCoordinates(specific);

        xMax=valami[6];
        xMin=valami[0];
        yMax=valami[1];
        yMin=valami[4];

    }

    public BoundingBox(float xMax, float yMax, float xMin, float yMin) {
        this.xMax = xMax;
        this.yMax = yMax;
        this.xMin = xMin;
        this.yMin = yMin;
    }
    /*public BoundingBox setMatrix(){
        float[] valami = MyGLRenderer.allCoordinates(specific);

        xMax=valami[6];
        xMin=valami[0];
        yMax=valami[1];
        yMin=valami[4];
        return this;
    }*/

    public BoundingBox(Specifications[] specifications) {

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

    public boolean doesLineIntersect(Specifications start ,Specifications end) {
        float[] stratPoint= MyGLRenderer.allCoordinates(start);
        float[] endPoint= MyGLRenderer.allCoordinates(end);

        if (stratPoint[0] == endPoint[0]) {
            return stratPoint[0] >= xMin && stratPoint[0] <= xMax;
        }

        float slope = (endPoint[1] - stratPoint[1]) / (endPoint[0] - stratPoint[0]);
        float yIntercept = stratPoint[1] - slope * stratPoint[0];

        float x1 = xMin, x2 = xMax;
        float y1 = slope * x1 + yIntercept, y2 = slope * x2 + yIntercept;

        if (y1 >= yMin && y1 <= yMax) {
            return true;
        }
        if (y2 >= yMin && y2 <= yMax) {
            return true;
        }

        return false;
    }
    public boolean contains(BoundingBox other) {
            return xMin <= other.xMin && xMax >= other.xMax && yMin <= other.yMin && yMax >= other.yMax;
    }
}
