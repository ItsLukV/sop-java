package Game;

import Game.Buttons.RestartButton;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Game.GOLEngine
 */
public class GOLEngine {
    private final newTiles hand;
    int[][] grid;
    public static final int maxTurn = 100;
    private int turn = 0;
    public static final int newStage = 10;
    public static int boardSize = 200;
    private PlayingState playingState = PlayingState.start;
    private final int tileSize = Game.SCREEN_SIZE / boardSize;
    private final RestartButton restartButton;
    private int stage;

    /**
     * Constructor for Game Of Life Engine
     * @param g
     */
    public GOLEngine(PGraphics g) {
        this.grid = new int[boardSize][boardSize];
        this.hand = new newTiles();
        clearGrid();
        restartButton = new RestartButton(g.width / 2 - 100 / 2, g.height / 2 - 50 / 2, 100, 50, "Restart");
    }

    public void start() {
        clearGrid();
        turn = 0;
        stage = 0;
        playingState = PlayingState.start;
    }

    /**
     * Show the next 10x10 grid of cells
     * @param g Processing stuff
     * @param x Mouse X pos
     * @param y Mouse Y pos
     */
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

    /**
     * Draws the game
     * @param g
     */
    public void draw(PApplet g) {

        switch (playingState) {
            case newStage, evolving -> {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j] == 1) g.rect(i * tileSize, j * tileSize, tileSize, tileSize);
                    }
                }
            }
            case finished -> {
                restartButton.draw(g);
            }
        }


    }

    /**
     * Converts screen coordinate to grid array index
     * @param e screen coordinate
     * @return grid array index
     */
    private int getTilePos(int e) {
        return (int) Math.floor((float) e / tileSize);
    }

    /**
     * Makes a new tick in the Game.GOLEngine
     * @param g
     */
    public void tick(PApplet g) {

        switch (playingState) {
            case start -> showHand(g, g.mouseX, g.mouseY);
            case evolving -> {
                if (gameFinished()) {
                    playingState = PlayingState.finished;
                    return;
                }
                ;

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
                g.frameRate(60);
            }
        }
    }

    /**
     * Restarts the Game.GOLEngine
     */
    public void restart() {
        clearGrid();
        turn = 0;
        stage = 0;
        playingState = PlayingState.start;
    }

    /**
     * Checks if a newStage has begun
     * @return true or false
     */
    private boolean checkTurn() {
        return turn % newStage != 0;
    }

    /**
     * Checks if game finished
     * @return
     */
    private boolean gameFinished() {
        return maxTurn <= turn;
    }

    /**
     * @return the number of alive cells
     */
    public int getAliveCells() {
        int e = 0;
        for (int[] i : grid) {
            for (int j : i) {
                e += j;
            }
        }
        return e;
    }

    /**
     * Begins the next generation
     */
    private void nextGen() {
        // Initializes gridfuture
        int[][] gridFuture = new int[boardSize][boardSize];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                // Gets the neighbors of the slot
                int Neighbor = Neighbors(i, j);

                // Underpopulation
                if (grid[i][j] == 1 && Neighbor < 2) {
                    gridFuture[i][j] = 0;
                }
                // overpopulation
                else if (grid[i][j] == 1 && Neighbor > 3) {
                    gridFuture[i][j] = 0;
                }
                // Birth
                else if (grid[i][j] == 0 && Neighbor == 3) {
                    gridFuture[i][j] = 1;

                    // Perfect population or dead cell with no neigbors
                } else {
                    gridFuture[i][j] = grid[i][j];
                }
            }
        }

        // Copys the gridfuture to grid
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(gridFuture[i], 0, grid[i], 0, gridFuture[i].length);
        }
    }

    /**
     * Returns the amount of Neighbors around X and Y
     */
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

    /**
     * Makes all slots in the grid "dead"
     */
    public void clearGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = 0;
            }
        }
    }

    /**
     * Canges the PlayingState of the GOLEnigne
     */
    public void changeState(PlayingState playingState) {
        this.playingState = playingState;
    }

    /**
     * This makes the hand preview a part of the real grid
     */
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
    }

    /**
     * runs if the screen is pressed
     */
    public void clicked(Game g) {
        switch (playingState) {
            case start, newStage -> {
                confirmHand(g.mouseX, g.mouseY);
                playingState = PlayingState.evolving;
                g.frameRate(2);
            }
            case finished -> restartButton.clicked(g);
        }
    }

    /**
     * runs if a button other than the mouse is pressed
     */
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

    /**
     * Returns the amount of turn in the game
     */
    public int getTurn() {
        return turn;
    }
}
