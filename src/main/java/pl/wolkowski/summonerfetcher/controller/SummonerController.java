package pl.wolkowski.summonerfetcher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wolkowski.summonerfetcher.model.summoner.Summoner;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;
import pl.wolkowski.summonerfetcher.service.DataService;

@RestController
public class SummonerController {

    DataService dataService = new DataService();

    /**
     * Returns a JSON response with detailed data about user.
     * @param username A user from whom the data will be fetched.
     * @return Detailed data about user.
     */
    @GetMapping(path = "/summoner/{username}")
    ResponseEntity<Summoner> getSummoner(@PathVariable("username") String username) {
        Summoner summoner = dataService.getSummoner(username);

        if(summoner.getSummonerState() == SummonerState.NOT_FOUND)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(summoner);
    }

}
