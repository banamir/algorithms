package geometry.triangulation.voronoi;

import geometry.dto.Point;

import java.util.Collection;
import java.util.List;

import static geometry.triangulation.voronoi.VoronoiVertex.*;
import static geometry.utils.VectorOperations.*;


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

        int size = voronoiVertices.size();
        for(int i = 1, j = 0; i < size; j = i++ ){

            Point s = voronoiVertices.get(j).getVertex(),
                  e = voronoiVertices.get(i).getVertex();

            if(vectProduct(diff(e, s), diff(p, s)) < 0) return false;
        }

        if(voronoiVertices.get(0).getType() == VertexType.INNER){
            Point s = voronoiVertices.get(voronoiVertices.size()-1).getVertex(),
                  e = voronoiVertices.get(0).getVertex();
            if(vectProduct(diff(e, s), diff(p, s)) < 0) return false;
        }

        return true;
    }

}
