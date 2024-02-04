package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class BG extends GameObj {
    private final float[] FinalMatrix = new float[16];
    private final float[] BGMove = new float[16];
    private final float[] foo = new float[16];
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
            R.drawable._t9,};
    private Map<Integer,Integer>textura = new HashMap<>();
    public int[][] completback;
    public BGBlock[][]  BG;
    public BG( int lenght, int hight) {
        Matrix.setIdentityM(foo,0);
        Matrix.setIdentityM(BGMove,0);
    //    Matrix.translateM(BGMove,0,  blocksize *lenght*2.5f, blocksize*2.5f*hight,0.1f);
      //  BG = new BGBlock[lenght*5][hight*5];
     //   completback = MazeGenerater.generate(lenght,hight);
        for (int i=0 ; i<listofblocks.length;i++){
            Integer a = MyGLRenderer.loadTexture( listofblocks[i]);
            textura.put(i,a);
        }
        setVertexShader(vertexShaderCode);
        setFragmentShader(fragmentShaderCode);
        setProg();
        setVertexBuffer();
        setDrawListBuffer();
        setTexCoordBuffer();
        setCompletback(new int[][] {{1,5},{2,8}});
        setCompletback(MazeGenerater.generate(2,2));
        //Matrix.translateM(BGMove,0,  blocksize *completback.length*2.5f, blocksize*completback[0].length*2.5f,0);
        Matrix.translateM(BGMove,0,  -1.5f, 1.5f,0);
        //Log.e("eredménykimutatás","eredméyn : "+blocksize*completback[0].length*2.5f);

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
        vissza.setTextureID(textura.get(id));
        return vissza;

    }
    public void draw(float[]moveMatrix){
        GLES20.glUseProgram(Prog);
        setPositionHandle();
        setTextCord();
        //  setColorHandle();
        for (BGBlock[] bc : BG) {
            for (BGBlock bg : bc) {
                Matrix.multiplyMM(foo, 0, BGMove, 0, bg.getmatrix(), 0);
                Matrix.multiplyMM(FinalMatrix, 0, moveMatrix, 0, foo, 0);
                setvPMatrixHandle(FinalMatrix);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bg.getTextureID());
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
            }
        }
      //  MyGLRenderer.whereareyou(FinalMatrix,squareCoords);
        setoffHandels();
    }
}
