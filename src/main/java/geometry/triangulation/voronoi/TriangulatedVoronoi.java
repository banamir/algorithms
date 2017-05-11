package geometry.triangulation.voronoi;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.IncrementDelaunay;
import geometry.triangulation.Triangle;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import static geometry.utils.VectorOperations.*;


public class TriangulatedVoronoi extends IncrementDelaunay {

    protected HashSet<VoronoiTile> tiles = new HashSet();

    public TriangulatedVoronoi(List<Point> points) {
        super(points);

        for (Entry entry : vertexMap.entrySet()) {
            Point vertex = (Point) entry.getKey();
            Set<Triangle> Ts = (Set<Triangle>) entry.getValue();

            List<VoronoiVertex> vs = new ArrayList<>();
            Point outVertex = Ts.iterator().next().centroid();

            for (Triangle T : Ts) {

                Point c = T.centroid();

                vs.add(new VoronoiVertex(c, VoronoiVertex.VertexType.INNER));

                for (int i = 0; i < 3; i++) {
                    if (T.vertex(i) == vertex) {
                        Point p = addOutPoint(vs, T, i - 1);
                        if (p != null) outVertex = p;

                        addOutPoint(vs, T, i);

                        break;
                    }
                }
            }

            final Point base = outVertex;
            vs.sort((VoronoiVertex v1, VoronoiVertex v2) -> {
                Point a = diff(v1.getVertex(), vertex),
                        b = diff(v2.getVertex(), vertex),
                        c = diff(base, vertex);

                double angle1 = Math.atan2(vectProduct(a, c), scalarProduct(a, c));
                if (angle1 < 0) angle1 += 2. * Math.PI;

                double angle2 = Math.atan2(vectProduct(b, c), scalarProduct(b, c));
                if (angle2 < 0) angle2 += 2. * Math.PI;

                return -Double.compare(angle1, angle2);
            });

            tiles.add(new VoronoiTile(vertex, vs));
        }
    }


    private Point addOutPoint(List<VoronoiVertex> vs, Triangle T, int i) {

        if (T.neighbor(i) != null) return null;

        Segment side = T.side(i);
        Point c = T.centroid(),
                s = side.start(), e = side.end();
        Point m = mult(add(e, s), 0.5),
                sc = diff(c, s), cm = diff(m, c);

        double mu = (T.contains(c)) ? 1 : -1;
        Point p = (length(cm) < 0.1E-3)
                ? diff(c, norm(mult(sc, mu)))
                : add(c, mult(cm, mu * length(sc) / length(cm)));


        vs.add(new VoronoiVertex(p, VoronoiVertex.VertexType.OUTER));

        return p;
    }


    public VoronoiTile tile(Point p) {
        int i = 0;
        for (VoronoiTile tile : tiles) {
            if (tile.contains(p)) return tile;
        }
        return null;
    }

    public HashSet<VoronoiTile> tiles() {

        return tiles;
    }


    public static void main(String[] args) {
        List<Point> verteses = new ArrayList();

        verteses.add(new Point(250, 200)); //3
        verteses.add(new Point(375, 25));  //1
        verteses.add(new Point(500, 150)); //2

//
        verteses.add(new Point(400, 225)); //4
//
        verteses.add(new Point(600, 300)); //5
        verteses.add(new Point(375, 300)); //6
        verteses.add(new Point(270, 300)); //7

        verteses.add(new Point(420, 350)); //8
        verteses.add(new Point(380, 450)); //9

        Collections.shuffle(verteses);

        TriangulatedVoronoi triangulation = new TriangulatedVoronoi(verteses);

        Draw draw = new Draw();
        DrawHelper helper = new DrawHelper(draw);

        draw.setCanvasSize(800, 600);
        draw.setXscale(0, 800);
        draw.setYscale(0, 600);

        draw.setPenColor(Color.RED);
        helper.draw(triangulation.graph());

        draw.setPenColor(Color.BLUE);
        helper.draw(verteses, 5.);

        draw.setPenColor(Color.MAGENTA);
        for (VoronoiTile tile : triangulation.tiles()) {
            helper.draw(tile);
        }

        Point p = new Point(450, 500);

        draw.setPenColor(Color.CYAN);
        helper.draw(p, 5);

        VoronoiTile tile = triangulation.tile(p);
        draw.setPenColor(Color.GREEN);
        helper.draw(tile);
        helper.draw(tile.getCenter(), 5);


    }


}
