package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.util.ArrayList;

public class SpriteSheets {
    int width;
    int height;
    ArrayList<Integer[]> spriteSheetArray = new ArrayList<>();
    private final int placeHolder =MyGLRenderer.loadTexture(R.drawable.place_holder);;

    int counter =0;
    public SpriteSheets(int resourceId,int width,int height) {
        this.height = height;
        this.width = width;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resourceId, options);
        for (int i = 0; i < bitmap.getWidth(); i+=width) {
            ArrayList<Integer> textureline = new ArrayList<>();
            for (int j = 0; j < bitmap.getHeight(); j+=height) {
                int[] textureId = new int[1];
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, Bitmap.createBitmap(bitmap,j,i,width,height), 0);
                textureline.add(textureId[0]);
            }
            spriteSheetArray.add(textureline.toArray(new Integer[]{}));
        }
        bitmap.recycle();
    }
    public int NextFrame(int irany){
        int a =placeHolder;
            if (spriteSheetArray.get(irany)!=null){
                if (spriteSheetArray.get(irany).length<=counter) counter=0;
                a = spriteSheetArray.get(irany)[counter];
            }
            counter ++;
        return a;
    }

}
