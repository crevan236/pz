package pl.tsp.springapp.service.algorithm;

import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Route;

import java.util.*;

public class PermutationAlgorithmService extends AlgorithmService {
  Map<Integer, Route> routesMap = new HashMap<>();
  int counter = 0;

  protected Map<Integer, Route> setRoute(List<Point> points) throws Exception {
    System.out.println("PermutationAlgorithmService");

    if (points.size() > 10)
      throw new Exception("Za duża liczba punktów");

    perm(points, 0);

    System.out.println(routesMap.size());

    return routesMap;
  }

  private void perm(List<Point> points, int n) {
    if (n >= points.size() - 1) {
      Queue<Point> route = new LinkedList<>();

      for (Point tmpPoint : points) {
        route.offer(tmpPoint);
      }

      route.offer(route.element());
      routesMap.put(counter, new Route(route));
      ++this.counter;
    } else {
      for (int i = n; i < points.size(); i++) {
        Collections.swap(points, i, n);
        perm(points, n + 1);
        Collections.swap(points, i, n);
      }
    }
  }
}
