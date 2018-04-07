package pl.tsp.springapp.dto;

import java.util.Queue;

public class Route {
  private double length;
  private Queue<Point> points;

  public Route(Queue<Point> points) {
    this.points = points;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public Queue<Point> getPoints() {
    return points;
  }

  public void setPoints(Queue<Point> points) {
    this.points = points;
  }
}
