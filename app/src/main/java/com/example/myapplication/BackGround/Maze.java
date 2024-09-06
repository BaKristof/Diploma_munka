package com.example.myapplication.BackGround;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Maze {
   static int[][] maze;
   static boolean[][] bitmaze;
   static List<int[]> unvisitedcell;
   public int[] startingpoint;
   public int size_up;
   public ArrayList<Integer[]> Movementpoints = new ArrayList<>();
   public ArrayList<Room> rooms = new ArrayList<>();

    Tiles[][] finale;
    public Maze(int size_up) {
        this.size_up = size_up;
    }

    public void setMovementpoints(Integer[] movementpoints) {
        this.Movementpoints.add(movementpoints);
    }
    public Maze() {
        this.size_up = 5;
    }
    public Tiles[][] generate(int lenght, int hight){
        Random r = new Random();
        this.finale = new Tiles[lenght*size_up][hight*size_up];

        unvisitedcell = new ArrayList<>();
        maze= new int[lenght][hight];
        bitmaze = new boolean[lenght][hight];
        startingpoint = new int[]{r.nextInt(lenght),r.nextInt(hight)};
        bitmaze[startingpoint[0]][startingpoint[1]] = true;


        for (int i = 0; i < bitmaze.length; i++){
            for (int j = 0; j < bitmaze[0].length; j++){
                if(!bitmaze[i][j]){
                    unvisitedcell.add(new int[]{i,j});
                    maze[i][j]=99;
                }
                if (bitmaze[i][j]) maze[i][j]=9;
            }
        }

        int kezd=0;
        while (unvisitedcell.size()>0) {
            kezd =r.nextInt(unvisitedcell.size());
            int cellx = unvisitedcell.get(kezd)[0];
            int celly = unvisitedcell.get(kezd)[1];
            while (!bitmaze[cellx][celly]){
                int foo = r.nextInt(4);
                maze[cellx][celly] = foo;
                //0 =fel
                //1=le
                //2=jobb
                //3=bal
                if (foo == 0 && cellx -1 >= 0)                  cellx-- ;
                if (foo == 1 && cellx < maze.length-1)          cellx++;
                if (foo == 2 && celly < maze[0].length-1)       celly++;
                if (foo == 3 && celly -1 >= 0)                  celly--;
            }
            cellx = unvisitedcell.get(kezd)[0];
            celly = unvisitedcell.get(kezd)[1];
            while (!bitmaze[cellx][celly]){
                bitmaze[cellx][celly] = true;
                switch (maze[cellx][celly]){
                    case 0:cellx--; break;
                    case 1:cellx++; break;
                    case 2:celly++; break;
                    case 3:celly--; break;

                }
            }
            unvisitedcell.clear();
            for (int i = 0; i < bitmaze.length; i++){
                for (int j = 0; j < bitmaze[0].length; j++){
                    if(!bitmaze[i][j]){
                        unvisitedcell.add(new int[] {i,j});
                        maze[i][j]=0;
                    }
                }
            }

        }
        int mod =(int) Math.pow(size_up,2);
        int number =0;

        Room room = new Room(size_up);

        for (int i = 0; i < finale.length; i+=size_up) {
            for (int j = 0; j < finale[0].length; j+=size_up) {
                //roomfill(i,j);

                room.roomfill(finale,i,j);
                rooms.add(new Room(7));
            }

        }
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < hight; j++) {
                switch (maze[i][j]){
                    case 0: //fel
                        Room.insterVertical(finale,(i*size_up)-2,(j*size_up)+1,this,size_up);
                        break;
                    case 1://le
                        Room.insterVertical(finale,(i*size_up)+(size_up-2),(j*size_up)+1,this,size_up);
                        break;
                    case 2: //jobbra
                        Room.insterHorozontal(finale,(i*size_up)+1,(j*size_up)+(size_up-2),this,size_up);
                        break;
                    case 3://balra
                        Room.insterHorozontal(finale,(i*size_up)+1,(j*size_up)-2,this,size_up);
                        break;
                }
            }
        }
       // Log.println(Log.ERROR,"Bakos vagyok", Arrays.deepToString(maze));

        for (Tiles[] a: finale) {
            Log.println(Log.ERROR,"final maze",Arrays.toString(a)+"\n");
        }
        return finale;
    }

    public ArrayList<Integer[]> getMovementpoints() {
        return Movementpoints;
    }
    public int[] getStartingpoint() {
        return startingpoint;
    }
    private int getRandomNumberFromArray(int[] a){
        Random r = new Random();
        return a[r.nextInt(a.length)];
    }
    public int getSize_up() {
        return size_up;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}

