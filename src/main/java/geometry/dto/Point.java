package geometry.dto;

public class Point {
  private final double x;
  private final double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double x() {
    return x;
  }

  public double y() {
    return y;
  }

  @Override
  public String toString() {
    return String.format("Point{x=%.1f, y=%.1f}", x, y);
  }

  public static Point point(double x, double y) {
    return new Point(x, y);
  }
}
