package geometry.triangulation.voronoi;

import geometry.dto.Point;

/**
 * Created by banamir on 06.04.17.
 */


public class VoronoiVertex{

    public enum VertexType {INNER, OUTER} ;

    private VertexType type;

    private Point vertex;

    public VoronoiVertex(Point vertex, VertexType type){
        this.type = type; this.vertex = vertex;
    }

    public VertexType getType() {return type; }

    public Point getVertex(){return vertex;}
}