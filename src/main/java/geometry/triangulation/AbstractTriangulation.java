package geometry.triangulation;

import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.graphs.EuclideanGraph;
import geometry.triangulation.utils.Determinant;
import geometry.utils.Visualiser;

import java.util.*;

/**
 * Created by banamir on 04.03.17.
 */
public abstract class AbstractTriangulation implements Iterable<Triangle> {

    private Set<Triangle> triangles = new HashSet();

    protected HashMap<Point,Set<Triangle>> vertexMap = new HashMap();

    public AbstractTriangulation(List<Point> points){
        triangulate(points);
    }

    abstract protected void triangulate(List<Point> points);

    public Iterator<Triangle> iterator(){
        return triangles.iterator();
    }

    public EuclideanGraph graph(){

        EuclideanGraph graph = new EuclideanGraph();

        for(Point v : vertises()){
            List<Triangle> triangles = getTriangles(v);
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

    public Iterable<Point> vertises(){
        return vertexMap.keySet();
    }


    protected void addTriangle(Triangle T){

        for(int i =0; i < 3; i++){

            Segment side = T.side(i);
            List<Triangle> list = new ArrayList();

            for(Triangle t : triangles){
                for(int j = 0; j < 3; j++){

                    Segment s = t.side(j);

                    if(side.equals(s) || side.equals(new Segment(s.end(),s.start()))){
                        list.add(t);
                        break;
                    }
                }
            }
            list.remove(T);

            if(!list.isEmpty()) { //TODO: if list has a size larger than one
                Triangle neighbor = list.get(0);
                T.setNeighbor(i, neighbor);

                for(int j = 0; j < 3; j++ ){
                    Segment s = neighbor.side(j);
                    if(side.equals(s) || side.equals(new Segment(s.end(), s.start())) ){
                        neighbor.setNeighbor(j,T);
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
            Triangle neighbor = T.getNeighbor(i);
            if(neighbor != null){
                for(int j = 0; j < 3; j++){
                    if(neighbor.getNeighbor(j) == T) neighbor.setNeighbor(j, null);
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

    public List<Triangle> getTriangles(Point p){
        List<Triangle> result = new ArrayList();

        if(vertexMap.containsKey(p))
            return new ArrayList<>(vertexMap.get(p));

        for(Triangle T : triangles){
            if(T.contains(p)) result.add(T); ;
        }

        return result;
    }

    protected boolean contains(Triangle T){
        return triangles.contains(T);
    }

    protected  boolean canImprove(Triangle T, int side){

        if(T.getNeighbor(side) == null) return false;

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
        Triangle neighbor = T.getNeighbor(side);

        Point A = T.vertex(side), B = T.vertex(side + 1),
              C = T.vertex(side + 2), D = findNeighborPoint(T, side);

        removeTriangle(T);
        removeTriangle(neighbor);
        Triangle T1 = new Triangle(C, B, D),
                 T2 = new Triangle(C, A, D);
        addTriangle(T1);
        addTriangle(T2);

        return Arrays.asList(T1, T2);

    }

    private Point findNeighborPoint(Triangle T, int side){
        Triangle neighbor = T.getNeighbor(side);
        Point D = null;
        for(int j  = 0; j < 3; j ++){
            if(neighbor.getNeighbor(j) == T) {
                D = neighbor.vertex(j + 2);
                break;
            };
        }
        return D;
    }


}
