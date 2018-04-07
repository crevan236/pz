package pl.tsp.springapp.service.algorithm;

import org.springframework.stereotype.Service;
import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Route;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Service
public abstract class AlgorithmService {

  public Queue<Point> manageAlgorithm(List<Point> points) throws Exception {
    Map<Integer, Route> routeMap = setRoute(points);
    calcRoute(routeMap);
    return findMinRoute(routeMap);
  }

  protected abstract Map<Integer, Route> setRoute(List<Point> points) throws Exception;

  protected double calcDistance(Point point1, Point point2) throws Exception {
    double xDistance = Math.pow(point2.getX() - point1.getX(), 2);
    double yDistance = Math.pow(point2.getY() - point1.getY(), 2);

    return Math.sqrt(xDistance + yDistance);
  }

  protected void calcRoute(Map<Integer, Route> routesMap) throws Exception {
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

  protected Queue<Point> findMinRoute(Map<Integer, Route> routesMap) throws Exception {
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
}
