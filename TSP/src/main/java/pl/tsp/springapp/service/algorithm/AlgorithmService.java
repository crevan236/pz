package pl.tsp.springapp.service.algorithm;

import org.springframework.stereotype.Service;
import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Population;
import pl.tsp.springapp.dto.Route;

import java.util.*;

@Service
public abstract class AlgorithmService {

  public List<Point> manageAlgorithm(List<Point> points) throws Exception {
    System.out.println(new Route(points).getLength());

    if(points.size() <= 2)
      return points;

    Population population = setRoute(points);
    Route minRoute = findMinRoute(population);
    System.out.println(minRoute.getLength());
    System.out.println("********************");

    addEndPoint(minRoute);

    return minRoute.getPoints();
  }

  protected abstract Population setRoute(List<Point> points) throws Exception;

  protected Route findMinRoute(Population population) throws Exception {
    Route fittest = null;
    boolean exist = false;

    for (int i = 0; i < population.getRoutes().length; i++) {
      if(population.getRoute(i) == null)
        continue;

      if (fittest == null || (fittest != null && fittest.getFitness() <= population.getRoute(i).getFitness())) {
        if (population.getRoute(i).isCorrectRoute()) {
          fittest = population.getRoute(i);
          exist = true;
        }
      }
    }

    if (!exist)
      throw new Exception("Brak poprawnej trasy!");

    return fittest;
  }

  protected void checkRoute(Population population) throws Exception {
    for (Route tmpRoute : population.getRoutes()) {
      boolean correctRoute = true;
      for (Point tmpPoint : tmpRoute.getPoints()) {
        boolean isRoute = true;
        for (Point tmpNextPoint : tmpRoute.getPoints()) {
          boolean isCorrectNextPoint = false;
          if (!tmpPoint.equals(tmpNextPoint)) {
            for (String availableRoutes : tmpPoint.getRoutes()) {
              if (availableRoutes.equals(tmpNextPoint.getName())) {
                isCorrectNextPoint = true;
                break;
              }
            }
            if(!isCorrectNextPoint) {
              isRoute = isCorrectNextPoint;
              break;
            }
          }
        }
        if (!isRoute)
          correctRoute = false;
      }
      tmpRoute.setCorrectRoute(correctRoute);
    }
  }

  private void addEndPoint(Route route) throws Exception {
    route.getPoints().add(route.getPoint(0));
  }

  protected List<Point> clonePoints(List<Point> points) {
    List<Point> tmpPoints = new ArrayList<>(points.size());
    for (Point tmpPoint : points)
      tmpPoints.add(new Point(tmpPoint));

    return tmpPoints;
  }
}
