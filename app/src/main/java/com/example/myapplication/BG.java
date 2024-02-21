package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BG extends GameObj {
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
                    "vec4 color = texture2D(uTexture, fTexCoord);" +
                    "if (color.a == 0.0) {" +
                    "discard;}" +
                    "gl_FragColor = color;" +
                    "}";

    private final int[] listofblocks={
            R.drawable._1,
            R.drawable._2,
            R.drawable._3,
            R.drawable._4,
            R.drawable._5,// bal sarokelem
            R.drawable._6,
            R.drawable._7,
            R.drawable._8,
            R.drawable._9,
            R.drawable._10,
            R.drawable._11,
            R.drawable._12,
            R.drawable._13,//jobb sarokelem
            R.drawable._14,
            R.drawable._15,
            R.drawable._t0,//15
            R.drawable._t1,
            R.drawable._t2,
            R.drawable._t3,
            R.drawable._t4,
            R.drawable._t5, //20
            R.drawable._t6,
            R.drawable._t7,
            R.drawable._t8,
            R.drawable._t9,
            R.drawable._16,//25
            R.drawable._17,
            R.drawable._18,
            R.drawable._19,
    };




    private final Map<Integer,Integer> texture = new HashMap<>();
    private ArrayList<Integer> valami = new ArrayList<>();
    public int[][] completback;
    public BGBlock[][]  BG;

    public BG( int[][] maze) {

        Log.e("adat","  "+blocksize);
        //  BG = new BGBlock[lenght*5][hight*5];
        //   completback = MazeGenerater.generate(lenght,hight);
        for (int i=0 ; i<listofblocks.length;i++){
            Integer a = MyGLRenderer.loadTexture( listofblocks[i]);
            texture.put(i,a);
        }

        for (int i = 15; i <25 ; i++) {
            valami.add(texture.get(i));
        }
        setVertexShader(vertexShaderCode);
        setFragmentShader(fragmentShaderCode);
        setProg();
        setVertexBuffer();
        setDrawListBuffer();
        setTexCoordBuffer();
        setCompletback(maze);
        LoadUpBG();

    }
    public void setCompletback(int[][] completback) {
        this.BG = new BGBlock[completback.length][completback[0].length];
        this.completback = completback;
    }

    public int[][] getCompletback() {
        return completback;
    }

    private void LoadUpBG() {
        int a = completback.length;
        for (int i=0; i<completback.length;i++){
            for(int j=0;j< completback[0].length; j++){
                BG[i][j] = TextureFromInt(completback[i][j],i,j);
            }
        }
    }

    private BGBlock TextureFromInt(int id, int i, int j ) {
        BGBlock vissza = new BGBlock();
       // Log.e("valami","bakosssss     "+blocksize);
        vissza.setMatrix( j* blocksize,i* blocksize*-1,0);
        vissza.setTextureID(texture.get(id));
        return vissza;

    }
    public void draw(float[]moveMatrix){

        GLES20.glUseProgram(Prog);
        setPositionHandle();
        setTextCord();
        //  setColorHandle();
        for (BGBlock[] bc : BG) {
            for (BGBlock bgb : bc) {
                System.arraycopy(bgb.getMatrix(),0,plsmove,0, plsmove.length);
                setvPMatrixHandle(moveMatrix);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bgb.getTextureID());
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
            }
        }
      //  MyGLRenderer.whereareyou(FinalMatrix,squareCoords);
        setoffHandels();
    }
    public BGBlock[] foundnearblocks(Point point){
        Log.e("teszt","idáig eljutsz");
        int x = point.getX();
        int y = point.getY();
        ArrayList<BGBlock> near = new ArrayList<>();
        for (int i = x-4; i < x+4; i++) {
            for (int j = y-4; j < y+4; j++) {
                if (i<=BG.length && i>=0){
                    if (j<=BG[0].length && j>=0){
                        if (!valami.contains(BG[i][j].getTextureID())){
                            near.add(BG[i][j]);
                        }
                    }
                }
            }
        }
        return near.toArray(new BGBlock[0]);
    }
    public float[] getboxmidel(int x,int y){
        return BG[x*5][y*5].getMatrix();
    }
    public float[] getboxmidel(int[] xy){
        return BG[(xy[0]*5)+2][(xy[1]*5)+2].getMatrix();
    }


}
