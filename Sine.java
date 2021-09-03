import java.awt.Color;

public class Sine extends Graph {
    // y = sin(tx + s)
    public Sine(double t, double s, Color c, Main m) {
        super(Sine.points(t, s), m, c);
    }

    public Sine(double t, double s, Color c, Main m, boolean dashed) {
        super(Sine.points(t, s), m, c, dashed);
    }

    private static double calc(double x, double t, double s) {
        return Math.sin(x * t + s);
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
