package pl.tsp.springapp.service.algorithm;

import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Route;

import java.util.*;

public class GreedyAlgorithmService extends AlgorithmService {
  @Override
  protected Map<Integer, Route> setRoute(List<Point> points) throws Exception {
    Map<Integer, Route> routesMap = new HashMap<>();
    for (int i = 0; i < points.size(); i++) {
      try {
        routesMap.put(i, new Route(greedyAlg(points, i)));
      } catch (Exception ex) {
        ex.printStackTrace();
        continue;
      }
    }

    return routesMap;
  }

  private Queue<Point> greedyAlg(List<Point> points, int start) throws Exception {
    List<Point> tmpPoints = clonePoints(points);
    Queue<Point> route = new LinkedList<>();

    int i = start;
    tmpPoints.get(start).setVisited(true);
    route.offer(tmpPoints.get(start));
    while (route.size() != tmpPoints.size()) {
      int min = findNextPoint(tmpPoints, i);

      tmpPoints.get(min).setVisited(true);
      Point tmpPoint = tmpPoints.get(min);

      route.offer(tmpPoint);
      i = min;
    }

    route.offer(route.element());
    return route;
  }

  private int findNextPoint(List<Point> points, int start) throws Exception {
    int min = 0;
    double minDistance = 0;
    boolean findNextPoint = false, existRoute;
    for (int i = 0; i < points.size(); i++) {
      if (i != start && !points.get(i).isVisited()) {
        existRoute = false;
        for (Integer tmpRoute : points.get(start).getRoutes()) {
          if (i == tmpRoute)
            existRoute = true;
        }
        double tmpDistance = calcDistance(points.get(start), points.get(i));

        if (existRoute && minDistance == 0 || tmpDistance < minDistance) {
          min = i;
          minDistance = tmpDistance;
          findNextPoint = true;
        }

      }
    }
    if (!findNextPoint)
      throw new Exception("Nie znaleziono żadnej drogi dla punktu " + start);

    return min;
  }

  private List<Point> clonePoints(List<Point> points) {
    List<Point> tmpPoints = new ArrayList<Point>(points.size());
    for (Point tmpPoint : points)
      tmpPoints.add(new Point(tmpPoint));
    return tmpPoints;
  }
}
