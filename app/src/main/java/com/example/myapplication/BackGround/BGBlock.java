package com.example.myapplication.BackGround;

import android.opengl.Matrix;

import androidx.annotation.NonNull;

import com.example.myapplication.MainClasses.MyGLRenderer;
import com.example.myapplication.SuperClasses.Specifications;

public class BGBlock extends Specifications {
    BlockTypes texture = new BlockTypes();

    public BGBlock() {
        Matrix.setIdentityM(ownPositionM,0);
    }
    public void sizechnage(float valami){
        Matrix.scaleM(ownPositionM,0,valami,valami,0);
    }

    @Override
    public String getName() {
        return "BGBlock";
    }

    public void setTexture(Tiles tile) {
        this.texture.setTexture(tile);
    }
    public int getTexture() {
        return texture.getTexture();
    }
    public boolean isHitable(){
        return texture.isHitable();
    }
}
