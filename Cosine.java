import java.awt.Color;

public class Cosine extends Graph {
    // y = cos(tx + s)
    public Cosine(double t, double s, Color c, Main m) {
        super(Cosine.points(t, s), m, c);
    }

    public Cosine(double t, double s, Color c, Main m, boolean dashed) {
        super(Cosine.points(t, s), m, c, dashed);
    }

    public Cosine(double t, double s, Color c, Main m, int off) {
        super(Cosine.points(t, s), m, c, off);
    }

    private static double calc(double x, double t, double s) {
        return Math.cos(x * t + s);
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
