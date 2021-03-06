package pl.tsp.springapp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Point implements Serializable {
  private String name;
  private double x;
  private double y;
  private boolean visited;
  private List<String> routes = new ArrayList<>();


  public Point() {
  }

  public Point(Point point) {
    this.name = point.getName();
    this.x = point.getX();
    this.y = point.getY();
    this.visited = point.isVisited();
    this.routes = point.getRoutes();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public List<String> getRoutes() {
    return routes;
  }

  public void setRoutes(List<String> routes) {
    this.routes = routes;
  }

  public double calcDistance(Point point) throws Exception {
    double xDistance = Math.pow(getX() - point.getX(), 2);
    double yDistance = Math.pow(getY() - point.getY(), 2);

    return Math.sqrt(xDistance + yDistance);
  }

  @Override
  public String toString() {
    return " name='" + name + '\'' +
      ", x=" + x +
      ", y=" + y;
  }
}
