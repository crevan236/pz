package pl.tsp.springapp.service.algorithm;

import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Route;

import java.util.*;

public class GreedyAlgorithmService extends AlgorithmService {
  @Override
  protected Map<Integer, Route> setRoute(List<Point> points) throws Exception {
    Map<Integer, Route> routesMap = new HashMap<>();

    try {
      routesMap.put(0, new Route(greedyAlg(points, 0)));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return routesMap;
  }

  protected Queue<Point> greedyAlg(List<Point> points, int start) throws Exception {
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
        for (String tmpRoute : points.get(start).getRoutes()) {
          if (points.get(i).getName().equals(tmpRoute))
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
}
