package geometry.graphs;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.AbstractTriangulation;
import geometry.triangulation.IncrementDelaunay;
import geometry.triangulation.Triangle;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by banamir on 17.04.17.
 */
public class EuclideanGraph {

    private HashMap<Point,HashSet<Segment>> edges;


    public EuclideanGraph(){
        edges = new HashMap<>();
    }

    public void addEdge(Point v1,Point v2){
       addOneEdge(v1,v2);
       addOneEdge(v2,v1);
    }

    protected void addOneEdge(Point v1, Point v2){
        if(edges.containsKey(v1)){
            edges.get(v1).add(new Segment(v1,v2));
        } else {
            HashSet<Segment> adj = new HashSet<>();
            adj.add(new Segment(v1,v2));
            edges.put(v1,adj);
        }
    }

    public boolean contains(Point p){
        return edges.containsKey(p);
    }

    public Iterable<Segment> adj(Point p){
        if(!contains(p)) return  null;

        return edges.get(p);
    }

    public Iterable<Point> points(){
        return edges.keySet();
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

        List<Segment> segments = new ArrayList<>();

        HashSet<Point> visited = new HashSet<>();
        EuclideanGraph graph = triangulation.graph();
        for(Point v: graph.points()) {
            visited.add(v);
            for(Segment e : graph.adj(v)) {
                if(visited.contains(e.end()))
                    segments.add(e);
                else {
                    System.out.println("Point " + v + " visited");
                }
            }
        }


        Draw draw = new Draw();
        draw.setCanvasSize(800, 600);
        draw.setXscale(0,800);
        draw.setYscale(0, 600);
        draw.setPenColor(Color.RED);
        DrawHelper.drawSegments(draw, segments);
        draw.setPenColor(Color.BLUE);
        DrawHelper.drawPoints(draw, verteses, 5.);
    }


}
