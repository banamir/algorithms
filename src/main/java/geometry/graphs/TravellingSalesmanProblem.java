package geometry.graphs;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.AbstractTriangulation;
import geometry.triangulation.IncrementDelaunay;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by banamir on 17.04.17.
 */
public class TravellingSalesmanProblem {

    private HashSet<Point> visited = new HashSet<>();

    private HashSet<Segment> edges = new HashSet<>();

    private Point lastVisited;


    public TravellingSalesmanProblem(EuclideanGraph graph){

        EuclideanGraph  mst = new MinimumSpaningTree(graph).graph();

        Point vertex = mst.points().iterator().next();
        visitVertex(mst,vertex);
        edges.add(new Segment(lastVisited,vertex));

    }

    private Point visitVertex(EuclideanGraph mst, Point vertex){

        visited.add(vertex);

        lastVisited = vertex;

        ArrayList<Segment> es = new ArrayList<>();
        mst.adj(vertex).iterator().forEachRemaining(es::add);
        Collections.sort(es,(Segment s1,Segment s2)->
            Double.compare(s1.length(),s2.length()));

        for(Segment edge : es){
            if(visited.contains(edge.end())) continue;
            //edges.add(new Segment(edge.start(),edge.end()));
            edges.add(new Segment(edge.end(),lastVisited));
            visitVertex(mst,edge.end());
        }
        return vertex;
    }

    public EuclideanGraph graph(){
        EuclideanGraph g = new EuclideanGraph();
        for(Segment e : edges){
            g.addEdge(e.start(), e.end());
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

        List<Segment> tspEdges = new ArrayList<>();
        EuclideanGraph tsp = new TravellingSalesmanProblem(graph).graph();
        for(Point v: tsp.points()) {
            visited.add(v);
            for(Segment e : tsp.adj(v)) {
                if(visited.contains(e.end()))
                    tspEdges.add(e);
            }
        }


        Draw draw = new Draw();
        draw.setCanvasSize(800, 600);
        draw.setXscale(0, 800);
        draw.setYscale(0, 600);
        draw.setPenColor(Color.RED);
        DrawHelper.drawSegments(draw, edges);
        draw.setPenRadius(0.01);
        DrawHelper.drawSegments(draw, tspEdges);
        draw.setPenColor(Color.BLUE);
        DrawHelper.drawPoints(draw, verteses, 5.);
    }



}
