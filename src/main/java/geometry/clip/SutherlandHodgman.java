package geometry.clip;

import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.utils.Visualiser;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static geometry.dto.Point.point;
import static geometry.dto.Segment.segment;

public class SutherlandHodgman {

  public static List<Point> clip(List<Point> subjectPolygon, List<Segment> clippingShape) {
    List<Point> result = new ArrayList<>(subjectPolygon);

    for (Segment clipEdge : clippingShape) {
      if (result.isEmpty()) return result;

      List<Point> inputList = new ArrayList<>(result);
      result.clear();
      Point S = inputList.get(inputList.size() - 1);

      for (Point E : inputList) {
        if (inside(E, clipEdge)) {
          if (!inside(S, clipEdge)) {
            result.add(computeIntersection(S, E, clipEdge));
          }
          result.add(E);
        } else if (inside(S, clipEdge)) {
          result.add(computeIntersection(S, E, clipEdge));
        }
        S = E;
      }
    }

    return result;
  }

  private static Point computeIntersection(Point s, Point e, Segment clipEdge) {
    return Segment.intersect(clipEdge, new Segment(s, e));
  }

  /**
   * Here we basically compare tangents(angles) of two lines - tg(side.start, p) and tg(side).
   */
  private static boolean inside(Point p, Segment edge) {
    double y1 = edge.start().y();
    double y2 = edge.end().y();
    double x1 = edge.start().x();
    double x2 = edge.end().x();

    return (x2 - x1) * (p.y() - y1) > (y2 - y1) * (p.x() - x1);
  }

  public static void main(String[] args) {
    List<Point> subject = new ArrayList<>();
    subject.add(point(10, 10));
    subject.add(point(350, 150));
    subject.add(point(350, 300));
    subject.add(point(120, 300));

    List<Segment> clipShape = new ArrayList<>();
    clipShape.add(segment(200, 30, 190, 350));
    clipShape.add(segment(190, 350, 30, 100));
    clipShape.add(segment(30, 100, 200, 30));

    List<Point> res = clip(subject, clipShape);
    System.out.println(res);

    new Visualiser().draw(subject).drawLines(clipShape, Color.GREEN).draw(res, Color.ORANGE, 2);
  }
}

