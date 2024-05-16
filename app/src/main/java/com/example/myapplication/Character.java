package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

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
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,spriteSheets.NextFrame(irany) );
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        setoffHandels();
    }
    public boolean hit(BoundingBox bb){
       return new BoundingBox(this).intersects(bb);
    }

    public void setIrany(int irany) {
        this.irany = irany;
    }

    public String getName(){return "Charater";}
}
