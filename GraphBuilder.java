import java.util.ArrayList;

public class GraphBuilder {
    ArrayList<double[]> points;
    public GraphBuilder() {
        points = new ArrayList<double[]>();
    }

    public void point(double x, double y) {
        points.add(new double[]{x, y});
    }

    public double[][] collapse() {
        return points.toArray(new double[0][]);
    }
}
