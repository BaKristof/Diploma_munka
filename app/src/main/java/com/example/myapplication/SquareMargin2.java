package com.example.myapplication;

import android.opengl.Matrix;

import com.example.myapplication.BackGround.Room;
import com.example.myapplication.MainClasses.MyGLRenderer;
import com.example.myapplication.SuperClasses.Drawable;
import com.example.myapplication.SuperClasses.Line;
import com.example.myapplication.SuperClasses.Specifications;

public class SquareMargin2 extends Drawable {
    Line[] valami = new Line[4];
    Specifications parent ;
    public SquareMargin2(Specifications specifications ) {
    parent = specifications;
    }

    public SquareMargin2(Room room) {
        float[] valami0 = room.getCourners();
        float[] valami3 = MyGLRenderer.midleCoordinate(new float[]{valami0[0],valami0[1]},room.getMatrix() );
        float[] valami4 = MyGLRenderer.midleCoordinate(new float[]{valami0[2],valami0[3]},room.getMatrix());
        float[] valami2 = Line.lineCoords;
        float scalingX =(valami3[0]-valami3[1])/(valami2[6]-valami2[0]);
        float scalingY =(valami4[0]-valami4[1])/(valami2[1]-valami2[4]);

        float[] loccalM = new float[16];

        System.arraycopy(room.getMatrix(),0,loccalM,0,loccalM.length);
        Matrix.scaleM(loccalM,0,scalingX,scalingY,0);
        for (Line line : valami) {
            line =new Line(loccalM);
            Matrix.rotateM(loccalM,0,0,0,0,0.25f);
        }

    }

    public void draw(float[]mvpMatrix){


    }
}
