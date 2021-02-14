import java.awt.*;

public class LowGraph extends Graph {
  public LowGraph(Graph to, Main m, Color c) {
    super(transform(to), m, c);
  }

  public static double[][] transfom(Graph t) {

  }

  public int getY(int i) {
    return (int)(points[i][1] * 50 + 675);
  }
}
