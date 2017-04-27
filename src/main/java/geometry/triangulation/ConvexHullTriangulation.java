package geometry.triangulation;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

import static geometry.convexhull.GrahamScan.convexHull;


public class ConvexHullTriangulation extends AbstractTriangulation {

    public ConvexHullTriangulation(List<Point> points){
        super(points);
    }

    @Override
    protected void triangulate(List<Point> points) {

        if(points.size() < 3) throw new IllegalArgumentException("Can't build a triangulation for 2 or less points");

        List<Point> sorted = new ArrayList<>(points);
        sorted.sort((Point p1, Point p2) -> {
            int res = Double.compare(p1.y(), p2.y());
            return res != 0 ? res : -Double.compare(p1.x(), p2.x());
        });

        addTriangle(new Triangle(sorted.get(0), sorted.get(1), sorted.get(2)));

        List<Segment> newConvHull, oldConvHull = convexHull(sorted.subList(0,3));

        for(int i = 3; i < sorted.size(); i++){
            newConvHull = convexHull(sorted.subList(0, i + 1));

            for(int j = 0; j < oldConvHull.size(); j++){
                if(!oldConvHull.get(j).equals(newConvHull.get(j))){

                    Point end = newConvHull.get(j + 1).end(),
                          C   = newConvHull.get(j + 1).start(),
                          A, B;

                    do{
                        A = oldConvHull.get(j).start();
                        B = oldConvHull.get(j).end();
                        addTriangle(new Triangle(A,C,B));
                        j++;
                    } while(!B.equals(end));

                    break;
                }
            }

            oldConvHull = newConvHull;
        }


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

        Collections.shuffle(verteses);

        AbstractTriangulation triangulation = new ConvexHullTriangulation(verteses);

        Set<Segment> edges = new HashSet();
        for(Triangle T : triangulation.triangles()){
            for(int i = 0; i < 3; i++){
                edges.add(T.side(i));
            }
        }

        List<Segment> segments = new ArrayList<>();
        segments.addAll(edges);

        Draw draw = new Draw();
        draw.setCanvasSize(800, 600);
        draw.setXscale(0,800);
        draw.setYscale(0,600);
        draw.setPenColor(Color.RED);
        DrawHelper.drawSegments(draw,segments);
        draw.setPenColor(Color.BLUE);
        DrawHelper.drawPoints(draw,verteses,5.);
    }

}
