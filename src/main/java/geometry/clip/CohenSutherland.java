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
 * https://en.wikipedia.org/wiki/Cohen%E2%80%93Sutherland_algorithm
 */
public class CohenSutherland {

  private static final int INSIDE = 0, // 0000
      LEFT = 1,   // 0001
      RIGHT = 2,  // 0010
      BOTTOM = 4, // 0100
      TOP = 8;    // 1000

  public static void main(String[] args) {
    List<Segment> lines = new ArrayList<>();
    lines.add(segment(10, 210, 710, 10));
    lines.add(segment(10, 10, 400, 400));
    Rectangle rec = new Rectangle(100, 100, 300, 300);

    Visualiser vis = new Visualiser();
    vis.drawLines(lines, Color.GRAY);
    vis.drawLines(rec.sides(), Color.ORANGE);
    vis.drawLines(clip(lines, rec), Color.RED);
  }

  private static int code(Point p, Rectangle rec) {
    int code = INSIDE;

    if (p.x() < rec.minX()) {          // to the left of clip window
      code |= LEFT;
    } else if (p.x() > rec.maxX()) {     // to the right of clip window
      code |= RIGHT;
    }
    if (p.y() < rec.minY()) {          // below the clip window
      code |= BOTTOM;
    } else if (p.y() > rec.maxY()) {     // above the clip window
      code |= TOP;
    }

    return code;
  }

  public static List<Segment> clip(List<Segment> lines, Rectangle rec) {
    List<Segment> res = new ArrayList<>();

    for (Segment line : lines) {
      int code0 = code(line.start(), rec);
      int code1 = code(line.end(), rec);

      while (true) {
        if ((code0 | code1) == 0) {
          res.add(line);
          break;

        } else if ((code0 & code1) != 0) {
          break; //nothing to add
        } else {

          double x = 0, y = 0, x0 = line.startX(), y0 = line.startY(), x1 = line.endX(), y1 = line.endY();

          int code = code0 == 0 ? code1 : code0;

          if ((code & TOP) != 0) {           // point is above the clip rectangle
            x = x0 + (x1 - x0) * (rec.maxY() - y0) / (y1 - y0);
            y = rec.maxY();
          } else if ((code & BOTTOM) != 0) { // point is below the clip rectangle
            x = x0 + (x1 - x0) * (rec.minY() - y0) / (y1 - y0);
            y = rec.minY();
          } else if ((code & RIGHT) != 0) {  // point is to the right of clip rectangle
            y = y0 + (y1 - y0) * (rec.maxX() - x0) / (x1 - x0);
            x = rec.maxX();
          } else if ((code & LEFT) != 0) {   // point is to the left of clip rectangle
            y = y0 + (y1 - y0) * (rec.minX() - x0) / (x1 - x0);
            x = rec.minX();
          }

          if (code == code0) {
            line = segment(new Point(x, y), line.end());
            code0 = code(line.start(), rec);
          } else {
            line = segment(line.start(), new Point(x, y));
            code1 = code(line.end(), rec);
          }

        }
      }
    }

    return res;
  }

}
