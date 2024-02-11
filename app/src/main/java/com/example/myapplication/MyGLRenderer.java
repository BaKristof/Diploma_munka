package com.example.myapplication;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    //TODO movement be constant by fps
    private Square sq;
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private Game gm;
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
       // sq = new Square();
        gm = Game.getInstance();
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
      //  sq.draw(vPMatrix);
        gm.draw(vPMatrix);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }
    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    public static int loadTexture(int resourceId) {
        final int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resourceId, options);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        return textureId[0];
    }
    public static void checkGLError(String operation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(operation + ": glError " + error);
        }
    }
    public static void whereareyou(float[] matrix, float[] objectCoords) {
        float[] transformedCoords = new float[objectCoords.length];

        // Copy the original coordinates to avoid modifying the original array
        System.arraycopy(objectCoords, 0, transformedCoords, 0, objectCoords.length);

        // Apply the transformation matrix to each vertex
        for (int i = 0; i < objectCoords.length; i += 3) {
            float x = objectCoords[i];
            float y = objectCoords[i + 1];
            float z = objectCoords[i + 2];

            // Set up the homogeneous coordinates
            float[] inputPoint = {x, y, z, 1.0f};

            // Multiply the matrix by the point
            Matrix.multiplyMV(inputPoint, 0, matrix, 0, inputPoint, 0);

            // Update the transformed coordinates
            transformedCoords[i] = inputPoint[0];
            transformedCoords[i + 1] = inputPoint[1];
            transformedCoords[i + 2] = inputPoint[2];
        }

        Log.println(Log.ERROR,"codinates find", Arrays.toString(transformedCoords));
    }


}
