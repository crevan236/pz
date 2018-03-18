package pl.tsp.springapp.service;

import org.springframework.stereotype.Service;
import pl.tsp.springapp.dto.TSP;
import pl.tsp.springapp.service.algorithm.greedy.GreedyAlgorithmService;
import pl.tsp.springapp.service.algorithm.permutation.PermutationAlgorithmService;


@Service
public class TSPService {

  public void setRoute(TSP tsp) {
    switch(tsp.getAlgorithm()) {
      case "GREEDY" :
        new GreedyAlgorithmService().setRoute(tsp.getPoints());
        break;
      case "GREEDYS":
        break;
      case "PERM":
        new PermutationAlgorithmService().setRoute(tsp.getPoints());
        break;
    }
  }
}
