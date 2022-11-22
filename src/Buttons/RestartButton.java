package Buttons;

import processing.core.PApplet;

public class RestartButton extends Button{
    public RestartButton(int x, int y,int w, int h, String txt) {
        super(x,y,w,h,txt);
    }

    @Override
    public void click(PApplet g) {}

    @Override
    public boolean clicked(PApplet g) {
        return false;
    }
}
