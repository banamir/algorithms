package geometry.triangulation;

import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.utils.Determinant;
import geometry.utils.Visualiser;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created by banamir on 05.03.17.
 */
public class DelaunayConvexHullTriangulation extends ConvexHullTriangulation {

    public DelaunayConvexHullTriangulation(List<Point > points){
        super(points);
    }

    protected void triangulate(List<Point> points){
        super.triangulate(points);

        Entry<Triangle, Integer> pair;

        while ((pair = getTriangleForUpdate()) != null){
            flip(pair.getKey(), pair.getValue());
        }

    }

    protected Entry<Triangle,Integer> getTriangleForUpdate(){

        for(Triangle T : this){
            for(int i = 0; i< 3; i++){
                if(canImprove(T, i))
                    return new AbstractMap.SimpleEntry<Triangle, Integer>(T, i);
            }
        }

        return null;
    }

    public static void main(String[] args){

        List<Point> verteses = new ArrayList();

        verteses.add(new Point(375,25));  //1
        verteses.add(new Point(500,150)); //2
        verteses.add(new Point(250,200)); //3

        verteses.add(new Point(400,225)); //4

        verteses.add(new Point(600,300)); //5
        verteses.add(new Point(375,300)); //6
        verteses.add(new Point(270,300)); //7

        verteses.add(new Point(420,350)); //8
        verteses.add(new Point(380,450)); //9

        Visualiser vis = new Visualiser();

        vis.drawPoints(verteses, 5., Color.BLUE);

        Collections.shuffle(verteses);

        AbstractTriangulation triangulation = new DelaunayConvexHullTriangulation(verteses);

        Set<Segment> edges = new HashSet();
        for(Triangle T : triangulation){
            for(int i = 0; i < 3; i++){
                edges.add(T.side(i));
            }
        }
        vis.drawLines(new ArrayList(edges));
    }
}
