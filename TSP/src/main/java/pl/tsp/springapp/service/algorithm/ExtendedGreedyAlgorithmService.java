package pl.tsp.springapp.service.algorithm;

import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendedGreedyAlgorithmService extends GreedyAlgorithmService {
  @Override
  protected Map<Integer, Route> setRoute(List<Point> points) throws Exception {
    Map<Integer, Route> routesMap = new HashMap<>();
    for (int i = 0; i < points.size(); i++) {
      try {
        routesMap.put(i, new Route(super.greedyAlg(points, i)));
      } catch (Exception ex) {
        ex.printStackTrace();
        continue;
      }
    }
    return routesMap;
  }
}
