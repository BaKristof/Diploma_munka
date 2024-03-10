package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Character extends Drawable {

    private BoundingBox boundingBox ;
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
        boundingBox =new  BoundingBox();
        Matrix.setIdentityM(ownPositionM,0);
     //   boundingBox = new BoundingBox(this);
        setVertexShader(vertexShaderCode);
        setFragmentShader(fragmentShaderCode);
        setProg();
        setVertexBuffer();
        setDrawListBuffer();
        setTexCoordBuffer();
        //setTextireID(resourceId);
       // setColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
    }

    public void draw(float[]mvpMatrix){
        GLES20.glUseProgram(Prog);
        setPositionHandle();
      //  setColorHandle();
        setvPMatrixHandle(mvpMatrix);
        setTextCord();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,animation.NextFrame(irany) );
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        setoffHandels();
    }
    public boolean hit(float[] matrix,BoundingBox bb){
       return boundingBox.intersects(matrix,bb);
    }
    public void setAnimation(int[] a) {
        animation = new Animation(a);
    }
    public void setIrany(int irany) {
        this.irany = irany;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
    public String getName(){return "Charater";}
}
