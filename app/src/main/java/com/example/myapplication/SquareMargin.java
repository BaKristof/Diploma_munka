package com.example.myapplication;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class SquareMargin extends Specifications {
    float[] matrix = new float[16];
    private int positionHandle;
    private int colorHandle;
    private int vPMatrixHandle;
    static float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };


    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  gl_Position = gl_Position + vec4(0.1, 0.1, 0.0, 0.0) " +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private final String fragmentShaderCode2 =
            "precision mediump float;" +
                  //  "uniform vec4 vColor;" +
                    "uniform float width;" +
                    "uniform float height;" +
                    "void main() {" +
                    "if (gl_FragCoord.x < 0.01*width || gl_FragCoord.x > 0.99*width || gl_FragCoord.y < 0.01*height || gl_FragCoord.y > 0.99*height) {"  +
                    " gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0); }" +
                    "    else { " +
                    "       gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0); }" +
                    "}";
    private int Prog;

    /*
    * uniform vec4 squareColor;

void main()
{
    if (gl_FragCoord.x < 0.1 || gl_FragCoord.x > 0.9 ||
        gl_FragCoord.y < 0.1 || gl_FragCoord.y > 0.9)
    {
        gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0); // white color
    }
    else
    {
        gl_FragColor = squareColor;
    }
}
    * */




    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private FloatBuffer mTexCoordBuffer;
    private  int TextCord;
    static final int COORDS_PER_VERTEX = 3;
    Specifications parent;

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    public SquareMargin(Specifications specifications) {
        parent = specifications;
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode2);
        MyGLRenderer.checkGLError("sheder load in ");
        Prog = GLES20.glCreateProgram();
        GLES20.glAttachShader(Prog, vertexShader);
        GLES20.glAttachShader(Prog, fragmentShader);
        GLES20.glLinkProgram(Prog);


        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public void draw(float[]mvpMatrix ) {
        GLES20.glUseProgram(Prog);

        vPMatrixHandle = GLES20.glGetUniformLocation(Prog, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

        colorHandle = GLES20.glGetUniformLocation(Prog, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        float[] loaclcord = MyGLRenderer.allCoordinates(parent);

        int width = GLES20.glGetUniformLocation(Prog,"width");
        GLES20.glUniform1f(width,loaclcord[0]-loaclcord[6]);


        int height = GLES20.glGetUniformLocation(Prog,"height");

        GLES20.glUniform1f(height,loaclcord[1]-loaclcord[7]);
        /*TextCord = GLES20.glGetAttribLocation(Prog, "vTexCoord");
        GLES20.glEnableVertexAttribArray(TextCord);
        GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, mTexCoordBuffer);*/

        //GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, MyGLRenderer.loadTexture(R.drawable.karakter));

        positionHandle = GLES20.glGetAttribLocation(Prog, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }


}