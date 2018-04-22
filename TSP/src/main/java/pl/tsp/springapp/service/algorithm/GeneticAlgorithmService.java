package pl.tsp.springapp.service.algorithm;

import pl.tsp.springapp.dto.Point;
import pl.tsp.springapp.dto.Population;
import pl.tsp.springapp.dto.Route;

import java.util.*;

public class GeneticAlgorithmService extends AlgorithmService {
  private final double mutationRate = 0.015;
  private final int tournamentSize = 5;
  private final boolean elitism = false;
  private final int loops = 100;

  @Override
  protected Population setRoute(List<Point> points) throws Exception {
    System.out.println("GeneticAlgorithmService");

    Population population = new Population(createNewPopulation(points));

    population = evolvePopulation(population);
    for(int i = 0; i < loops; i++) {
      population = evolvePopulation(population);
    }

    checkRoute(population);
    return population;
  }

  private Population evolvePopulation(Population population) throws Exception {
    Population newPopulation = new Population(population.getRoutes().length);

    int elitismOffset = 0;
    if(elitism) {
      newPopulation.setRoute(0, findMinRoute(population));
      elitismOffset = 1;
    }

    for(int i = elitismOffset; i < newPopulation.getRoutes().length; i++) {
      Route parent1 = routeSelection(population);
      Route parent2 = routeSelection(population);

      Route children = crossover(parent1, parent2, population.getRoutes().length);

      newPopulation.setRoute(i, children);
    }

    for(int i = elitismOffset; i < newPopulation.getRoutes().length; i++) {
      mutate(newPopulation.getRoute(i));
    }

    return newPopulation;
  }

  private Route[] createNewPopulation(List<Point> points) throws Exception {
    Route[] routes = new Route[points.size()];
    for(int i = 0; i < points.size(); i++) {
      Route newRoute = new Route();
      newRoute = createIndividual(points);
      routes[i] = newRoute;
    }
    return routes;
  }

  private Route createIndividual(List<Point> points) throws Exception {
    List<Point> newPoints = new ArrayList<>();
    for(int i = 0; i < points.size(); i++) {
      newPoints.add(points.get(i));
    }

    Collections.shuffle(newPoints);
    return new Route(new ArrayList<>(newPoints));
  }

  private Route routeSelection(Population population) throws Exception {
    Population newPopulation = new Population(tournamentSize);
    for(int i = 0; i < tournamentSize; i++) {
      int randomId = (int) (Math.random() * population.getRoutes().length);
      newPopulation.setRoute(i, population.getRoute(randomId));
    }

    return findMinRoute(population);
  }

  private Route crossover(Route parent1, Route parent2, int routeSize) {
    Route children = new Route(routeSize);

    List<Point> parent1Points = new ArrayList<>(parent1.getPoints());
    List<Point> parent2Points = new ArrayList<>(parent2.getPoints());

    int startPos = (int) (Math.random() * parent1.getPoints().size());
    int endPos = (int) (Math.random() * parent2.getPoints().size());

    for(int i = 0; i < children.getPoints().size(); i++) {
      if(startPos < endPos && i > startPos && i < endPos) {
        children.setPoint(i, parent1Points.get(i));
      }
      else if(startPos > endPos) {
        if(!(i < startPos && i > endPos)) {
          children.setPoint(i, parent1Points.get(i));
        }
      }
    }

    for(int i = 0; i < parent2Points.size(); i++) {
      if(!children.getPoints().contains(parent2Points.get(i))) {
        for(int j = 0; j < children.getPoints().size(); j++) {
          if(children.getPoint(j) == null) {
            children.setPoint(j, parent2Points.get(i));
            break;
          }
        }
      }
    }

    return children;
  }

  private void mutate(Route route) {
    List<Point> points = new ArrayList<>(route.getPoints());

    for(int position1 = 0; position1 < points.size(); position1++) {
      if(Math.random() < mutationRate) {

        int position2 = (int) (points.size() * Math.random());

        Point point1 = points.get(position1);
        Point point2 = points.get(position2);

        points.set(position2, point1);
        points.set(position1, point2);
      }
    }

    route.setPoints(new ArrayList<>(points));
  }
}
