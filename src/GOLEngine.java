import processing.core.PApplet;

import java.util.Arrays;
import java.util.Random;

public class GOLEngine {
    int[][] grid;
    Random rand;
    int boardSize = 100;
    public GOLEngine() {
        this.grid = new int[boardSize][boardSize];
//        this.grid[50][50] = 1;
//        this.grid[50][51] = 1;
//        this.grid[49][51] = 1;
//        this.grid[49][50] = 1;
//        this.grid[49][49] = 1;

         rand = new Random();
         for(int i = 0; i < grid.length; i++) {
             for(int j = 0; j < grid[i].length; j++) {
                 this.grid[i][j] = rand.nextInt(2);
             }
         }
    }

    public void draw(PApplet g) {
        int ratio = g.width/100;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 1)
                g.rect(i * ratio,j * ratio, ratio, ratio);
            }
        }

    }

    public void tick() {
        int [][] gridFuture = new int[boardSize][boardSize];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                int Neigbor = Neighbors(i,j);
                // Underpopulation
                    if(grid[i][j] == 1 && Neigbor < 2) {

                    gridFuture[i][j] = 0;
                }

                // overpopulation
                else if(grid[i][j] == 1 && Neigbor > 3) {
                    gridFuture[i][j] = 0;
                }
                // perfect population
                else if(grid[i][j] == 0 && Neigbor == 3) {
                    gridFuture[i][j] = 1;
                }
                else {
                    gridFuture[i][j] = grid[i][j];
                }
            }
        }
        for(int i = 0; i< grid.length; i++){
            System.arraycopy(gridFuture[i], 0, grid[i], 0, gridFuture[i].length);
        }
    }

    private int Neighbors(int x, int y){
        int sum = 0;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if( x + i >= 0 && x + i < boardSize && y + j >= 0 && y + j < boardSize) {
                    sum += grid[(x + i + boardSize) % boardSize][
                            (y + j + boardSize) % boardSize
                            ];
                }
            }
        }
        return sum - grid[x][y];
    }

    public int[][] getGrid() {
        return grid;
    }
}
