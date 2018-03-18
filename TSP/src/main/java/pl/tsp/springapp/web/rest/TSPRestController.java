package pl.tsp.springapp.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tsp.springapp.dto.TSP;
import pl.tsp.springapp.service.TSPService;


@RestController
@Scope("request")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/tsp")
public class TSPRestController {
  private final TSPService tspService;

  @Autowired
  public TSPRestController(TSPService tspService) {
    this.tspService = tspService;
  }

  @RequestMapping(method = RequestMethod.POST)
  private ResponseEntity setRoute(@RequestBody TSP tsp){
    try {
      this.tspService.setRoute(tsp);
      return new ResponseEntity(tsp.getPoints(), HttpStatus.OK);
    }
    catch(Exception e){
      e.printStackTrace();
      return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
