import java.awt.Color;

public class Linear extends Graph {
    // y = mx + b
    public Linear(double m, double b, Color c, Main main) {
        super(Linear.points(m, b), main, c);
    }

    public Linear(double m, double b, Color c, Main main, int off) {
        super(Linear.points(m, b), main, c, off);
    }

    private static double calc(double x, double m, double b) {
        return m * x + b;
    }

    public static double[][] points(double t, double s) {
        double[][] data = new double[Main.width][2];

        for (int i = 0; i < Main.width; i++) {
            double x = i * Main.jump;
            data[i][0] = x;
            data[i][1] = calc(x, t, s);
        }

        return data;
    }
}
