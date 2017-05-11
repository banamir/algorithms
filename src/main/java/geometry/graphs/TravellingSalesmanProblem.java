package geometry.graphs;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.IncrementDelaunay;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class TravellingSalesmanProblem extends EuclideanGraph {

    private HashSet<Point> visited = new HashSet<>();

    private Point lastVisited;


    public TravellingSalesmanProblem(EuclideanGraph graph) {

        EuclideanGraph mst = new MinimumSpaningTree(graph);

        Point vertex = mst.points().iterator().next();
        visitVertex(mst, vertex);
        addEdge(lastVisited, vertex);

    }

    private Point visitVertex(EuclideanGraph mst, Point vertex) {

        visited.add(vertex);

        lastVisited = vertex;

        ArrayList<Point> points = new ArrayList<>();
        mst.adj(vertex).iterator().forEachRemaining(points::add);
        Collections.sort(points, (Point p1, Point p2) ->
                Double.compare(new Segment(vertex, p1).length(), new Segment(vertex, p2).length()));

        for (Point p : points) {

            if (visited.contains(p)) continue;

            addEdge(p, lastVisited);
            visitVertex(mst, p);
        }
        return vertex;
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
        EuclideanGraph tsp = new TravellingSalesmanProblem(graph);

        Draw draw = new Draw();
        DrawHelper helper = new DrawHelper(draw);

        draw.setCanvasSize(800, 600);
        draw.setXscale(0, 800);
        draw.setYscale(0, 600);

        draw.setPenColor(Color.RED);
        helper.draw(graph);

        draw.setPenRadius(0.01);
        helper.draw(tsp);

        draw.setPenColor(Color.BLUE);
        helper.draw(verteses, 5.);
    }


}
