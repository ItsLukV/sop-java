import Buttons.Button;
import Buttons.StartButton;
import processing.core.PApplet;
import txtBoxs.TxtBox;

public class Main extends PApplet {
    GOLEngine golEngine;
    GameState gameState;
    private TxtBox showTurn;
    private TxtBox showCellsCount;

    private Button StartPlayingBtn;
    public static final int SCREEN_SIZE = 800;

    public static void main(String[] args) {
        PApplet.main("Main");
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
        if (StartPlayingBtn.clicked(this)) {
            golEngine.restart();
            gameState = GameState.Playing;
            return;
        }
        if (gameState == GameState.Playing) {
            golEngine.clicked(this);
        }
    }

    public void keyPressed() {
        golEngine.keyPressed(this, keyCode);
    }
}

