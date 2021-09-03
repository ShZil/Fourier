import java.awt.Color;

public class Parabola extends Graph {
    // y = axx + bx + c
    public Parabola(double a, double b, double c, Color color, Main main) {
        super(Parabola.points(a, b, c), main, color);
    }

    public Parabola(double a, double b, double c, Color color, Main main, int off) {
        super(Parabola.points(a, b, c), main, color, off);
    }

    private static double calc(double x, double a, double b, double c) {
        return a * x * x + b * x + c;
    }

    public static double[][] points(double a, double b, double c) {
        double[][] data = new double[Main.width][2];

        for (int i = 0; i < Main.width; i++) {
            double x = i * Main.jump;
            data[i][0] = x;
            data[i][1] = calc(x, a, b, c);
        }

        return data;
    }
}
