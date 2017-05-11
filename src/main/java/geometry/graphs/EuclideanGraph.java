package geometry.graphs;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.triangulation.IncrementDelaunay;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EuclideanGraph {

    private HashMap<Point, HashSet<Point>> edges;

    public EuclideanGraph() {
        edges = new HashMap<>();
    }

    public void addEdge(Point v1, Point v2) {

        addOneEdge(v1, v2);
        addOneEdge(v2, v1);
    }

    protected void addOneEdge(Point v1, Point v2) {
        HashSet<Point> adj;

        if (edges.containsKey(v1)) {
            adj = edges.get(v1);
        } else {
            adj = new HashSet<>();
            edges.put(v1, adj);
        }

        adj.add(v2);
    }

    public boolean contains(Point p) {

        return edges.containsKey(p);
    }

    public Iterable<Point> adj(Point p) {
        if (!contains(p)) return null;

        return edges.get(p);
    }

    public Iterable<Point> points() {
        return edges.keySet();
    }

    public static void main(String[] args) {
        List<Point> verteses = new ArrayList();

        verteses.add(new Point(250, 200)); //3
        verteses.add(new Point(375, 25));  //1
        verteses.add(new Point(500, 150)); //2


        verteses.add(new Point(400, 225)); //4

        verteses.add(new Point(600, 300)); //5
        verteses.add(new Point(375, 300)); //6
        verteses.add(new Point(270, 300)); //7

        verteses.add(new Point(420, 350)); //8
        verteses.add(new Point(380, 450)); //9

        Collections.shuffle(verteses);

        EuclideanGraph graph = new IncrementDelaunay(verteses).graph();

        Draw draw = new Draw();
        DrawHelper helper = new DrawHelper(draw);

        draw.setCanvasSize(800, 600);
        draw.setXscale(0, 800);
        draw.setYscale(0, 600);

        draw.setPenColor(Color.RED);
        helper.draw(graph);

        draw.setPenColor(Color.BLUE);
        helper.draw(verteses, 5.);
    }

}
