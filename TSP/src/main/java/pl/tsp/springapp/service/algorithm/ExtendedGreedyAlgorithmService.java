package pl.tsp.springapp.service.algorithm;

import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Population;
import pl.tsp.springapp.dto.Route;

import java.util.List;

public class ExtendedGreedyAlgorithmService extends GreedyAlgorithmService {
  @Override
  protected Population setRoute(List<Point> points) throws Exception {
    System.out.println("ExtendedGreedyAlgorithmService");
    Population population = new Population(points.size());

    for (int i = 0; i < points.size(); i++) {
      try {
        population.setRoute(i, new Route(super.greedyAlg(points, i)));
      } catch (Exception ex) {
        ex.printStackTrace();
        continue;
      }
    }
    return population;
  }
}
