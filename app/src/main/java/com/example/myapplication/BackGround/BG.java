package com.example.myapplication.BackGround;

import android.opengl.GLES20;
import android.util.Log;

import com.example.myapplication.SuperClasses.Character;
import com.example.myapplication.HitBoxes.BoundingBox;
import com.example.myapplication.SuperClasses.Drawable;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.R;
import com.example.myapplication.Enemys.Spawners.Spawner;
import com.example.myapplication.SuperClasses.Specifications;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;

public class BG extends Drawable {
    private final ArrayList<Integer[]> Movementpoints;
    private final ArrayList<Room> rooms;
    private ArrayList<BGBlock> points;
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

    public Tiles[][] completback;
    public BGBlock[][]  BG;
    private float loadingDistance;
    private Graph<Specifications, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private int sizeUp;
    public BG(Maze maze, int lenght, int hight) {
        setVertexShader(vertexShaderCode);
        setFragmentShader(fragmentShaderCode);
        setProg();
        setVertexBuffer();
        setDrawListBuffer();
        setTexCoordBuffer();

        setCompletback(maze.generate(lenght,hight));
        Movementpoints = maze.getMovementpoints();
        rooms = maze.getRooms();
        this.sizeUp = maze.getSize_up();
        LoadUpBG();
        Game.setMove(getboxmidel(new int[]{0,0}));

        int f = 0;
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < hight; j++) {
                rooms.get(f).setCourners(sizeUp,BG[0][0]).setMatrix(BG[i*sizeUp][j*sizeUp].getOwnPositionM());
                for (int k = i*sizeUp; k < (i*sizeUp)+sizeUp; k++) {
                    for (int l = j*sizeUp; l <(j*sizeUp)+sizeUp ; l++) {
                        rooms.get(f).setBlocks(BG[k][l]);
                    }
                }
                f++;
            }
        }
        Game.setMove(getboxmidel(maze.startingpoint));
        LoadUpGraph();
        float[] atmeneti= rooms.get(0).getRandomFloorBlock().getOwnPositionM();
        Log.e("BG",  Arrays.toString(atmeneti)+" "+rooms.get(0).getRandomFloorBlock().getHeight());
        //Game.addInvisible_pooints(new Triangle(atmeneti).setColor(new float[]{0.0f,1.0f,0.0f,1.0f}));
        rooms.get(0).addSpawners(new Spawner(R.drawable.spawing_fire_animation,64,64).setPosition(atmeneti));
        //rooms.forEach(i-> i.setSpawners(new Spawner(R.drawable.spawing_fire_animation,64,64).setPosition(i.getRandomFloorBlock())));
        //Spawner valmai = new Spawner(R.drawable.spawing_fire_animation,64,64);
    }
    public void setCompletback(Tiles[][] completback) {
        this.BG = new BGBlock[completback.length][completback[0].length];
        this.completback = completback;
    }

    private void LoadUpBG() {
        int a = completback.length;
        for (int i=0; i<completback.length;i++){
            for(int j=0;j< completback[0].length; j++){
                BG[i][j] = setTexture(completback[i][j],i,j);
            }
        }
    }
    private BGBlock setTexture(Tiles tiles, int i, int j ) {
        BGBlock vissza = new BGBlock();
        vissza.setMatrix( j*vissza.getHeight(),i* vissza.getHeight()*-1);
        vissza.setTexture(tiles);
        return vissza;

    }
    public void draw(float[]moveMatrix){

        GLES20.glUseProgram(Prog);
        setPositionHandle();
        setTextCord();
        for (BGBlock[] bc : BG) {
            for (BGBlock bgb : bc) {
                System.arraycopy(bgb.getOwnPositionM(),0, ownPositionM,0, ownPositionM.length);
                setvPMatrixHandle(moveMatrix);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bgb.getTexture());
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
            }
        }
        setoffHandels();
        for (Room room : rooms) {
        room.drawSpawner(moveMatrix);
        }

    }
    public BGBlock[] loadChunks(){
        ArrayList<BGBlock> near = new ArrayList<>();

        for (Room room : rooms) {
            BoundingBox valami = new BoundingBox(room);
            BoundingBox player = new BoundingBox(Game.getInstance().getPlayer());
            boolean elso = valami.intersects(player);
            boolean masodik = valami.contains(player);
            if ( elso || masodik){
                near.addAll(room.getWalls());
            }
        }
        /*int valami =(int)Math.ceil((float) sizeUp/2);
        for (Integer[] a : Lodingpoints) {
            float b = BG[a[0]][a[1]].distance(Game.getInstance().getPlayer());
            //Log.e("distance","index"+a[0]+","+a[1]+"  distance:  "+b);
            if(b < loadingDistance){
                for (int i = a[0]-valami; i < a[0]+valami; i++) {
                    for (int j = a[1]-valami; j < a[1]+valami; j++) {
                        if (i<=BG.length-1 && i>=0){
                            if (j<=BG[0].length-1 && j>=0){
                                if (BG[i][j].isHitable()){
                                    near.add(BG[i][j]);
                                    //BG[i][j].setTexture(Tiles.Black_Back_Ground);
                                }
                            }
                        }
                    }
                }
            }
        }*/
        return near.toArray(new BGBlock[0]);
    }
    private void LoadUpGraph(){
        ArrayList<BoundingBox> hitField = new ArrayList<>();
        points = new ArrayList<>();
        int local =0;
        for (Integer[] movementPoint : Movementpoints) {
            points.add(BG[movementPoint[0]][movementPoint[1]]);
            graph.addVertex(points.get(local++));
        }
        for (BGBlock[] bgBlocks : BG) {
            for (BGBlock bgBlock : bgBlocks) {
                if (bgBlock.isHitable()){
                    hitField.add(new BoundingBox(bgBlock));
                }
            }
        }
        //  Log.e("hitfield","hitfield size :"+hitField.size());
        for (int i = 0; i < points.size(); i++) {
            for (int j = i+1; j < points.size(); j++) {
                for (BoundingBox boundingBox : hitField) {
                    if (boundingBox.doesLineIntersect(points.get(i),points.get(j)))break;
                    else{
                        graph.addEdge(points.get(i),points.get(j));
                        graph.setEdgeWeight(points.get(i),points.get(j),points.get(i).distance(points.get(j)));
                    }
                }
            }
        }
    }
    public float[] getboxmidel(int[] xy){
        return BG[(xy[0]*sizeUp)+(int)Math.floor((float)sizeUp/2)][(xy[1]*sizeUp)+(int)Math.floor((float)sizeUp/2)].getOwnPositionM();
    }
    public BGBlock nearestMovmentPoint(Character character){
        float min = Float.MAX_VALUE;
        int i=0;
        BGBlock save = new BGBlock() ;
        for (BGBlock point : points) {
            i++ ;
            if (point.distance(character)<min){
                save = point;
                min= point.distance(character);
            }
        }
        return save;
    }

    @Override
    public String getName() {
        return "BG";
    }
    public ArrayList<BGBlock> getMovementpoints() {
        ArrayList<BGBlock> valami= new ArrayList<>();
        for (Integer[] a : Movementpoints) {
            valami.add(BG[a[0]][a[1]]);
        }
        return valami;
    }
    public void spawn(){
        for (Room room : rooms) {
            room.spawners.forEach(Spawner::spawn);
        }
    }
    public Graph<Specifications, DefaultWeightedEdge> getGraph() {
        return graph;
    }
}
