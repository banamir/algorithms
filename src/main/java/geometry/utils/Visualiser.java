package geometry.utils;

import geometry.dto.*;
import geometry.dto.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static geometry.dto.Segment.segment;

public class Visualiser extends JFrame {

  private final Graphics2D g2;
  private final JPanel panel;

  public Visualiser() {
    Container content = getContentPane();
    content.setLayout(new BorderLayout());
    panel = new JPanel();
    panel.setPreferredSize(new Dimension(800, 600));
    content.add(panel, BorderLayout.CENTER);
    setTitle("Visualiser");
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    this.g2 = (Graphics2D) panel.getGraphics();
    g2.setStroke(new BasicStroke(1));
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
  }

  public Visualiser drawLines(List<Segment> lines, Color color) {
    g2.setColor(color);
    Line2D l = new Line2D.Double();
    for (Segment line : lines) {
      l.setLine(line.startX(), line.startY(), line.endX(), line.endY());
      g2.draw(l);
    }
    return this;
  }

  public Visualiser drawLines(List<Segment> lines) {
    return drawLines(lines, Color.BLACK);
  }

  public Visualiser draw(List<Point> subject) {
    return draw(subject, Color.BLACK);
  }

  public Visualiser draw(List<Point> subject, Color color, int thickness) {
    g2.setStroke(new BasicStroke(thickness));
    return draw(subject, color);
  }

  public Visualiser draw(List<Point> subject, Color color) {
    if (subject.isEmpty()) return this;
    List<Segment> lines = new ArrayList<>();
    Point cur, prev = subject.get(0);

    for (int i = 1; i < subject.size(); i++) {
      cur = subject.get(i);
      lines.add(segment(prev.x(), prev.y(), cur.x(), cur.y()));
      prev = cur;
    }

    cur = subject.get(0);
    lines.add(segment(prev.x(), prev.y(), cur.x(), cur.y()));
    drawLines(lines, color);
    return this;
  }


  public Visualiser drawPoints(List<Point> points, double radius){
    return drawPoints(points, radius, Color.BLACK);
  }

  public Visualiser drawPoints(List<Point> points, double radius, Color color) {
    if(points.isEmpty()) return this;

    g2.setColor(color);

    for(Point point: points){
      g2.fill(new Ellipse2D.Double(point.x() - radius, point.y() - radius, 2*radius, 2*radius));
    }

    return this;
  }
}
