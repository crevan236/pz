package pl.tsp.springapp.service;

import org.springframework.stereotype.Service;
import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.TSP;
import pl.tsp.springapp.service.algorithm.ExtendedGreedyAlgorithmService;
import pl.tsp.springapp.service.algorithm.GeneticAlgorithmService;
import pl.tsp.springapp.service.algorithm.GreedyAlgorithmService;
import pl.tsp.springapp.service.algorithm.PermutationAlgorithmService;

import java.util.List;
import java.util.Queue;


@Service
public class TSPService {

  public List<Point> setRoute(TSP tsp) throws Exception {
    switch(tsp.getAlgorithm()) {
      case "GREEDY" :
        return new GreedyAlgorithmService().manageAlgorithm(tsp.getPoints());
      case "GREEDYS":
        return new ExtendedGreedyAlgorithmService().manageAlgorithm(tsp.getPoints());
      case "PERM":
        return new PermutationAlgorithmService().manageAlgorithm(tsp.getPoints());
        case "GENETIC":
        return new GeneticAlgorithmService().manageAlgorithm(tsp.getPoints());
      default:
        return null;
    }
  }
}
