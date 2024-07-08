package com.example.myapplication.SuperClasses;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.myapplication.MainClasses.MyGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Line {

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  gl_Position = gl_Position + vec4(0.1,0.1,0.0,0.0);" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "void main() {" +
                    "  gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);" +
                    "}";

    private final FloatBuffer vertexBuffer;
    float[] matrix = new float[16] ;
    private final int mProgram;
    private int mPositionHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    protected static final float size =0.25f;
    public static float lineCoords[] = {
            -0.15f*size,  0.0f*size, 0.0f,   // start point
            0.15f*size,  0.0f*size, 0.0f };  // end point

    public Line(float[] matrix) {
        System.arraycopy(matrix,0,this.matrix,0,this.matrix.length);
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                lineCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(lineCoords);
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL ES program executables
    }

    public void draw(float[] mVPmatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        float[] local= new float[16];
        Matrix.setIdentityM(local,0);
        Matrix.multiplyMM(local,0,mVPmatrix,0,matrix,0);

        int vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, local, 0);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the line coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                COORDS_PER_VERTEX * 4, vertexBuffer);

        // Draw the line
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, lineCoords.length / COORDS_PER_VERTEX);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glUseProgram(0);
    }
}
