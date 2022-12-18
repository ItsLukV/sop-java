package Game.Buttons;

import Game.Game;
import Game.GameState;

public class StartButton extends Button {
    public StartButton(int x, int y, int w, int h, String txt){
        super(x,y,w,h,txt);
    }

    @Override
    public void clicked(Game game) {
        if (bounds.contains(game.mouseX, game.mouseY)) {
            game.golEngine.start();
            game.gameState = GameState.Playing;
        }
    }


}
