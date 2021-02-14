import java.awt.*;

public class Graph {
  double[][] points;
  int thickness = 7;
  double[] params;

  private Color color;

  public Graph(double[][] data, Main m, Color c) {
    this.points = data;
    this.color = c;
  }

  public void paint(Graphics g) {
    g.setColor(this.color);
    for (int i = 0; i < points.length; i++) {
      g.fillRect(getX(i), getY(i), thickness, thickness);
    }
  }

  public void paint(Graphics g, boolean connect) {
    if (!connect) {
      paint(g);
    } else {
      g.setColor(this.color);
      for (int i = 0; i < points.length; i++) {
        g.fillRect(getX(i), getY(i), thickness, thickness);
        if (i > 0) {
          g.drawLine(getX(i-1), getY(i-1), getX(i), getY(i));
          g.drawLine(getX(i-1) + thickness, getY(i-1) + thickness, getX(i) + thickness, getY(i) + thickness);
        }
      }
    }
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

  public int getX(int i) {
    return (int)(points[i][0] * 50);
  }

  public int getY(int i) {
    return (int)(points[i][1] * 50 + 225);
  }
}
