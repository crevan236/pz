package pl.tsp.springapp.dto;

public class Population {
  private Route[] routes;


  public Population() {
  }

  public Population(int size) {
    routes = new Route[size];
  }

  public Population(Route[] routes) {
    this.routes = routes;
  }

  public Route[] getRoutes() {
    return routes;
  }

  public Route getRoute(int index) {
    return routes[index];
  }

  public void setRoutes(Route[] routes) {
    this.routes = routes;
  }

  public void setRoute(int index, Route route) {
    this.routes[index] = route;
  }
}
