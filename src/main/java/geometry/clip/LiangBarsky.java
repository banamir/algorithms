package geometry.clip;

import geometry.dto.Point;
import geometry.dto.Rectangle;
import geometry.dto.Segment;
import geometry.utils.Visualiser;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static geometry.dto.Segment.segment;

/**
 * https://en.wikipedia.org/wiki/Liang%E2%80%93Barsky_algorithm
 */
public class LiangBarsky {

  public static void main(String[] args) {
    List<Segment> lines = new ArrayList<>();

    lines.add(segment(10, 210, 710, 10));
    Rectangle rec = new Rectangle(100, 100, 300, 300);

    Visualiser vis = new Visualiser();
    vis.drawLines(lines);
    vis.drawLines(rec.sides(), Color.ORANGE);
    vis.drawLines(clip(lines, rec), Color.RED);
  }

  public static List<Segment> clip(List<Segment> lines, Rectangle rec) {
    List<Segment> res = new ArrayList<>();

    for (Segment line : lines) {

      double t0 = 0d, t1 = 1d, dx = line.endX() - line.startX(), dy = line.endY() - line.startY();
      double[] p = {-dx, dx, -dy, dy};
      double[] q = {line.startX() - rec.minX(), rec.maxX() - line.startX(),
          line.startY() - rec.minY(), rec.maxY() - line.startY()};

      for (int i = 0; i < 4; i++) { // Traverse through left, right, bottom, top edges.

        if (p[i] == 0) {
          if (q[i] < 0) {
            break;
          }
        } else {

          if (p[i] < 0) {
            t0 = Math.max(t0, q[i] / p[i]);
          } else {
            t1 = Math.min(t1, q[i] / p[i]);
          }
        }
      }

      if (t1 < t0) {
        continue;
      }
      Point start = new Point(line.startX() + t0 * dx, line.startY() + t0 * dy);
      Point end = new Point(line.startX() + t1 * dx, line.startY() + t1 * dy);
      res.add(segment(start, end));
    }

    return res;
  }
}

