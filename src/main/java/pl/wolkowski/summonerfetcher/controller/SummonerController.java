package pl.wolkowski.summonerfetcher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.wolkowski.summonerfetcher.model.summoner.Summoner;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;
import pl.wolkowski.summonerfetcher.service.SummonerService;

@RestController
public class SummonerController {

    private final SummonerService summonerService;

    public SummonerController(SummonerService summonerService){
        this.summonerService = summonerService;
    }

    /**
     * Returns a JSON response with detailed data about user.
     * @param username A user from whom the data will be fetched.
     * @return Detailed data about user.
     */
    @GetMapping(path = "/summoner/{username}")
    ResponseEntity<Summoner> getSummoner(@PathVariable("username") String username) {
        Summoner summoner = summonerService.getSummoner(username, new RestTemplate());

        if(summoner.getSummonerState() == SummonerState.NOT_FOUND)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(summoner);
    }

}
