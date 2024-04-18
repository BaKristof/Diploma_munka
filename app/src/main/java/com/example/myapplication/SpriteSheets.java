package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;

public class SpriteSheets {
    int width;
    int height;
    ArrayList<Integer[]> spriteSheetArray = new ArrayList<>();
    private long currentTime = System.nanoTime();
    private static final long elapseConst = 1000000000/60;
    private static final int placeholder= MyGLRenderer.loadTexture(R.drawable.place_holder);
    int FPS;
    int counter =0;
    public SpriteSheets(int resourceId,int width,int height,int FPS) {
        this.height = height;
        this.width = width;
        this.FPS =FPS;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resourceId, options);
        for (int i = 0; i < bitmap.getWidth(); i+=width) {
            ArrayList<Integer> textureline = new ArrayList<>();
            for (int j = 0; j < bitmap.getHeight(); j+=height) {

                Bitmap map = Bitmap.createBitmap(bitmap,j,i,width,height);
                textureline.add(MyGLRenderer.loadTexture(map));
            }
            spriteSheetArray.add(textureline.toArray(new Integer[]{}));
        }
        bitmap.recycle();
    }
    public int NextFrame(int irany){

        int texture =spriteSheetArray.get(0)[0];
            if (irany <= spriteSheetArray.size()-1){
                if (spriteSheetArray.get(irany).length<=counter) {
                    counter = 0;
                }
                texture = spriteSheetArray.get(irany)[counter];
            }
        long local = System.nanoTime()-currentTime;

        if (local >= elapseConst * FPS){
            counter ++;
            currentTime =System.nanoTime();
        }

        Log.e("valami ","time: "+ local+"   time courrent :"+ currentTime);
        return texture;
    }
    public int NextFrame(){
        return spriteSheetArray.get(0)[0];
    }

}
