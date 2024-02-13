package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

public class Circle {
    //TODO make it happen
    private float[] matrix = new float[16];
    private float[] FinalMatrix = new float[16];
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
               "void main() {" +
                  "gl_Position = uMVPMatrix * vPosition;" +
               "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private static final int COORDS_PER_VERTEX = 2;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private int positionHandle;
    private int colorHandle;
    private int vPMatrixHandle;
    private int Prog;
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private FloatBuffer mTexCoordBuffer;
    private  int TextCord;
    private float[] circleCoords;

    private final int vertexCount = circleCoords.length / COORDS_PER_VERTEX;

    public Circle(float positionx,float positiony,float radius) {

        circleCoords = generateCircle(  positionx, positiony, radius, 50);

        // Initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                circleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(circleCoords);
        vertexBuffer.position(0);


        Matrix.setIdentityM(matrix,0);
        Matrix.setIdentityM(FinalMatrix,0);
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        Prog = GLES20.glCreateProgram();
        GLES20.glAttachShader(Prog, vertexShader);
        GLES20.glAttachShader(Prog, fragmentShader);
        GLES20.glLinkProgram(Prog);
        // initialize vertex byte buffer for shape coordinates

    }
    public void draw(float[]mvpMatrix ) {
        GLES20.glUseProgram(Prog);



        Matrix.multiplyMM(FinalMatrix, 0, matrix, 0, mvpMatrix, 0);
        vPMatrixHandle = GLES20.glGetUniformLocation(Prog, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, FinalMatrix, 0);



        colorHandle = GLES20.glGetUniformLocation(Prog, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);



        GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, mTexCoordBuffer);
        positionHandle = GLES20.glGetAttribLocation(Prog, "vPosition");



        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);


        /*colorHandle = GLES20.glGetUniformLocation(Prog, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);*/

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }
    public static float[] generateCircle(float centerX, float centerY, float radius, int numPoints) {
        ArrayList<Float> coordinates = new ArrayList<>();

        // Angle increment between each point on the circle
        float angleIncrement = (float) (2 * Math.PI / numPoints);

        for (int i = 0; i < numPoints; i++) {
            // Calculate the angle for this point
            float angle = i * angleIncrement;

            // Calculate the coordinates of the point using trigonometry
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);

            // Add the coordinates to the list
            coordinates.add(x);
            coordinates.add(y);
        }
        float[] a = new float[coordinates.size()];
        int i=0;
        for (float c : coordinates) {
            a[i++]= c;
        }

        return a;
    }
}
