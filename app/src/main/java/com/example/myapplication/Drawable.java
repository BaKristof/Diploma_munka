package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


public class Drawable extends Specifications {
    protected final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTexCoord;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  fTexCoord = vTexCoord;" +
                    "}";
    protected final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(uTexture, fTexCoord);" +
                    "}";
    protected final int vertexStride = 3 * 4;
    protected final int vertexCount = squareCoords.length / 3;
    protected int positionHandle;
    protected int colorHandle;
    protected FloatBuffer vertexBuffer;
    protected ShortBuffer drawListBuffer;
    protected int Prog;
    private final short[] drawOrder = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
    private int TextCord;
    private FloatBuffer TexCoordBuffer;
    float[] color = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private int vertexShader;
    private int fragmentShader;
    private final float[] foo = new float[16];
    protected float[] rotateM = new  float[16];
    protected float[] scaleM = new float[16];

    public Drawable() {
        Matrix.setIdentityM(ownPositionM,0);
        Matrix.setIdentityM(rotateM,0);
        Matrix.setIdentityM(scaleM,0);
        setVertexShader(vertexShaderCode);
        setFragmentShader(fragmentShaderCode);
        setProg();
        setVertexBuffer();
        setDrawListBuffer();
        setTexCoordBuffer();
        setSpriteSheets(R.drawable.place_holder,64,64);
    }

    public void setVertexShader() {
        String vertexShaderCode = "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = vPosition;" +
                "}";
        this.vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
    }
    public void setVertexShader(String VertexSahderCode) {
        this.vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                VertexSahderCode);
    }
    public void setFragmentShader(String FragmentShadeCode) {
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

    public void setPositionHandle() {
        this.positionHandle = GLES20.glGetAttribLocation(Prog, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
    }

    public void setColorHandle() {
        this.colorHandle =GLES20.glGetUniformLocation(Prog, "vColor") ;
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
    }

    public void setVertexBuffer() {

        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        this.vertexBuffer = bb.asFloatBuffer();
        this.vertexBuffer.put(squareCoords);
        this.vertexBuffer.position(0);
    }

    public void setDrawListBuffer() {
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        this.drawListBuffer = dlb.asShortBuffer();
        this.drawListBuffer.put(drawOrder);
        this.drawListBuffer.position(0);    }

    public void setProg() {
        this.Prog =  GLES20.glCreateProgram();
        GLES20.glAttachShader(Prog, vertexShader);
        GLES20.glAttachShader(Prog, fragmentShader);
        GLES20.glLinkProgram(Prog);
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public void setvPMatrixHandle(float[] mvpMatrix) {
        Matrix.setIdentityM(foo,0);
        Matrix.multiplyMM(foo,0,foo,0,scaleM,0);
        Matrix.multiplyMM(foo,0,mvpMatrix,0, ownPositionM,0);
        Matrix.multiplyMM(foo,0,foo,0,rotateM,0);
        int vPMatrixHandle = GLES20.glGetUniformLocation(Prog, "uMVPMatrix");
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
    public void setTexCoordBuffer() {
        TexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        TexCoordBuffer.put(texCoords).position(0);
    }
    public void setoffHandels(){
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(TextCord);
        GLES20.glUseProgram(0);
    }
    public void setUniform1Handel(String codeSnippet,float unform){
        MyGLRenderer.checkGLError("setuni");
        int local = GLES20.glGetUniformLocation(Prog,codeSnippet);
        MyGLRenderer.checkGLError("setuni");
        GLES20.glUniform1f(local,unform);
    }
    public String getName() {
        return "Gameobj";
    }

}
