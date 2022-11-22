import java.util.Random;

public class newTiles {

    private final int[][] grid;

    public newTiles(){
        this.grid = new int[GOLEngine.boardSize][GOLEngine.boardSize];

        Random rand = new Random();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = rand.nextInt(2);
            }
        }
    }



    public int getStarterGrid(int x,int y) {
        return grid[x][y];
    }

    public int[][] getGrid() {
        return grid;
    }
}
