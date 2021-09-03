import java.awt.*;
import java.util.function.BiFunction;

public class Graph {
    double[][] points;
    int thickness = 5;
    double[] params;
    int x = 10;
    int y = 250;
    int scl = 60;
    boolean dashed = false;
    double maxx;
    double maxy;
    double minx;
    double miny;

    private Color color;

    public Graph(double[][] data, Main m, Color c) {
        this.points = data;
        this.color = c;
        maxes();
    }

    public Graph(double[][] data, Main m, Color c, boolean dashed) {
        this.points = data;
        this.color = c;
        this.dashed = dashed;
        maxes();
    }

    public Graph(double[][] data, Main m, Color c, int yoff, boolean dashed) {
        this.points = data;
        this.color = c;
        this.y += yoff;
        this.dashed = dashed;
        maxes();
    }

    public Graph(double[][] data, Main m, Color c, int yoff) {
        this.points = data;
        this.color = c;
        this.y += yoff;
        maxes();
    }

    public void paint(Graphics g) {
        paintAxis(g);
        g.setColor(this.color);
        for (int i = 0; i < points.length; i++) {
            g.fillRect(getX(i), getY(i), thickness, thickness);
        }
    }

    public void paint(Graphics g, boolean connect) {
        if (!connect) {
            paint(g);
        } else {
            paintAxis(g);
            if (this.dashed) {
                ((Graphics2D)g).setStroke(new BasicStroke(thickness / 2.0f));
            } else {
                ((Graphics2D)g).setStroke(new BasicStroke(thickness));
            }
            g.setColor(this.color);
            for (int i = 0; i < points.length; i++) {
                // g.fillRect(getX(i), getY(i), thickness, thickness);
                if (i > 0) {
                    if (this.dashed) {
                        if (i % 3 == 0) {
                            g.drawLine(getX(i-1), getY(i-1), getX(i), getY(i));
                        }
                    } else {
                        g.drawLine(getX(i-1), getY(i-1), getX(i), getY(i));
                    }
                    // g.drawLine(getX(i-1) + thickness, getY(i-1) + thickness, getX(i) + thickness, getY(i) + thickness);
                }
            }
        }
    }

    private void paintAxis(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 1000, 3);
        g.fillRect(x, y - 200, 3, 400);
    }

    public void add(Graph other) {
        for (int i = 0; i < this.points.length; i++) {
            this.points[i][1] += other.points[i][1];
        }
    }

    public void mult(double factor) {
        for (int i = 0; i < this.points.length; i++) {
            this.points[i][1] *= factor;
        }
    }

    }

    public int getX(int i) {
        return (int)(points[i][0] * scl + this.x);
    }

    public int getY(int i) {
        return (int)(points[i][1] * scl + this.y);
    }

}
