package pl.tsp.springapp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Point implements Serializable {
  private String name;
  private double x;
  private double y;
  private List<String> routes = new ArrayList<>();


  public Point() {
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

  public List<String> getRoutes() {
    return routes;
  }

  public void setRoutes(List<String> routes) {
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
