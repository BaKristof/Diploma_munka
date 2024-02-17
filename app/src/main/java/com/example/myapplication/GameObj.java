package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class GameObj {
    protected float[] matrix = new float[16];
    protected static float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };
    private static final float size =0.5f;
    protected static  float[] squareCoords = {
            -0.15f*size,  0.15f*size, 0.0f,   // left top
            -0.15f*size, -0.15f*size, 0.0f,   // left bottom
            0.15f*size, -0.15f*size, 0.0f,   // right bottom
            0.15f*size,  0.15f*size, 0.0f }; // right top

    protected final int vertexCount = squareCoords.length / 3;
    protected int positionHandle;
    protected int colorHandle;
    protected FloatBuffer vertexBuffer;
    protected ShortBuffer drawListBuffer;
    protected int Prog;
    private static final short[] drawOrder = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
    private int TextCord;
    private FloatBuffer TexCoordBuffer;
    private int textureID;
    float[] color = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    public static final float blocksize = squareCoords[1]-squareCoords[7];
    private int vertexShader;
    private int fragmentShader;

    public void setVertexShader() {
        String vertexShaderCode = "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = vPosition;" +
                "}";
        this.vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
    }
    private void setVertexShader(String VertexSahderCode) {
        this.vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                VertexSahderCode);
    }
    private void setFragmentShader(String FragmentShadeCode) {
        this.fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                FragmentShadeCode);
    }
    public void setFragmentShader() {
        String fragmentShaderCode = "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}";
        this.fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
    }

    protected void setPositionHandle() {
        this.positionHandle = GLES20.glGetAttribLocation(Prog, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
    }

    public void setColorHandle() {
        this.colorHandle =GLES20.glGetUniformLocation(Prog, "vColor") ;
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
    }

    private void setVertexBuffer() {

        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        this.vertexBuffer = bb.asFloatBuffer();
        this.vertexBuffer.put(squareCoords);
        this.vertexBuffer.position(0);
    }

    private void setDrawListBuffer() {
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        this.drawListBuffer = dlb.asShortBuffer();
        this.drawListBuffer.put(drawOrder);
        this.drawListBuffer.position(0);    }

    private void setProg() {
        this.Prog =  GLES20.glCreateProgram();
        GLES20.glAttachShader(Prog, vertexShader);
        GLES20.glAttachShader(Prog, fragmentShader);
        GLES20.glLinkProgram(Prog);
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public void setvPMatrixHandle() {

        int vPMatrixHandle = GLES20.glGetUniformLocation(Prog, "uMVPMatrix");
        float[] foo = new float[16];
        Matrix.multiplyMM(foo,0,matrix,0,MyGLRenderer.getProjectionMatrix(),0);
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, foo, 0);
    }

    public void setTextCord() {
        TextCord = GLES20.glGetAttribLocation(Prog, "vTexCoord");
        GLES20.glEnableVertexAttribArray(TextCord);
        GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, TexCoordBuffer);
    }
    public void setTexCoordBuffer(float[] texCoords2) {
        TexCoordBuffer = ByteBuffer.allocateDirect(texCoords2.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        TexCoordBuffer.put(texCoords2).position(0);
    }
    private void setTexCoordBuffer() {
        TexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        TexCoordBuffer.put(texCoords).position(0);
    }

    public void setoffHandels(){
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(TextCord);
        GLES20.glUseProgram(0);
    }

    public float[] getSquareCoords() {
        return squareCoords;
    }
    public float[] getMatrix() {
        return matrix;
    }
    public  void construct(String VertexSahderCode,String FragmentShadeCode){
        if(VertexSahderCode.isEmpty())setVertexShader();
        else setVertexShader(VertexSahderCode);
        if(FragmentShadeCode.isEmpty())setFragmentShader();
        else setFragmentShader(FragmentShadeCode);
        setProg();
        setVertexBuffer();
        setDrawListBuffer();
        setTexCoordBuffer();
    }

}
