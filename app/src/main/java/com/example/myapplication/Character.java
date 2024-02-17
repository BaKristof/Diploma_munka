package com.example.myapplication;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Character extends GameObj{
    private BoundingBox boundingBox ;
    protected Animation animation= new Animation();
    int irany =0;
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
    public Character() {
    construct(vertexShaderCode,fragmentShaderCode);

    }
    public void draw(){
        GLES20.glUseProgram(Prog);
        setPositionHandle();
        setvPMatrixHandle();
        setTextCord();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,animation.NextFrame(irany) );
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        setoffHandels();
    }
    public boolean hit(GameObj gameObj){
       return new BoundingBox(this).intersects(new BoundingBox(gameObj));
    }
    public void setIrany(int irany) {
        this.irany = irany;
    }
    public void setAnimation(int[] a) {
        animation = new Animation(a);
    }
    public void setAnimation(int[] backward,int[] left,int[] forward,int[] right) {
        animation = new Animation(backward,left,forward,right);
    }
}
