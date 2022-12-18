package Game.Buttons;

import Game.Game;
import Game.GameState;

public class RestartButton extends Button{
    public RestartButton(int x, int y,int w, int h, String txt) {
        super(x,y,w,h,txt);
    }

    public void clicked(Game game) {
        if (bounds.contains(game.mouseX, game.mouseY)) {
            game.golEngine.restart();
        }
    }
}
