package geometry.triangulation;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;


public class DelaunayConvexHullTriangulation extends ConvexHullTriangulation {

    public DelaunayConvexHullTriangulation(List<Point> points) {
        super(points);
    }

    protected void triangulate(List<Point> points) {
        super.triangulate(points);

        Entry<Triangle, Integer> pair;

        while ((pair = getTriangleForUpdate()) != null) {
            flip(pair.getKey(), pair.getValue());
        }

    }

    protected Entry<Triangle, Integer> getTriangleForUpdate() {

        for (Triangle T : triangles()) {
            for (int i = 0; i < 3; i++) {
                if (canImprove(T, i))
                    return new AbstractMap.SimpleEntry<Triangle, Integer>(T, i);
            }
        }

        return null;
    }

    public static void main(String[] args) {

        List<Point> verteses = new ArrayList();

        verteses.add(new Point(375, 25));  //1
        verteses.add(new Point(500, 150)); //2
        verteses.add(new Point(250, 200)); //3

        verteses.add(new Point(400, 225)); //4

        verteses.add(new Point(600, 300)); //5
        verteses.add(new Point(375, 300)); //6
        verteses.add(new Point(270, 300)); //7

        verteses.add(new Point(420, 350)); //8
        verteses.add(new Point(380, 450)); //9


        Collections.shuffle(verteses);

        AbstractTriangulation triangulation = new DelaunayConvexHullTriangulation(verteses);

        Draw draw = new Draw();
        DrawHelper helper = new DrawHelper(draw);

        draw.setCanvasSize(800, 600);
        draw.setXscale(0, 800);
        draw.setYscale(0, 600);

        draw.setPenColor(Color.RED);
        helper.draw(triangulation.graph());

        draw.setPenColor(Color.BLUE);
        helper.draw(verteses, 5.);
    }
}
