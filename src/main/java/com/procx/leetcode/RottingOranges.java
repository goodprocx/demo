package com.procx.leetcode;

import java.util.ArrayList;

/**
 * In a given grid, each cell can have one of three values:
 *
 *     the value 0 representing an empty cell;
 *     the value 1 representing a fresh orange;
 *     the value 2 representing a rotten orange.
 *
 * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible, return -1 instead.
 *
 *
 * Input: [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 */
public class RottingOranges {

    int time = 0;

    /**
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        int row = grid.length;
        int column = grid[0].length;

        ArrayList<int[]> list2 = new ArrayList<>();//记录2格子
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j < column; j++) {
                int value = grid[i][j];
                if(value==2){
                    list2.add(new int[]{i, j});
                }
            }
        }

        rotten(grid,list2);

        for (int i = 0; i <row ; i++) {
            for (int j = 0; j < column; j++) {
                int value = grid[i][j];
                if(value==1){
                    time = -1;
                }
            }
        }

        return time;
    }


    public void rotten(int[][] grid, ArrayList<int[]> rottenList){
        ArrayList<int[]> newRottenList = new ArrayList<>();
        for (int[] temp:rottenList) {
            //相邻的4个格
            int[] top = {-1,0};
            int[] down = {1,0};
            int[] left = {0,-1};
            int[] right = {0,1};

            int row = grid.length;
            int column = grid[0].length;
            int[] newTop = {temp[0]+top[0],temp[1]+top[1]};

            if(newTop[0]>=0 && grid[newTop[0]][newTop[1]]==1){
                grid[newTop[0]][newTop[1]] = 2;
                newRottenList.add(newTop);
            }

            int[] newDown = {temp[0]+down[0],temp[1]+down[1]};
            if(newDown[0]<row && grid[newDown[0]][newDown[1]]==1){
                grid[newDown[0]][newDown[1]] = 2;
                newRottenList.add(newDown);
            }

            int[] newLeft = {temp[0]+left[0],temp[1]+left[1]};
            if(newLeft[1]>=0 && grid[newLeft[0]][newLeft[1]]==1){
                grid[newLeft[0]][newLeft[1]] = 2;
                newRottenList.add(newLeft);
            }

            int[] newRight = {temp[0]+right[0],temp[1]+right[1]};
            if(newRight[1]<column && grid[newRight[0]][newRight[1]]==1){
                grid[newRight[0]][newRight[1]] = 2;
                newRottenList.add(newRight);
            }
        }
        if(newRottenList.size()>0){
            time++;
            rotten(grid,newRottenList);
        }

    }

    public static void main(String[] args) {
        System.out.println(new RottingOranges().orangesRotting(new int[][]{{2,1,1},{1,1,0},{0,1,1}}));
    }
}
