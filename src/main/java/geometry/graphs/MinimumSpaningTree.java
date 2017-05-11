package geometry.graphs;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.IncrementDelaunay;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by banamir on 17.04.17.
 */
public class MinimumSpaningTree extends EuclideanGraph {

    private HashSet<Point> marked = new HashSet<>();

    private PriorityQueue<Segment> pq = new PriorityQueue<>(
            (Segment s1, Segment s2) -> Double.compare(s1.length(), s2.length()));

    public MinimumSpaningTree(EuclideanGraph graph) {
        this(graph, graph.points().iterator().next());
    }

    public MinimumSpaningTree(EuclideanGraph graph, Point p) {
        scan(graph, p);

        while (!pq.isEmpty()) {

            Segment edge = pq.poll();
            Point s = edge.start(), e = edge.end();
            if (marked.contains(s) && marked.contains(e)) continue;

            this.addEdge(edge.start(), edge.end());

            if (!marked.contains(s)) scan(graph, s);
            if (!marked.contains(e)) scan(graph, e);
        }
    }

    private void scan(EuclideanGraph graph, Point p) {
        marked.add(p);

        for (Point v : graph.adj(p)) {
            if (!marked.contains(v)) pq.add(new Segment(v, p));
        }
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
        EuclideanGraph mst = new MinimumSpaningTree(graph);

        Draw draw = new Draw();
        DrawHelper helper = new DrawHelper(draw);

        draw.setCanvasSize(800, 600);
        draw.setXscale(0, 800);
        draw.setYscale(0, 600);

        draw.setPenColor(Color.RED);
        helper.draw(graph);

        draw.setPenRadius(0.005);
        helper.draw(mst);
        draw.setPenColor(Color.BLUE);
        helper.draw(verteses, 5.);
    }

}
