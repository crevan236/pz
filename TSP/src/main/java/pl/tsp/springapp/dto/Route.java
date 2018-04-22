package pl.tsp.springapp.dto;

import java.util.ArrayList;
import java.util.List;

public class Route {
  private double length;
  private double fitness = 0;
  private List<Point> points;
  private boolean correctRoute = true;

  public Route() {
  }

  public Route(int routeSize) {
    this.points = new ArrayList<>();
    for (int i = 0; i < routeSize; i++) {
      this.points.add(null);
    }
  }

  public Route(List<Point> points) {
    this.points = points;
  }

  public double getLength() throws Exception {
    if (length == 0) {
      int tourDistance = 0;
      for (int i = 0; i < points.size(); i++) {
        Point startPoint = points.get(i);
        Point endPoint;
        if (i + 1 < points.size()) {
          endPoint = points.get(i + 1);
        } else {
          endPoint = points.get(0);
        }
        tourDistance += startPoint.calcDistance(endPoint);
      }
      length = tourDistance;
    }
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public List<Point> getPoints() {
    return points;
  }

  public void setPoints(List<Point> points) {
    this.points = points;
  }

  public void setPoint(int index, Point point) {
    this.points.set(index, point);
  }

  public Point getPoint(int index) {
    return this.points.get(index);
  }

  public double getFitness() throws Exception {
    if (fitness == 0) {
      fitness = 1 / getLength();
    }
    return fitness;
  }

  public void setFitness(double fitness) {
    this.fitness = fitness;
  }

  public boolean isCorrectRoute() {
    return correctRoute;
  }

  public void setCorrectRoute(boolean correctRoute) {
    this.correctRoute = correctRoute;
  }

  @Override
  public String toString() {
    String string = "";
    for (Point tmpPoint : points) {
      string += tmpPoint.toString();
      string += " | ";
    }

    return string;
  }
}
