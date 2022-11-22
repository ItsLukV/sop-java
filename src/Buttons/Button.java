package Buttons;

import processing.core.PApplet;

import java.awt.*;

public abstract class Button {
    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private int txtSize = 12;
    protected final Rectangle bounds;
    private final String txt;

    public Button(int x, int y, int w, int h, String txt) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.txt = txt;
        this.bounds = new Rectangle(x,y,w,h);
    }

    public void draw(PApplet g) {
        g.push();
        if(bounds.contains(g.mouseX,g.mouseY)) {
            g.fill(220);
        } else {
            g.fill(255);
        }
        g.rect(x,y,w,h);
        g.fill(0);
        g.textAlign(g.CENTER, g.CENTER);
        g.textSize(txtSize);
        g.text(txt,x,y,w,h);
        g.pop();
    }
    public abstract void click(PApplet g);
    public abstract boolean clicked(PApplet g);
    // Getter and Setter
    public void setTxtSize(int txtSize) {
        this.txtSize =txtSize;
    }
}
