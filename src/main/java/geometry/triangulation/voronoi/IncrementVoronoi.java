package geometry.triangulation.voronoi;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.triangulation.IncrementDelaunay;
import geometry.triangulation.Triangle;
import geometry.utils.DrawHelper;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import static geometry.utils.VectorOperations.*;

/**
 * Created by banamir on 04.04.17.
 */
public class IncrementVoronoi extends IncrementDelaunay {



    protected HashMap<Point,TreeSet<Triangle>> vertexMap = new HashMap();

    protected HashSet<VoronoiTile> tiles = new HashSet();

    public IncrementVoronoi(List<Point> points){
        super();
        triangulate(points);

        for(Entry entry : vertexMap.entrySet()){
            Point vertex = (Point) entry.getKey();
            TreeSet<Triangle>  Ts = (TreeSet<Triangle>) entry.getValue();

            List<VoronoiVertex> vs = new ArrayList<>();
            Point outVertex = Ts.first().centroidCoord();

            for(Triangle T : Ts){

                Point c = T.centroidCoord();

                vs.add(new VoronoiVertex(c, VoronoiVertex.VertexType.INNER));

                for(int i = 0; i <3; i++) {
                    if(T.vertex(i) == vertex){
                        Point p = addOutPoint(vs,T,i - 1);
                        if(p != null) outVertex = p;
                        addOutPoint(vs,T, i);
                        if(p != null) outVertex = p;
                        break;
                    }
                }
            }

            final Point  base = outVertex;
            vs.sort((VoronoiVertex v1, VoronoiVertex v2)-> {
                Point  a = diff(v1.getVertex(),vertex),
                       b = diff(v2.getVertex(),vertex),
                       c = diff(base, vertex);
                double angle1 = Math.atan2(vectProduct(a, c), scalarProduct(a, c));
                if(angle1 < 0) angle1 += 2.*Math.PI;
                double angle2 = Math.atan2(vectProduct(b, c),scalarProduct(b, c));
                if(angle2 < 0) angle2 += 2.*Math.PI;
                return Double.compare(angle1,angle2);
            });

            tiles.add(new VoronoiTile(vertex,vs));
        }
    }


    private Point addOutPoint(List<VoronoiVertex> vs,Triangle T,int i){
        if(T.getNeighbor(i) != null) return null;

        Segment side = T.side(i);
        Point c = T.centroidCoord(),
              s = side.start(), e = side.end();
        Point m  = mult(add(e, s), 0.5),
              sc = diff(c, s), cm = diff(m, c);

        double mu = (T.contains(c))? 1 : -1;
        Point p = (length(cm) < 0.1E-3)
                ? diff(c, norm(mult(sc,mu)))
                : add(c,mult(cm, mu * length(sc) / length(cm)));


        vs.add(new VoronoiVertex(p, VoronoiVertex.VertexType.OUTER));

        return p;
    }


    public VoronoiTile tile(Point p){
        for(VoronoiTile tile : tiles){
            if(tile.contains(p)) return tile;
        }
        return null;
    }

    public HashSet<VoronoiTile> getTiles(){
        return tiles;
    }


    @Override
    protected void addTriangle(Triangle T) {
        super.addTriangle(T);

        for(int i =0; i<3; i++) {
            Point v = T.vertex(i);

            TreeSet<Triangle> Ts;
            if (vertexMap.containsKey(v)) {
                Ts = vertexMap.get(v);
            } else {
                Ts = new TreeSet<Triangle>((T1, T2) -> {
                    Segment l1 = new Segment(v, T1.centroidCoord()),
                            l2 = new Segment(v, T2.centroidCoord());
                    return Double.compare(l1.atan(), l2.atan());
                });
                vertexMap.put(v, Ts);
            }

            Ts.add(T);
        }
    }

    @Override
    protected void removeTriangle(Triangle T){
        super.removeTriangle(T);

        for(int i = 0; i < 3; i++) {
            Point v = T.vertex(i);
            TreeSet<Triangle> Ts = vertexMap.get(v);

            Ts.remove(T);
            if(Ts.isEmpty()) {
                vertexMap.remove(v);
            }
        }

    }

    public static void drawTile(Draw draw, List<VoronoiVertex> tile){

        //for(VoronoiVertex vertex)
    }

    public static void main(String[] args) {
        List<Point> verteses = new ArrayList();

        verteses.add(new Point(250,200)); //3
        verteses.add(new Point(375,25));  //1
        verteses.add(new Point(500,150)); //2

//
        verteses.add(new Point(400,225)); //4
//
        verteses.add(new Point(600,300)); //5
        verteses.add(new Point(375,300)); //6
        verteses.add(new Point(270,300)); //7

        verteses.add(new Point(420,350)); //8
        verteses.add(new Point(380,450)); //9

        Collections.shuffle(verteses);

        IncrementVoronoi triangulation = new IncrementVoronoi(verteses);

        Draw draw = new Draw();
        draw.setCanvasSize(800, 600);
        draw.setXscale(0, 800);
        draw.setYscale(0, 600);

        draw.setPenColor(Color.GREEN);

        Set<Segment> edges = new HashSet();
        for(Triangle T : triangulation){
            for(int i = 0; i < 3; i++) {
                edges.add(T.side(i));
            }
            Point c = T.centroidCoord();
           // draw.circle(c.x(),c.y(), length(diff(c,T.vertex(0))));
            System.out.println(c.toString());
        }

        List<Segment> segments = new ArrayList<>();
        segments.addAll(edges);

        draw.setPenColor(Color.RED);
        DrawHelper.drawSegments(draw, segments);

        draw.setPenColor(Color.BLUE);
        DrawHelper.drawPoints(draw, verteses, 5.);

        draw.setPenColor(Color.MAGENTA);
        for(VoronoiTile tile : triangulation.getTiles()){
            List<VoronoiVertex> lv = new ArrayList<>(tile.voronoiVertices());
            for(int i =0; i < lv.size()-1; i++){
                Point v1 = lv.get(i).getVertex();
                Point v2 = lv.get(i+1).getVertex();
                //draw.filledCircle(v1.x(),v1.y(),5.);
                draw.line(v1.x(),v1.y(),v2.x(),v2.y());
            }
            if(lv.get(0).getType() == VoronoiVertex.VertexType.INNER){
                Point v1 = lv.get(0).getVertex();
                Point v2 = lv.get(lv.size()-1).getVertex();
                draw.line(v1.x(),v1.y(),v2.x(),v2.y());
            }
        }



    }


}
