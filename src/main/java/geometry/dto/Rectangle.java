package geometry.dto;

import java.util.ArrayList;
import java.util.List;

import static geometry.dto.Segment.segment;

public class Rectangle {
  private final Point topLeft, bottomRight;

  public Rectangle(Point topLeft, Point bottomRight) {
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
  }

  public Rectangle(double minX, double maxY, double maxX, double minY) {
    topLeft = new Point(minX, maxY);
    bottomRight = new Point(maxX, minY);
  }

  public double minX() {
    return Math.min(topLeft.x(), bottomRight.x());
  }

  public double maxX() {
    return Math.max(bottomRight.x(), topLeft.x());
  }

  public double minY() {
    return Math.min(bottomRight.y(), topLeft.y());
  }

  public double maxY() {
    return Math.max(topLeft.y(), bottomRight.y());
  }

  public List<Segment> sides() {
    List<Segment> res = new ArrayList<>(4);
    res.add(segment(minX(), maxY(), maxX(), maxY())); // top
    res.add(segment(maxX(), maxY(), maxX(), minY())); // right
    res.add(segment(minX(), minY(), maxX(), minY())); // bottom
    res.add(segment(minX(), minY(), minX(), maxY())); // left
    return res;
  }
}
