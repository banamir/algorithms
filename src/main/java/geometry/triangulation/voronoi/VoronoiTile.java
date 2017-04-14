package geometry.triangulation.voronoi;

import geometry.dto.Point;

import java.util.Collection;
import java.util.List;

import static geometry.triangulation.voronoi.VoronoiVertex.*;

/**
 * Created by banamir on 13.04.17.
 */
public class VoronoiTile {

    private Point center;
    private List<VoronoiVertex> voronoiVertices;

    public  VoronoiTile(Point center, List<VoronoiVertex> points){
        this.center = center;
        this.voronoiVertices = points;
    }

    public Collection<VoronoiVertex> voronoiVertices(){
        return voronoiVertices;
    }

    public Point getCenter(){
        return center;
    }

    public boolean contains(Point p){
        int cross = 0;
        double order = 0; //????
        int size = voronoiVertices.size();
        for(int i = 0, j = 0; i < size - 1 ; j = i++ ){

            if(voronoiVertices.get(i).getType() == VertexType.OUTER) continue;

            Point s = voronoiVertices.get(i).getVertex(),
                  e = voronoiVertices.get(j).getVertex();

            if(p == s) return true;

            double factor = e.y() - s.y();

            if(factor == 0) continue;

            double t = (p.y()-s.y())/factor;


            if((voronoiVertices.get(i).getType() == VertexType.INNER && t < 0 ) ||
               (voronoiVertices.get(i + 1).getType() == VertexType.INNER && t > 1 ))
                continue;

            if((p.y() == s.y() && order*factor <= 0) ||
               (t*(e.x()-s.x()) > p.x()) && p.y() != s.y()){
                cross++;
                order = Math.signum(factor);
            }
        }

        return cross % 2 != 0;
    }

}
