package com.example.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MazeGenerater {
   static int[][] maze;
   static boolean[][] bitmaze;
   static Random r = new Random();
   static List<int[]> unvisitedcell;

    public static int[][] generate(int lenght,int hight){

        unvisitedcell = new ArrayList<>();
        maze= new int[lenght][hight];
        bitmaze = new boolean[lenght][hight];
        bitmaze[r.nextInt(lenght)][r.nextInt(hight)] = true;
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
        int[][] finale = new int[lenght*5][hight*5];
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < hight; j++) {
                finale[i*5][j*5] =4;    finale[i*5][j*5+1] =2;      finale[i*5][j*5+2] =10;     finale[i*5][j*5+3] =11;     finale[i*5][j*5+4] =12;
                finale[i*5+1][j*5] =3;  finale[i*5+1][j*5+1] =16;   finale[i*5+1][j*5+2] =21;   finale[i*5+1][j*5+3] =20;   finale[i*5+1][j*5+4] =13;
                finale[i*5+2][j*5] =5;  finale[i*5+2][j*5+1] =18;   finale[i*5+2][j*5+2] =19;   finale[i*5+2][j*5+3] =22;   finale[i*5+2][j*5+4] =14;
                finale[i*5+3][j*5] =3;  finale[i*5+3][j*5+1] =15;   finale[i*5+3][j*5+2] =17;   finale[i*5+3][j*5+3] =23;   finale[i*5+3][j*5+4] =13;
                finale[i*5+4][j*5] =6;  finale[i*5+4][j*5+1] =7;    finale[i*5+4][j*5+2] =8;    finale[i*5+4][j*5+3] =7;    finale[i*5+4][j*5+4] =0;
            }
        }

       /* for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < hight; j++) {
                switch (maze[i][j]){
                    case 0: //fel
                        finale[i*5-2][j*5] =4;  finale[i*5-2][j*5+1] =18;    finale[i*5-2][j*5+2] =19;   finale[i*5-2][j*5+3] =22;   finale[i*5-2][j*5+4] =14;
                        finale[i*5-1][j*5] =3;  finale[i*5-1][j*5+1] =18;    finale[i*5-1][j*5+2] =19;   finale[i*5-1][j*5+3] =22;   finale[i*5-1][j*5+4] =13;
                        finale[i*5][j*5] =5;    finale[i*5][j*5+1] =18;      finale[i*5][j*5+2] =19;     finale[i*5][j*5+3] =22;     finale[i*5][j*5+4] =14;
                                                finale[i*5+1][j*5+1] =18;    finale[i*5+1][j*5+2] =19;   finale[i*5+1][j*5+3] =22;
                        break;
                    case 1://le
                                                finale[i*5+3][j*5+1] =18;    finale[i*5+3][j*5+2] =19;   finale[i*5+3][j*5+3] =22;
                        finale[i*5+4][j*5] =5;  finale[i*5+4][j*5+1] =18;    finale[i*5+4][j*5+2] =19;   finale[i*5+4][j*5+3] =22;   finale[i*5+4][j*5+4] =14;
                        finale[i*5+5][j*5] =3;  finale[i*5+5][j*5+1] =18;    finale[i*5+5][j*5+2] =19;   finale[i*5+5][j*5+3] =22;   finale[i*5+5][j*5+4] =13;
                        finale[i*5+6][j*5] =5;  finale[i*5+6][j*5+1] =18;    finale[i*5+6][j*5+2] =19;   finale[i*5+6][j*5+3] =22;   finale[i*5+6][j*5+4] =14;
                        break;
                    case 2: //jobbra
                                                    finale[i*5][j*5+4] =2;      finale[i*5][j*5+5] =11;     finale[i*5][j*5+6] =2;
                        finale[i*5+1][j*5+3] =21;   finale[i*5+1][j*5+4] =21;   finale[i*5+1][j*5+5] =21;   finale[i*5+1][j*5+6] =21;
                        finale[i*5+2][j*5+3] =19;   finale[i*5+2][j*5+4] =19;   finale[i*5+2][j*5+5] =19;   finale[i*5+2][j*5+6] =19;
                        finale[i*5+3][j*5+3] =17;   finale[i*5+3][j*5+4] =17;   finale[i*5+3][j*5+5] =17;   finale[i*5+3][j*5+6] =17;
                                                    finale[i*5+4][j*5+4] =8;    finale[i*5+4][j*5+5] =7;    finale[i*5+4][j*5+6] =8;
                        break;
                    case 3://balra
                        finale[i*5][j*5-2] =11;    finale[i*5][j*5-1] =2;     finale[i*5][j*5] =11;
                        finale[i*5+1][j*5-2] =21;  finale[i*5+1][j*5-1] =21;  finale[i*5+1][j*5] =21;  finale[i*5+1][j*5+1] =21;
                        finale[i*5+2][j*5-2] =19;  finale[i*5+2][j*5-1] =19;  finale[i*5+2][j*5] =19;  finale[i*5+2][j*5+1] =19;
                        finale[i*5+3][j*5-2] =17;  finale[i*5+3][j*5-1] =17;  finale[i*5+3][j*5] =17;  finale[i*5+3][j*5+1] =17;
                        finale[i*5+4][j*5-2] =8;   finale[i*5+4][j*5-1] =7;   finale[i*5+4][j*5] =8;
                        break;
                }
            }
        }*/
        Log.println(Log.ERROR,"Bakos vagyok", Arrays.deepToString(maze));

        for (int[] a: finale) {
            Log.println(Log.ERROR,"final maze",Arrays.toString(a)+"\n");
        }

        //Log.println(Log.ERROR,"Bakos vagyok", Arrays.deepToString(finale));

        return finale;
    }
}

