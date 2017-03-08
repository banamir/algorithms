package geometry.triangulation;

import geometry.dto.Point;
import geometry.dto.Segment;

import static geometry.utils.VectorOperations.*;

/**
 * Created by banamir on 04.03.17.
 */
public class Triangle {

    private Point[] verteses = new Point[3];

    private Triangle[] neighbors = new Triangle[3];

    public Triangle(Point p_i, Point p_j, Point p_k){
        verteses[0] = p_i;
        if(vectProduct(diff(p_j,p_i), diff(p_k,p_j)) >= 0){
            verteses[1] = p_j;
            verteses[2] = p_k;
        } else {
            verteses[1] = p_k;
            verteses[2] = p_j;
        }
    }

    public Segment edge(int i){
        return new Segment(vertex(i),vertex(i + 1));
    }

    public Point vertex(int i){
        return verteses[i % 3];
    }

    public void setNeighbor(int i, Triangle T){
        neighbors[i % 3] = T;
    }

    public Triangle getNeighbor(int i){
       return neighbors[i % 3];
    }

    public boolean contains(Point p){
        for(int i = 0; i <= verteses.length; i++){
            if(vectProduct(diff(vertex(i+1),vertex(i)), diff(p, vertex(i))) < 0){
                return false;
            }
        }
        return true;
    }

    public boolean contains(Segment edge){
        for(int i = 0; i < 3; i++){
            Segment e = this.edge(i);
            if(edge.equals(e) || edge.equals(new Segment(e.end(), e.start()))){
                return true;
            }
        }
        return false;
    }


}
