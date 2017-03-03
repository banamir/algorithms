package geometry.dto;

public class Segment {
  private final Point start, end;

  public Segment(Point start, Point end) {
    this.start = start;
    this.end = end;
  }

  public Segment(double x1, double y1, double x2, double y2) {
    this.start = new Point(x1, y1);
    this.end = new Point(x2, y2);
  }

  public Point start() {
    return start;
  }

  public Point end() {
    return end;
  }

  public double yIntersect() {
    return (end.x() * start.y() - end.y() * start.x()) / (end.x() - start.x());
  }

  public double tg() {
    return Math.tan(atan());
  }

  public double atan() {
    return Math.atan2(end.x() - start.x(), end.y() - start.y());
  }

  public static boolean isIntersect(Segment s1, Segment s2) {
    double a = s1.tg();
    double b = s2.tg();
    double c = s1.yIntersect();
    double d = s2.yIntersect();

    if (Math.abs(a - b) < .000_001d) {
      if (Math.abs(c - d) > .000_001d) {
        return false;
      } else {
        return s1.betweenXs(s2.start().x()) || s1.betweenXs(s2.end().x());
      }
    }

    return true;
  }


  private boolean betweenXs(double x) {
    return minX() <= x && x <= maxX();
  }

  private double minX() {
    return Math.min(start.x(), end.x());
  }

  private double maxX() {
    return Math.max(start.x(), end.x());
  }

  public static Point intersect(Segment s1, Segment s2) {
    double a = s1.tg();
    double c = s1.yIntersect();

    double b = s2.tg();
    double d = s2.yIntersect();

    return new Point((d - c) / (a - b), (a * d - b * c) / (a - b));
  }

  public static void main(String[] args) {
    Segment s1 = new Segment(1, 1, 3, 3);
    Segment s2 = new Segment(1, 3, 3, 1);
    Point point = intersect(s1, s2);
    System.out.println(point);
  }

  public static Segment segment(Point start, Point end) {
    return new Segment(start, end);
  }

  public static Segment segment(double x1, double y1, double x2, double y2) {
    return new Segment(x1, y1, x2, y2);
  }
  public double startX() {
    return start.x();
  }

  public double startY() {
    return start.y();
  }

  public double endX() {
    return end.x();
  }

  public double endY() {
    return end.y();
  }

  @Override
  public String toString() {
    return String.format("Segment{%.1f, %.1f; %.1f, %.1f}", startX(), startY(), endX(), endY());
  }
}
