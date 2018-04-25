package pl.tsp.springapp.service.algorithm;

import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Population;
import pl.tsp.springapp.dto.Route;

import java.util.*;

public class PermutationAlgorithmService extends AlgorithmService {
  Map<Integer, Route> routesMap = new HashMap<>();
  int counter = 0;

  protected Population setRoute(List<Point> points) throws Exception {
    System.out.println("PermutationAlgorithmService");

    if (points.size() > 10)
      throw new Exception("Za duża liczba punktów");

    perm(points, 0);

    Population population = new Population(routesMap.size());
    for (Map.Entry<Integer, Route> entry : routesMap.entrySet()) {
      population.setRoute(entry.getKey(), entry.getValue());
    }

    checkRoute(population);

    return population;
  }

  private void perm(List<Point> points, int n) {
    if (n >= points.size() - 1) {
      List<Point> route = new ArrayList<>();

      for (Point tmpPoint : points) {
        route.add(tmpPoint);
      }

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
