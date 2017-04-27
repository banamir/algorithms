package geometry.triangulation;

import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.graphs.EuclideanGraph;
import geometry.triangulation.utils.Determinant;

import static geometry.utils.VectorOperations.*;

import java.util.*;

public abstract class AbstractTriangulation {

    private Set<Triangle> triangles = new HashSet();

    protected HashMap<Point,Set<Triangle>> vertexMap = new HashMap();

    public AbstractTriangulation(List<Point> points){
        triangulate(points);
    }

    abstract protected void triangulate(List<Point> points);


    public Iterable<Triangle> triangles(){
        return triangles;
    }

    public Iterable<Point> vertises(){
        return vertexMap.keySet();
    }

    public EuclideanGraph graph(){

        EuclideanGraph graph = new EuclideanGraph();

        for(Point v : vertises()){
            List<Triangle> triangles = pointTriangles(v);
            for(Triangle T : triangles){
                for(int i = 0; i < 3; i++){
                    if(T.vertex(i).equals(v)){
                        graph.addEdge(v,T.vertex(i - 1));
                        graph.addEdge(v,T.vertex(i + 1));
                        break;
                    }
                }
            }
        }
        return graph;
    }

    public List<Triangle> pointTriangles(Point p){

        List<Triangle> result = new ArrayList();

        if(vertexMap.containsKey(p))
            return new ArrayList<>(vertexMap.get(p));

        for(Triangle T : triangles){
            if(T.contains(p)) result.add(T); ;
        }

        return result;
    }

    public boolean contains(Triangle T){
        return triangles.contains(T);
    }


    protected void addTriangle(Triangle T){

        //establish neighbor triangles
        for(int i =0; i < 3; i++){

            Segment side = T.side(i);
            List<Triangle> list = new ArrayList();

            for(Triangle t : triangles){
                for(int j = 0; j < 3; j++){

                    Segment s = t.side(j);

                    if(side.equals(s) || side.equals(inverse(s)) && !T.equals(t)){
                        t.neighbor(j,T);
                        T.neighbor(i,t);
                        break;
                    }
                }
            }
        }
        triangles.add(T);

        //add triangles for a vertex map
        for(int i =0; i<3; i++) {
            Point v = T.vertex(i);

            Set<Triangle> Ts;
            if (vertexMap.containsKey(v)) {
                Ts = vertexMap.get(v);
            } else {
                Ts = new HashSet<>();
                vertexMap.put(v, Ts);
            }

            Ts.add(T);
        }
    }

    protected void removeTriangle(Triangle T){

        for(int i = 0; i < 3; i++){

            //remove neighbors
            Triangle neighbor = T.neighbor(i);
            if(neighbor != null){
                for(int j = 0; j < 3; j++){
                    if(neighbor.neighbor(j) == T) neighbor.neighbor(j, null);
                }
            }

            //remove triangles from vertex hash map
            Point v = T.vertex(i);
            Set<Triangle> Ts = vertexMap.get(v);
            Ts.remove(T);

            if(Ts.isEmpty()) {
                vertexMap.remove(v);
            }
        }
        triangles.remove(T);
    }

    protected  boolean canImprove(Triangle T, int side){

        if(T.neighbor(side) == null) return false;

        Point   A = T.vertex(0),
                B = T.vertex(1),
                C = T.vertex(2),
                D = findNeighborPoint(T, side);

        double det[][] =
                {{A.x(),A.y(), A.x()*A.x() + A.y()*A.y(), 1.},
                 {B.x(),B.y(), B.x()*B.x() + B.y()*B.y(), 1.},
                 {C.x(),C.y(), C.x()*C.x() + C.y()*C.y(), 1.},
                 {D.x(),D.y(), D.x()*D.x() + D.y()*D.y(), 1.}};

        return Determinant.det(det, 4) > 0;
    }

    protected List<Triangle> flip(Triangle T, int side){

        Triangle neighbor = T.neighbor(side);

        Point A = T.vertex(side),     B = T.vertex(side + 1),
              C = T.vertex(side + 2), D = findNeighborPoint(T, side);

        Triangle T1 = new Triangle(C, B, D),
                 T2 = new Triangle(C, A, D);

        removeTriangle(T);
        removeTriangle(neighbor);
        addTriangle(T1);
        addTriangle(T2);

        return Arrays.asList(T1, T2);

    }

    protected Point findNeighborPoint(Triangle T, int side){
        Triangle neighbor = T.neighbor(side);

        for(int j  = 0; j < 3; j ++){
            if(neighbor.neighbor(j) == T) {
                return neighbor.vertex(j + 2);
            };
        }
        return null;
    }


}
