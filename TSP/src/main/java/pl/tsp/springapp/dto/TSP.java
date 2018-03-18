package pl.tsp.springapp.dto;

import java.io.Serializable;
import java.util.List;

public class TSP implements Serializable {
  private String algorithm;
  private List<Point> points;

  public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public List<Point> getPoints() {
    return points;
  }

  public void setPoints(List<Point> points) {
    this.points = points;
  }
}
