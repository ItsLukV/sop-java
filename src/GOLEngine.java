import Buttons.RestartButton;
import processing.core.PApplet;
import processing.core.PGraphics;

public class GOLEngine {
    private final newTiles hand;
    int[][] grid;
    public static final int maxStage = 2;
    private int stage = 0;
    private int turn = 0;
    public static final int newStage = 10;
    public static int boardSize = 200;
    private PlayingState playingState = PlayingState.start;
    private final int tileSize = Main.SCREEN_SIZE / boardSize;
    private final RestartButton restartButton;

    public GOLEngine(PGraphics g) {
        this.grid = new int[boardSize][boardSize];
        this.hand = new newTiles();
        clearGrid();
        restartButton = new RestartButton(g.width / 2 - 100 / 2, g.height / 2 - 50 / 2, 100,50,"Restart");
    }

    private void showHand(PApplet g, int x, int y) {
        int tileHandX = getTilePos(x);
        int tileHandY = getTilePos(y);

        for (int i = -10; i < 11; i++) {
            for (int j = -10; j < 11; j++) {
                int tilePosX = tileHandX - i;
                int tilePosY = tileHandY - j;
                if (0 < tilePosX && 0 < tilePosY) {
                    if (grid.length > tilePosX && grid[tileHandX - i].length > tilePosY) {
                        if (hand.getStarterGrid(tileHandX - i, tileHandY - j) == 1) {
                            g.rect((tileHandX - i) * tileSize, (tileHandY - j) * tileSize, tileSize, tileSize);
                        }
                    }
                }

            }
        }

    }

    public void draw(PApplet g) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) g.rect(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }
    }

    private int getTilePos(int e) {
        return (int) Math.floor((float) e / tileSize);
    }

    public void tick(PApplet g) {

        switch (playingState) {
            case start -> showHand(g, g.mouseX, g.mouseY);
            case evolving -> {
                if (!gameFinished()) {
                    playingState = PlayingState.finished;
                    return;
                };

                System.out.printf("Turn: %d | Stage: %d\n", turn, stage);
                if (checkTurn()) {
                    turn += 1;
                    nextGen();
                } else {
                    playingState = PlayingState.newStage;
                }

            }
            case newStage -> {
                g.frameRate(60);
                showHand(g, g.mouseX, g.mouseY);
            }
            case finished -> {
                g.background(0);
                restartButton.draw(g);
                restart();
                System.out.println("Game finished");
            }
        }
    }

    private void restart() {
        turn = 0;
        stage = 0;
    }

    private boolean checkTurn() {
        return turn % newStage != 0;
    }

    private boolean gameFinished() {
        return maxStage >= stage;
    }

    public int getAliveCells() {
        int e = 0;
        for (int[] i : grid) {
            for (int j : i) {
                e += j;
            }
        }
        return e;
    }

    private void nextGen() {
        int[][] gridFuture = new int[boardSize][boardSize];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int Neighbor = Neighbors(i, j);
                // Underpopulation
                if (grid[i][j] == 1 && Neighbor < 2) {
                    gridFuture[i][j] = 0;
                }
                // overpopulation
                else if (grid[i][j] == 1 && Neighbor > 3) {
                    gridFuture[i][j] = 0;
                }
                // perfect population
                else if (grid[i][j] == 0 && Neighbor == 3) {
                    gridFuture[i][j] = 1;
                } else {
                    gridFuture[i][j] = grid[i][j];
                }
            }
        }
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(gridFuture[i], 0, grid[i], 0, gridFuture[i].length);
        }
    }

    private int Neighbors(int x, int y) {
        int sum = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i >= 0 && x + i < boardSize && y + j >= 0 && y + j < boardSize) {
                    sum += grid[(x + i + boardSize) % boardSize][(y + j + boardSize) % boardSize];
                }
            }
        }
        return sum - grid[x][y];
    }

    public void clearGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = 0;
            }
        }
    }

    public void changeState(PlayingState playingState) {
        this.playingState = playingState;
    }

    public int[][] getGrid() {
        return grid;
    }

    private void confirmHand(int x, int y) {
        int tileHandX = getTilePos(x);
        int tileHandY = getTilePos(y);

        for (int i = -10; i < 11; i++) {
            for (int j = -10; j < 11; j++) {
                int tilePosX = tileHandX - i;
                int tilePosY = tileHandY - j;
                if (0 < tilePosX && 0 < tilePosY) {
                    if (grid.length > tilePosX && grid[tileHandX - i].length > tilePosY) {
                        if (hand.getStarterGrid(tileHandX - i, tileHandY - j) == 1) {
                            grid[tileHandX - i][tileHandY - j] = hand.getStarterGrid(tileHandX - i, tileHandY - j);

                        }
                    }
                }

            }
        }

        turn++;
        stage++;
    }

    public void clicked(PApplet g) {
        switch (playingState) {
            case start, newStage -> {
                confirmHand(g.mouseX, g.mouseY);
                playingState = PlayingState.evolving;
                g.frameRate(2);
            }
            case finished -> {}
        }
    }

    public void keyPressed(PApplet g, int keyCode) {
        if (keyCode == 32) {
            if (playingState != PlayingState.evolving) {
                confirmHand(g.mouseX, g.mouseY);
                playingState = PlayingState.evolving;
                changeState(GOLEngine.PlayingState.evolving);
                g.frameRate(2);
            }
        }
    }

    public enum PlayingState {
        start, evolving, newStage, finished

    }

    public PlayingState getGameState() {
        return playingState;
    }

    public int getTurn() {
        return turn;
    }
}
