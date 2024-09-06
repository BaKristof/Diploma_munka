package com.example.myapplication.MainClasses;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import com.example.myapplication.BackGround.Room;
import com.example.myapplication.SquareMargin2;
import com.example.myapplication.SuperClasses.Specifications;

import java.util.ArrayList;
import java.util.Arrays;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private long lastFrameTime;
    private static final long targetElapsedTime = 1000000000 / 60;
    private final static float[]  vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];;
    private Game gm;
    MyGLSurfaceView glsw;
    private static boolean stoprender = true;
    public static float eyeZ =3.0f;
    public static float upY =3.0f;
    private static ArrayList<SquareMargin2> margins = new ArrayList<>();

    public MyGLRenderer(MyGLSurfaceView glSurfaceView) {
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        this.glsw =glSurfaceView;
    }
    public void setCameraToObject(Specifications specifications){
        float[] ownPositionMidle = MyGLRenderer.midleCoordinate(specifications);
        Matrix.setLookAtM(viewMatrix,0, ownPositionMidle[0], ownPositionMidle[1], ownPositionMidle[2]+MyGLRenderer.eyeZ, ownPositionMidle[0], ownPositionMidle[1], ownPositionMidle[2], 0f, 1.0f, 0.0f);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gm = Game.getInstance();
        lastFrameTime = System.nanoTime();
    }

    public void onDrawFrame(GL10 unused) {
        setCameraToObject(Game.getInstance().getPlayer());
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastFrameTime;

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        gm.beforDraw();
        gm.draw(vPMatrix);
       // MyGLRenderer.checkGLError("draw van e problémaalsjmfaj");
        for (SquareMargin2 margin : margins) {
            margin.draw(vPMatrix);
        }
       // MyGLRenderer.checkGLError("draw van e probléma");




        lastFrameTime = currentTime;
       /* if (elapsedTime < targetElapsedTime) {
            try {
                Thread.sleep((targetElapsedTime - elapsedTime) / 1000000); // Convert nanoseconds to milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else Log.e("frametimer","tulfotottunk");*/
        if (stoprender) glsw.requestRender();
       // Log.e("time","time: "+ elapsedTime);

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }
    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    public static void checkGLError(String operation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(operation + ": glError " + error);
        }
    }
    public static int loadTexture(int resourceId) {
        final int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resourceId, options);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        return textureId[0];
    }
    public static int loadTexture(Bitmap bitmap) {
        final int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        return textureId[0];
    }
    public static float[] midleCoordinate(Specifications specific ) {
            float[] inputPoint = {0.0f, 0.0f, 0.0f, 1.0f};
            float[] TESZT = specific.getOwnPositionM().clone();
            Matrix.multiplyMV(inputPoint, 0,TESZT , 0, inputPoint, 0);
        Log.println(Log.ERROR,specific.getName(), Arrays.toString(inputPoint));
        return inputPoint;
    }
    public static float[] midleCoordinate(float[] valami, float[] screenPostioinM) {
        float[] inputPoint = {valami[0],valami[1], 0.0f, 1.0f};
        Matrix.multiplyMV(inputPoint, 0,screenPostioinM , 0, inputPoint, 0);
        //Log.println(Log.ERROR,specific.getName(), Arrays.toString(inputPoint));
        return inputPoint;
    }
    public static float[] allCoordinates(Specifications specific ) {
        float[] TESZT = specific.getOwnPositionM();
        float[]  input = Specifications.getSquareCoords();
        float[] local = new float[]{0.0f,0.0f,0.0f,1.0f};
        float[] output = new float[Specifications.getSquareCoords().length];
        int j = 0;
        for (int i = 0; i < input.length; i+=3) {
            System.arraycopy(input,i,local,0,3);
            Matrix.multiplyMV(local, 0,TESZT , 0,local , 0);
            System.arraycopy(local,0,output,i,3);
        }
        //Log.println(Log.ERROR,specific.getName(), Arrays.toString(output));
        return output;
    }



  /*public static void addmargin(Room room){
        float[] valami0 = room.getCourners();
        float[] valami3 = MyGLRenderer.midleCoordinate(new float[]{valami0[0],valami0[1]},room.getMatrix() );
        float[] valami4 = MyGLRenderer.midleCoordinate(new float[]{valami0[2],valami0[3]},room.getMatrix());
        float[] valami2 = Specifications.getSquareCoords();
        float scalingX =(valami3[0]-valami3[1])/(valami2[6]-valami2[0]);
        float scalingY =(valami4[0]-valami4[1])/(valami2[1]-valami2[4]);

        float[] loccalM = new float[16];

        System.arraycopy(room.getMatrix(),0,loccalM,0,loccalM.length);
        Matrix.scaleM(loccalM,0,scalingX,scalingY,0);
        margins.add(new SquareMargin2(new Specifications().setOwnPositionM(loccalM)));
    }*/
    public static void addmargin(Specifications specifications){
        margins.add(new SquareMargin2(specifications));
    }


    public static void setStoprender() {
        MyGLRenderer.stoprender = false;
    }
    public static float[] getvPMatrix() {return vPMatrix;}
}
