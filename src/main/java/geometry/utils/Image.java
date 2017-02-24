package geometry.utils;

import geometry.dto.Point;
import geometry.dto.Segment;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static geometry.dto.Segment.segment;

public class Image {

  private final String filename;
  private final Graphics2D g2;
  private final BufferedImage image;

  private Image(String filename, int width, int height) {
    this.filename = filename;
    this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    this.g2 = image.createGraphics();
    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, width, height);
    g2.setStroke(new BasicStroke(2));
  }

  public static Image create(String filename) {
    return new Image(filename, 800, 600);
  }

  public static Image create(String filename, int width, int height) {
    return new Image(filename, width, height);
  }

  public Image draw(List<Segment> lines, Color color) {
    g2.setColor(color);
    Line2D l = new Line2D.Double();
    for (Segment line : lines) {
      l.setLine(line.startX(), line.startY(), line.endX(), line.endY());
      g2.draw(l);
    }
    return this;
  }

  public Image drawPolygon(List<Point> subject, Color color) {
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
    return draw(lines, color);
  }

  public void save() {
    g2.dispose();
    try {
      ImageIO.write(image, "PNG", new File(filename));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
