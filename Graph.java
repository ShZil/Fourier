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

    public Graph mult(double factor) {
        GraphBuilder gb = new GraphBuilder();
        for (int i = 0; i < this.points.length; i++) {
            gb.point(this.points[i][0], this.points[i][1] * factor);
        }
        return new Graph(gb.collapse(), null, this.color);
    }

    public Graph mult(Graph other) {
        GraphBuilder gb = new GraphBuilder();
        for (int i = 0; i < this.points.length; i++) {
            gb.point(this.points[i][0], this.points[i][1] * other.points[i][1]);
        }
        return new Graph(gb.collapse(), null, this.color);
    }

    public void act(Graph other, BiFunction<Double, Double, Double> action) {
        for (int i = 0; i < this.points.length; i++) {
            this.points[i][1] = action.apply(this.points[i][1], other.points[i][1]);
        }
    }

    public int getX(int i) {
        return (int)(points[i][0] * scl + this.x);
    }

    public int getY(int i) {
        return (int)(points[i][1] * scl + this.y);
    }

    public double value(double x) {
        if (x < minx || x > maxx) return 0.0;
        double left = maxx;
        int lefti = 0;
        double right = minx;
        int righti = points.length - 1;
        for (int i = 0; i < this.points.length; i++) {
            double px = points[i][0];
            if (left > px && px < x) {
                left = px;
                lefti = i;
            }
            if (right < px && px > x) {
                right = px;
                righti = i;
            }
        }
        double y0 = points[lefti][1];
        double y1 = points[righti][1];
        double t = (x - left) / (right - left);
        return (y0 * (1-t)) + (y1 * t);
    }

    private double f(double x) {
        return value(x);
    }

    private void maxes() {
        minx = 10000;
        miny = 10000;
        maxx = -10000;
        maxy = -10000;
        for (int i = 0; i < this.points.length; i++) {
            double x = points[i][0];
            double y = points[i][1];
            if (x < minx) minx = x;
            if (y < miny) miny = y;
            if (x > maxx) maxx = x;
            if (y > maxy) maxy = x;
        }
    }

    private double integral(double a, double b, double dx) {
        // returns integral[a,b] f(x) dx
        double sum = 0;
        for (double x = a; x <= b; x += dx) {
            sum += f(x) * dx;
        }
        return sum;
    }

    public double integral(double dx) {
        if (dx <= 0) throw new IllegalArgumentException("double dx cannot be smaller than or equal to 0");
        return integral(minx + dx, maxx - dx, dx);
    }
}
