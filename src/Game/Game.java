package Game;

import Game.Buttons.Button;
import Game.Buttons.StartButton;
import processing.core.PApplet;
import Game.txtBoxs.TxtBox;

public class Game extends PApplet {
    public GOLEngine golEngine;
    public GameState gameState;
    private TxtBox showTurn;
    private TxtBox showCellsCount;

    private Button StartPlayingBtn;
    public static final int SCREEN_SIZE = 800;

    public static void main(String[] args) {
        PApplet.main("Game.Game");
    }

    public void settings() {
        size(SCREEN_SIZE, SCREEN_SIZE);
    }

    public void setup() {
        golEngine = new GOLEngine(g);
        StartPlayingBtn = new StartButton(width / 2 - 50, height / 2 - 25, 100, 50, "Start");
        StartPlayingBtn.setTxtSize(30);

        showTurn = new TxtBox("Turn: 0", 0, 0, 100, 50);
        showCellsCount = new TxtBox("0", width - 100, 0, 100, 50);
        frameRate(60);
        gameState = GameState.StartMenu;
    }

    public void draw() {
        switch (gameState) {
            case StartMenu -> StartPlayingBtn.draw(this);
            case Playing -> {
                background(0);

                golEngine.tick(this);
                golEngine.draw(this);

                if (golEngine.getGameState() == GOLEngine.PlayingState.finished) {
                    showCellsCount.updateTxt("Final Cell Count: " + str(golEngine.getAliveCells()));
                } else {
                    showCellsCount.updateTxt("Cell Count: " + str(golEngine.getAliveCells()));
                }
                showTurn.updateTxt("Turn: " + str(golEngine.getTurn()));

                showTurn.show(g);
                showCellsCount.show(g);
            }
        }
    }

    public void mouseClicked() {
        switch (gameState) {
            case StartMenu -> StartPlayingBtn.clicked(this);
            case Playing -> golEngine.clicked(this);
        }
    }

    public void keyPressed() {
        golEngine.keyPressed(this, keyCode);
    }
}

