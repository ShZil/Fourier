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

    // 03.08.2021 21:00 - IDK WHY BUT I CANNOT FIND THE MUTATION AND APPROX PROCESS!!!
    // So... i guess it's gone or in another project

    // 04.08.2021 01:00 - Heyo I just thought, I could easily do reverse Fourier Transform (FT) by just summing sines.
    // So I added a `double Graph.value(double x)`, which lerps closest points and gives `y` for input `x`.
    // Future self, please do implement the reverse FT, should be ez. If you can do FT as well, gl!

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
    Graph[] graphs;
    String[] titles;
    Color[] colors;
    Checkbox[] checkboxes;
    int clicks = 0;

    public Main() {
        this.graphs = new Graph[5];
        this.titles = new String[this.graphs.length];
        this.colors = new Color[this.graphs.length];
        this.checkboxes = new Checkbox[this.graphs.length];
        this.size = new Dimension(Main.sizeOfWindow, Main.sizeOfWindow);
        init(this.graphs, this.colors, this.titles);
        for (int i = 0; i < titles.length; i++) {
            checkboxes[i] = new Checkbox(5 + i * Main.sizeOfWindow / this.titles.length, (i % 2 == 0) ? 60 : 20, this.colors[i]);
        }
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
        ((Graphics2D)g).scale(1, -1);
        ((Graphics2D)g).translate(0.0, -1000.0);
        for (int i = 0; i < this.graphs.length; i++) {
            Graph graph = this.graphs[i];
            if (graph != null && checkboxes[i].isOn()) graph.paint(g, true);
        }
        ((Graphics2D)g).translate(0.0, 1000.0);
        ((Graphics2D)g).scale(1, -1);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        for (int i = 0; i < this.titles.length; i++) {
            checkboxes[i].paint(g);
            g.drawString(titles[i], 30 + i * Main.sizeOfWindow / titles.length, (i % 2 == 0) ? 70 : 30);
        }
    }

    public void init(Graph[] graphs, Color[] colors, String[] titles) {
        colors[0] = new Color(0f, 1f, 0f);
        titles[0] = "sin(10x) + sin(4x) + sin(x)";
        graphs[0] = new Sine(1, 0, colors[0], this, 410);

        colors[1] = new Color(0f, 0f, 1f);
        titles[1] = "sin(10x)";
        graphs[1] = new Sine(10, clicks / 2.0, colors[1], this, true);

        colors[2] = new Color(1f, 0f, 0f);
        titles[2] = "2sin(0.5x)";
        graphs[2] = new Sine(0.5, 0, colors[2], this, true);
        graphs[2] = graphs[2].mult(2.0);

        colors[3] = new Color(0.8f, 0.8f, 0.8f);
        titles[3] = "sin(4x)";
        graphs[3] = new Sine(4, 0, colors[3], this, true);

        colors[4] = new Color(255, 255, 0);
        titles[4] = "FT";
        // graphs[4] = new Linear(-0.5 + clicks / 200.0, 0.3, colors[4], this);

        // colors[5] = new Color(1f, 0f, 1f);
        // titles[5] = "0.1x² - x + 2";
        // graphs[5] = new Parabola(0.1, -1, +2, colors[5], this, 410);

        graphs[0].add(graphs[1]);
        graphs[0].add(graphs[2]);
        graphs[0].add(graphs[3]);
        
        graphs[4] = FT(graphs[0]);

        // System.out.println(graphs[1].integral(0.2));
    }

    public void mouseListener(int x, int y) {
        System.out.println("Mouse Clicked! (" + x + ", " + y + "); clicks=" + clicks);
        clicks++;
        init(this.graphs, this.colors, this.titles);
        for (int i = 0; i < this.checkboxes.length; i++) {
            if (this.checkboxes[i].clicked(x, y)) {
                break;
            }
        }
    }

    public Graph FT(Graph input) {
        //  f(s) = 1/π ∫cos(sx)dx ∫f(y)cos(sy)dy
        //       + 1/π ∫sin(sx)dx ∫f(y)sin(sy)dy
        double dx = 0.01;
        double dy = 0.01;
        GraphBuilder gb = new GraphBuilder();
        for (double freqency = 0.1; freqency < 15.0; freqency += 0.1) {
            Graph cosine = new Cosine(freqency, 0, Color.BLACK, this);
            Graph sine = new Sine(freqency, 0, Color.BLACK, this);
            double precos = cosine.integral(dx);
            double presin = sine.integral(dx);

            double postcos = input.mult(cosine).integral(dy);
            double postsin = input.mult(sine).integral(dy);

            gb.point(freqency, (precos * postcos + presin * postsin)/50);
        }
        return new Graph(gb.collapse(), this, new Color(255, 255, 0));
    }
}
