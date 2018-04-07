package pl.tsp.springapp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Point implements Serializable {
  private String name;
  private double x;
  private double y;
  private boolean visited;
  private List<Integer> routes = new ArrayList<>();


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

  public List<Integer> getRoutes() {
    return routes;
  }

  public void setRoutes(List<Integer> routes) {
    this.routes = routes;
  }

  @Override
  public String toString() {
    return "Point{" +
      "name='" + name + '\'' +
      ", x=" + x +
      ", y=" + y +
      ", routes=" + routes +
      '}';
  }
}
