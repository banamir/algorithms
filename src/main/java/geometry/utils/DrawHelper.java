package geometry.utils;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.graphs.EuclideanGraph;
import geometry.triangulation.voronoi.VoronoiTile;
import geometry.triangulation.voronoi.VoronoiVertex;

import java.util.ArrayList;
import java.util.List;


public class DrawHelper {

    public Draw image;

    public DrawHelper(Draw imgae) {
        this.image = imgae;
    }

    public void draw(Segment segment) {
        image.line(segment.startX(), segment.startY(), segment.endX(), segment.endY());
    }

    public void draw(List<Segment> segments) {

        for (Segment s : segments) {
            draw(s);
        }
    }

    public void draw(Point point, double radius) {

        image.filledCircle(point.x(), point.y(), radius);
    }

    public void draw(List<Point> points, double radius) {

        for (Point p : points) {
            draw(p, radius);
        }
    }

    public void draw(VoronoiTile tile) {
        List<VoronoiVertex> lv = new ArrayList<>(tile.voronoiVertices());

        for (int i = 0; i < lv.size() - 1; i++) {
            draw(new Segment(lv.get(i).getVertex(), lv.get(i + 1).getVertex()));
        }

        if (lv.get(0).getType() == VoronoiVertex.VertexType.INNER &&
                lv.get(lv.size() - 1).getType() == VoronoiVertex.VertexType.INNER) {
            draw(new Segment(lv.get(0).getVertex(), lv.get(lv.size() - 1).getVertex()));
        }
    }

    public void draw(EuclideanGraph graph) {

        for (Point v : graph.points()) {
            for (Point w : graph.adj(v)) {
                draw(new Segment(v, w));
            }
        }
    }

}
