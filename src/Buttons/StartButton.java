package Buttons;

import Buttons.Button;
import processing.core.PApplet;

public class StartButton extends Button {
    public StartButton(int x, int y, int w, int h, String txt){
        super(x,y,w,h,txt);
    }

    @Override
    public void click(PApplet g) {

    }

    @Override
    public boolean clicked(PApplet g) {
        if (bounds.contains(g.mouseX, g.mouseY)) {
            return true;
        }
        return false;
    }
}
