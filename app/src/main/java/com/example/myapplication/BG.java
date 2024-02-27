package com.example.myapplication;

import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BG extends Drawable {
    private ArrayList<int[]> Movementpoints = new ArrayList<>();
    private ArrayList<int[]> Lodingpoints = new ArrayList<>();
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
    public BG( Maze maze) {

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
        setCompletback(maze.generate(2,2));
        Movementpoints = maze.getMovementpoints();
        Lodingpoints = maze.getLodingPoints();
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
        vissza.setMatrix( j* blocksize,i* blocksize*-1);
        vissza.setSingleTexture(texture.get(id));
        return vissza;

    }
    public void draw(float[]moveMatrix){

        GLES20.glUseProgram(Prog);
        setPositionHandle();
        setTextCord();
        //  setColorHandle();


        for (BGBlock[] bc : BG) {
            for (BGBlock bgb : bc) {
                System.arraycopy(bgb.getMatrix(),0, matrix,0, matrix.length);
                setvPMatrixHandle(moveMatrix);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bgb.getAnimation());
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
            }
        }
      //  MyGLRenderer.whereareyou(FinalMatrix,squareCoords);
        setoffHandels();
    }
    public BGBlock[] loadablechunks(Point point){
        ArrayList<BGBlock> near = new ArrayList<>();
        for (int[] a : Lodingpoints) {
            float b = MyGLRenderer.whereisyourmidle(BG[a[0]][a[1]]).distance(point);
            if(b < Specifications.blocksize*3.5f){
                for (int i = a[0]-4; i < a[0]+4; i++) {
                    for (int j = a[1]-4; j < a[1]+4; j++) {
                        if (i<=BG.length && i>=0){
                            if (j<=BG[0].length && j>=0){
                                if (!valami.contains(BG[i][j].getAnimation())){
                                    near.add(BG[i][j]);
                                }
                            }
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
    public void drawmovementpoints(float[] matrix){
        int i = 0;
        //new Triangle(BG[0][1].getMatrix()).draw(matrix);
        for (int[] a : Movementpoints) {
            new Triangle( BG[a[0]][a[1]].getMatrix()).draw(matrix);
            //Log.e("movement points "+i++, Arrays.toString(a));
        }
    }
    public void NearestMovmentPoint(EnemyCharacter enemy){
        //todo check if needed the nearest movement point or just go to the player
        Point newpoint = new Point(enemy);
        float min = Float.MAX_VALUE;
        Point save= new Point(0.0f,0.0f);
        for (int[] a : Movementpoints) {
            Point foo = new Point(BG[a[0]][a[1]]);
            if(newpoint.distance(foo)<min){
              save = foo;
              min = newpoint.distance(foo);
            }
        }
        enemy.setMovementPoint(save);
    }
}
