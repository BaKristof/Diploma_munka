package com.example.myapplication.SuperClasses;

import android.opengl.GLES20;

public class Character extends Drawable {
    int irany =0;
    public Character() {
        super();
    }

    public void draw(float[]mvpMatrix){
        GLES20.glUseProgram(Prog);
        setPositionHandle();
        setvPMatrixHandle(mvpMatrix);
        setTextCord();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, spriteSheets.NextOpenGLSFrame(irany));
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        setoffHandels();
    }
    public void hit(Specifications specifications){

    }

    public void setIrany(int irany) {
        this.irany = irany;
    }

    public String getName(){return "Charater";}
}
