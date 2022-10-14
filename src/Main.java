import processing.core.PApplet;

public class Main extends PApplet {
    GOLEngine golEngine;
    GameState gameState;
    private Button StartPlayingBtn;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size(600,600);
    }

    public void setup() {
        golEngine = new GOLEngine();
        StartPlayingBtn = new Button(width / 2 - 50, height / 2 - 25, 100, 50);
//        frameRate(4);
        gameState = GameState.Menu;
    }

    public void draw() {
        switch (gameState) {
            case Menu -> {
                StartPlayingBtn.draw(this);
            }
            case Playing -> {
                background(0);
                golEngine.draw(this);
                golEngine.tick();
            }
        }
    }

    public void mouseClicked() {
        StartPlayingBtn.clicked(this,()-> {gameState = GameState.Playing;});
    }
}

