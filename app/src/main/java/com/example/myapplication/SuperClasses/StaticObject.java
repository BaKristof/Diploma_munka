package com.example.myapplication.SuperClasses;

import android.opengl.GLES20;

import com.example.myapplication.Enemys.Spawners.Spawner;
import com.example.myapplication.MainClasses.SpriteSheets;

public class StaticObject extends Drawable {


    public StaticObject(SpriteSheets spriteSheets) {
    setSpriteSheets(spriteSheets);
    }

    public StaticObject() {
        setSpriteSheets(new SpriteSheets());
    }
    public StaticObject setPosition (float[] position){
        this.setOwnPositionM(position);
        return this;
    }
    public void draw(float[] mvpMatrix){
        GLES20.glUseProgram(Prog);
        setPositionHandle();
        setvPMatrixHandle(mvpMatrix);
        setTextCord();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,spriteSheets.NextFrame(0) );
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        setoffHandels();
    }

    public void hit (Specifications specifications){
    }
}
