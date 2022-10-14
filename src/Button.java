import processing.core.PApplet;

import java.awt.*;

public class Button {
    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private final Rectangle bounds;

    public Button(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bounds = new Rectangle(x,y,w,h);
    }

    public void draw(PApplet g) {
        g.rect(x,y,w,h);
    }

    public void clicked(PApplet g,Runnable e) {
        if (bounds.contains(g.mouseX, g.mouseY)) {
            e.run();
        }
    }
}
