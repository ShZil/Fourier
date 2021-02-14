/*
* GraphApprox
* by ShZil
* -- Methods used:
* ---- Directional determained cost-proportional mutation.
* ---- Render by evaluating points in jumps.
* ---- Cost evaluation by sum of absolute differences.
* ---- Function evaluation by Quadratic Parabola.
*/

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.text.MessageFormat;

class Main extends Canvas {
  // This code mutates every parameter to minimize the cost,
  // but doesn't think about parameters affecting each other.

  /* START of User-readable and changable code. */
  public static final int sizeOfWindow = 1000;
  public static final double[] inputGraphColor = new double[]{0f, 1f, 0f};
  public static final double[] outputGraphColor = new double[]{1f, 0f, 0f};
  public static final Color axisColor = Color.white;
  public static final Color bgColor = Color.BLACK;
  public static final int width = 500;
  public static final double jump = 0.02;
  /* END of User-readable and changable code. */

  Dimension size;
  Graph graph;
  Graph first;
  Graph second;
  Graph feq;

  public Main() {
    this.size = new Dimension(Main.sizeOfWindow, Main.sizeOfWindow);
    this.graph = new Sin(1, 1, new Color(0f, 1f, 0f), this);
    this.first = new Sin(10, 1, new Color(0f, 0f, 1f), this);
    this.second = new Sin(4, 1, new Color(1f, 0f, 0f), this);
    this.graph.add(first);
    this.graph.add(second);
    this.feq = new LowGraph(this.graph, this, new Color(1f, 1f, 0f));
  }

  public static void main(String[] args) {
    Main m = new Main();
    JFrame f = new JFrame("Fourier by ShZil");
    f.getContentPane().setBackground(Main.bgColor);
    f.add(m);
    f.setSize(new Dimension(m.size.width, m.size.height));
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    m.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        m.mouseListener(e.getX(), e.getY());
        m.repaint();
      }
    });
  }

  public void paint(Graphics g) {
    paintAxis(g);
    first.paint(g, false);
    second.paint(g, false);
    graph.paint(g, false);
    feq.paint(g, false);
  }

  public void mouseListener(int x, int y) {
    System.out.println("Mouse Clicked! (" + x + ", " + y + ")");
  }

  private void paintAxis(Graphics g) {
    g.setColor(axisColor);
    Graph x = new Graph(new double[][]{
      {-1, 0},
      {0, 0},
      {1, 0},
      {2, 0},
      {3, 0},
      {4, 0},
      {5, 0},
      {6, 0},
      {7, 0},
      {8, 0},
      {9, 0},
      {10, 0}
    }, this, axisColor);
    Graph y = new Graph(new double[][]{
      {0, -10},
      {0, -9},
      {0, -8},
      {0, -7},
      {0, -6},
      {0, -5},
      {0, -4},
      {0, -3},
      {0, -2},
      {0, -1},
      {0, 0},
      {0, 1},
      {0, 2},
      {0, 3},
      {0, 4},
      {0, 5},
      {0, 6},
      {0, 7},
      {0, 8},
      {0, 9},
      {0, 10}
    }, this, axisColor);
    x.paint(g, true);
    y.paint(g, true);
  }
}
