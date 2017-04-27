package geometry.utils;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.voronoi.VoronoiTile;
import geometry.triangulation.voronoi.VoronoiVertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by banamir on 03.04.17.
 */
public class DrawHelper {

    public static void drawSegment(Draw draw,Segment segment) {
        draw.line(segment.startX(), segment.startY(), segment.endX(), segment.endY());
    }

    public static void drawSegments(Draw draw, List<Segment> segments){

        for(Segment s : segments) {
           drawSegment(draw, s);
        }
    }

    public static void drawPoints(Draw draw, List<Point> points, double radius){

        for(Point p : points){
            draw.filledCircle(p.x(), p.y(), radius);
        }
    }

    public static void drawVoronoiTile(Draw draw, VoronoiTile tile) {
        List<VoronoiVertex> lv = new ArrayList<>(tile.voronoiVertices());

        for(int i =0; i < lv.size()-1; i++){
            drawSegment(draw, new Segment(lv.get(i).getVertex(), lv.get(i + 1).getVertex()));
        }

        if(lv.get(0).getType() == VoronoiVertex.VertexType.INNER &&
           lv.get(lv.size()-1).getType() == VoronoiVertex.VertexType.INNER ){
            drawSegment(draw, new Segment(lv.get(0).getVertex(), lv.get(lv.size()-1).getVertex()));
        }
    }

}
