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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Point)) return false;

    Point point = (Point) o;

    if (Double.compare(point.x, x) != 0) return false;
    return Double.compare(point.y, y) == 0;

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(x);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
