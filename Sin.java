import java.awt.Color;

public class Sin extends Graph {
  public Sin(double t, double s, Color c, Main m) {
    super(Sin.points(t, s), m, c);
  }

  private static double calc(double x, double t, double s) {
    return Math.sin(x * t) * s;
  }

  public static double[][] points(double t, double s) {
    double[][] data = new double[Main.width][2];

    for (int i = 0; i < Main.width; i++) {
      System.out.println("I="+i);
      double x = i * Main.jump;
      System.out.println("X="+x);
      data[i][0] = x;
      data[i][1] = calc(x, t, s);
      System.out.println("Y="+data[i][1]);
    }

    return data;
  }
}
