package com.example.myapplication;

public class BoundingBox {
    private float[] cordinates = Specifications.getSquareCoords();
    public float xMax=Float.NEGATIVE_INFINITY,yMax=Float.NEGATIVE_INFINITY;
    public float xMin=Float.POSITIVE_INFINITY,yMin=Float.POSITIVE_INFINITY;


    public BoundingBox(Specifications specific) {
        float[] valami = MyGLRenderer.allCoordinates(specific);

        xMax=valami[6];
        xMin=valami[0];
        yMax=valami[1];
        yMin=valami[4];

        // Log.e("BoundingBox2",specific.getName()+"     xmax: "+xMax+" ymax: "+yMax+" xmin: "+xMin+" ymin: "+yMin);
    }
    public boolean intersects(BoundingBox other) {
        return !(other.xMax < this.xMin || other.xMin > this.xMax || other.yMax < this.yMin || other.yMin > this.yMax);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BoundingBox2{");
        sb.append("x=").append(xMax-Specifications.blocksize/2);
        sb.append(", y=").append(yMax-Specifications.blocksize/2);
        sb.append('}');
        return sb.toString();
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

        // Check if the line is vertical
        if (stratPoint[0] == endPoint[0]) {
            // The line is vertical, check if it intersects the bounding box horizontally
            return stratPoint[0] >= xMin && stratPoint[0] <= xMax;
        }

        // Calculate the slope and y-intercept of the line
        float slope = (endPoint[1] - stratPoint[1]) / (endPoint[0] - stratPoint[0]);
        float yIntercept = stratPoint[1] - slope * stratPoint[0];

        // Calculate the intersection points of the line with the bounding box
        float x1 = xMin, x2 = xMax;
        float y1 = slope * x1 + yIntercept, y2 = slope * x2 + yIntercept;

        // Check if the intersection points are within the bounding box
        if (y1 >= yMin && y1 <= yMax) {
            return true;
        }
        if (y2 >= yMin && y2 <= yMax) {
            return true;
        }

        // The line does not intersect the bounding box
        return false;
    }



    }
