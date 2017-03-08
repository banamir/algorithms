package geometry.triangulation;

import geometry.dto.Point;
import geometry.dto.Segment;

import java.util.*;

/**
 * Created by banamir on 04.03.17.
 */
public abstract class AbstractTriangulation implements Iterable<Triangle> {

    private Set<Triangle> triangles = new HashSet();

    public AbstractTriangulation(List<Point> points){
        triangulate(points);
    }

    abstract protected void triangulate(List<Point> points);

    public Iterator<Triangle> iterator(){
        return triangles.iterator();
    }

    protected void addTriangle(Triangle T){

        for(int i =0; i < 3; i++){

            Segment edge = T.edge(i);
            Point p =  new Point(0.5*(edge.endX() + edge.startX()),0.5*(edge.endY() + edge.startY()));
            List<Triangle> list = getTriangles(p);
            list.remove(T);

            if(!list.isEmpty()) { //TODO: if list has a size biggest than one
                Triangle neighbor = list.get(0);
                T.setNeighbor(i, neighbor);

                for(int j = 0; j < 3; j++ ){
                    Segment e = neighbor.edge(j);
                    if(edge.equals(e) || edge.equals(new Segment(e.end(),e.start())) ){
                        neighbor.setNeighbor(j,T);
                        break;
                    }
                }
            }
        }
        triangles.add(T);
    }

    protected void removeTriangle(Triangle T){

        for(int i = 0; i < 3; i++){

            Triangle neighbor = T.getNeighbor(i);

            if(neighbor == null) continue;

            for(int j = 0; j < 3; j++){
                if(neighbor.getNeighbor(j) == T) neighbor.setNeighbor(j, null);
            }
        }
        triangles.remove(T);
    }

    protected List<Triangle> getTriangles(Point p){
        List<Triangle> result = new ArrayList();

        for(Triangle T : triangles){
            if(T.contains(p)) result.add(T); ;
        }

        return result;
    }

}
