package geometry.utils;


import geometry.dto.Point;
import geometry.dto.Segment;

import java.awt.geom.Point2D;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.StrictMath.sqrt;

/**
 * Created by banamir on 02.03.17.
 */
public class VectorOperations {

    public static Point diff(Point a, Point b){
        return new Point(a.x()-b.x(), a.y()-b.y());
    }

    public static double scalarProduct(Point a, Point b){
        return a.x()*b.x() +  a.y()*b.y();
    }

    public static double length(Point a){
        return sqrt(scalarProduct(a,a));
    }

    public static double square(Point a){
        return scalarProduct(a,a);
    }

    public static double vectProduct(Point a, Point b){
        return a.x()*b.x() +  a.y()*b.y();
    }

    public static boolean intersected(Segment s1, Segment s2){
        Point a = s1.start(), b = s1.end(),
              c = s2.start(), d = s2.end();

        Point ab = diff(a,b), ac = diff(a,c), ad = diff(a,d);
        Point cd = diff(c, d), ca = diff(c, a), cb = diff(c, b);

        return intersected(a.x(),b.x(),c.x(),d.x()) &&
                vectProduct(ab,ac) *  vectProduct(ab,ad) <=0  &&
                vectProduct(cd,ca) *  vectProduct(cd,cb) <=0;
    }

    public static Point intersectPoint(Segment s1, Segment s2) {
        if(!intersected(s1,s2)) return  null;

        Point a = s1.start(), b = s1.end(),
                c = s2.start(), d = s2.end();

        if(Double.compare(a.x(),b.x()) == 0 && Double.compare(a.y(),b.y()) == 0 )
            return a;
        if(Double.compare(c.x(),d.x()) == 0 && Double.compare(c.y(),d.y()) == 0 )
            return c;

        double A1 = b.y() - a.y(), A2 = d.y() - c.y(),
                B1 = a.x() - b.x(), B2 = c.x() - d.x(),
                C1 = A1 * a.x() + B1 * a.y(),
                C2 = A2 * c.x() + B2 * c.y(),
                D  = A1 * B2 - B1 * A2,
                D1 = C1 * B2 - B1 * C2,
                D2 = A1 * C2 - C1 * A2;
        if(Double.compare(D,0.) == 0) return null;

        return new Point(D1/D,D2/D);
    }

    public static boolean intersected(double a, double b, double c, double d) {
        double p;
        if(a > b) { p = a; a = b; b = p; }
        if(c > d) { p = c; c = d; d = p;  }
        return max(a,c) <= min(b,d);
    }

}
