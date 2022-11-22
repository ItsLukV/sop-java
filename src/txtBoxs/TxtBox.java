package txtBoxs;

import processing.core.PGraphics;

public class TxtBox {
    private String txt;
    private final int x;
    private final int y;
    private final int h;
    private final int w;

    public TxtBox(String txt, int x, int y, int w, int h) {
        this.txt = txt;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void updateTxt(String txt) {
        this.txt = txt;
    }

    public void show(PGraphics g) {
        int margin = 20;
        int txtSize = 15;

        g.push();

        g.rect(x - margin / 2, y - margin / 2, w + margin, h + margin);

        g.fill(0);

        g.textAlign(g.CENTER, g.CENTER);
        g.textSize(txtSize);
        g.text(txt, x, y, w, h);

        g.pop();
    }
}
