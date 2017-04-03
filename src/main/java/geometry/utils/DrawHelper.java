package geometry.utils;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;

import java.util.List;

/**
 * Created by banamir on 03.04.17.
 */
public class DrawHelper {

    public static void drawSegments(Draw draw, List<Segment> segments){

        for(Segment s : segments) {
            draw.line(s.startX(), s.startY(), s.endX(), s.endY());
        }
    }

    public static void drawPoints(Draw draw, List<Point> points, double radius){

        for(Point p : points){
            draw.filledCircle(p.x(),p.y(),radius);
        }
    }

}
