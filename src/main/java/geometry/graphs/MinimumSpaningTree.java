package geometry.graphs;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.AbstractTriangulation;
import geometry.triangulation.IncrementDelaunay;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by banamir on 17.04.17.
 */
public class MinimumSpaningTree {

    private HashSet<Point> marked = new HashSet<>();
    private PriorityQueue<Segment> pq = new PriorityQueue<>(
            (Segment s1, Segment s2)-> Double.compare(s1.length(),s2.length()));

    private HashSet<Segment> edges = new HashSet();

    public MinimumSpaningTree(EuclideanGraph graph){
        this(graph, graph.points().iterator().next());
    }

    public MinimumSpaningTree(EuclideanGraph graph, Point p){
        scan(graph, p);
        while(!pq.isEmpty()){
            Segment edge = pq.poll();
            Point s = edge.start(), e = edge.end();
            if(marked.contains(s) && marked.contains(e)) continue;

            edges.add(edge);

            if(!marked.contains(s)) scan(graph,s);
            if(!marked.contains(e)) scan(graph,e);
        }
    }

    private void scan(EuclideanGraph graph, Point p){
        marked.add(p);

        for(Segment edge : graph.adj(p)){
            if(!marked.contains(edge.end())) pq.add(edge);
        }
    }

    public Iterable<Segment>  edges(){
        return edges();
    }

    public EuclideanGraph graph(){
        EuclideanGraph g = new EuclideanGraph();
        for(Segment e : edges){
            g.addEdge(e.start(),e.end());
        }
        return g;
    }

    public static void main(String[] args) {
        List<Point> verteses = new ArrayList();

        verteses.add(new Point(250,200)); //3
        verteses.add(new Point(375,25));  //1
        verteses.add(new Point(500,150)); //2


        verteses.add(new Point(400,225)); //4

        verteses.add(new Point(600,300)); //5
        verteses.add(new Point(375,300)); //6
        verteses.add(new Point(270,300)); //7

        verteses.add(new Point(420,350)); //8
        verteses.add(new Point(380,450)); //9

        Collections.shuffle(verteses);

        AbstractTriangulation triangulation = new IncrementDelaunay(verteses);

        List<Segment> edges = new ArrayList<>();

        HashSet<Point> visited = new HashSet<>();
        EuclideanGraph graph = triangulation.graph();
        for(Point v: graph.points()) {
            visited.add(v);
            for(Segment e : graph.adj(v)) {
                if(visited.contains(e.end())){
                    edges.add(e);
                }
            }
        }

        List<Segment> mstEdges = new ArrayList<>();
        EuclideanGraph mst = new MinimumSpaningTree(graph).graph();
        for(Point v: mst.points()) {
            visited.add(v);
            for(Segment e : mst.adj(v)) {
                if(visited.contains(e.end()))
                    mstEdges.add(e);
            }
        }


        Draw draw = new Draw();
        draw.setCanvasSize(800, 600);
        draw.setXscale(0, 800);
        draw.setYscale(0, 600);
        draw.setPenColor(Color.RED);
        DrawHelper.drawSegments(draw, edges);
        draw.setPenRadius(0.005);
        DrawHelper.drawSegments(draw, mstEdges);
        draw.setPenColor(Color.BLUE);
        DrawHelper.drawPoints(draw, verteses, 5.);
    }

}
