package pl.tsp.springapp.service.algorithm.greedy;

import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.service.algorithm.AlgorithmService;

import java.util.List;

public class GreedyAlgorithmService extends AlgorithmService {

  public void setRoute(List<Point> points){
    System.out.println("GreedyAlgorithmService");
    for(Point tmpPoint : points){

      if(!tmpPoint.getRoutes().isEmpty())
        continue;

      double minDistance = 0;
      Point minPoint = null;
      for(Point tmpPoint2 : points){

        if(tmpPoint.equals(tmpPoint2))
          continue;

        double distance = calcDistance(tmpPoint, tmpPoint2);
        //System.out.println(distance);
        if(minDistance == 0 || distance < minDistance) {
          minDistance = distance;
          minPoint = tmpPoint2;
        }
      }
      //System.out.println("minDistance "+minDistance);

      if(minPoint != null)
        tmpPoint.getRoutes().add(minPoint.getName());
    }
  }
}
