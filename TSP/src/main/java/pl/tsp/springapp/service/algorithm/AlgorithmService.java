package pl.tsp.springapp.service.algorithm;

import org.springframework.stereotype.Service;
import pl.tsp.springapp.dto.Point;

import java.util.List;

@Service
public abstract class AlgorithmService {

  public abstract void setRoute(List<Point> points);

  protected double calcDistance(Point point1, Point point2) {
    double xDistance = Math.pow(point2.getX() - point1.getX(), 2);
    double yDistance = Math.pow(point2.getY() - point1.getY(), 2);

    return Math.sqrt(xDistance + yDistance);
  }
}
