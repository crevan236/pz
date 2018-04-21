package pl.tsp.springapp.service.algorithm;

import org.springframework.stereotype.Service;
import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Route;

import java.util.*;

@Service
public abstract class AlgorithmService {

  public Queue<Point> manageAlgorithm(List<Point> points) throws Exception {
    Map<Integer, Route> routeMap = setRoute(points);
    checkRoute(routeMap);
    return findMinRoute(routeMap);
  }

  protected abstract Map<Integer, Route> setRoute(List<Point> points) throws Exception;

  protected double calcDistance(Point point1, Point point2) throws Exception {
    double xDistance = Math.pow(point2.getX() - point1.getX(), 2);
    double yDistance = Math.pow(point2.getY() - point1.getY(), 2);

    return Math.sqrt(xDistance + yDistance);
  }

  protected void checkRoute(Map<Integer, Route> routesMap) throws Exception {
    for(Map.Entry<Integer, Route> entry : routesMap.entrySet()){
      Route route = entry.getValue();
      double distance = 0;
      for(Point tmpPoint : entry.getValue().getPoints()){
        for(Point tmpPoint2 : entry.getValue().getPoints()) {
          if(!tmpPoint.equals(tmpPoint2))
            distance += calcDistance(tmpPoint, tmpPoint2);
        }
      }
      route.setLength(distance);
    }
  }

  private Queue<Point> findMinRoute(Map<Integer, Route> routesMap) throws Exception {
    Queue<Point> minRoute = new LinkedList<>();
    double minDistance = 0;
    for(Map.Entry<Integer, Route> entry : routesMap.entrySet()) {
      Route route = entry.getValue();
      if(minDistance == 0 || route.getLength() < minDistance) {
        minDistance = route.getLength();
        minRoute = route.getPoints();
      }
    }
    return minRoute;
  }

  protected List<Point> clonePoints(List<Point> points) {
    List<Point> tmpPoints = new ArrayList<>(points.size());
    for (Point tmpPoint : points)
      tmpPoints.add(new Point(tmpPoint));
    return tmpPoints;
  }
}
