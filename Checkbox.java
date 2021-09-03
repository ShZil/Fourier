import java.awt.Graphics;
import java.awt.Color;

public class Checkbox {
    int x;
    int y;
    boolean on;
    Color color;
    static int r = 20;

    public Checkbox(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.on = true;
    }

    public boolean clicked(int x, int y) {
        if (x > this.x && x < this.x + r) {
            if (y > this.y && y < this.y + r) {
                this.on = !this.on;
                return true;
            }
        }
        return false;
    }

    public void paint(Graphics g) {
        g.setColor(this.color);
        g.drawArc(x, y, r, r, 0, 360);
        if (this.on) g.fillArc(x + r/4, y + r/4, r/2, r/2, 0, 360);
    }

    public boolean isOn() {
        return this.on;
    }
}
