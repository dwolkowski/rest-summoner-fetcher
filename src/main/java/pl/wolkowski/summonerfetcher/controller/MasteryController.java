package pl.wolkowski.summonerfetcher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.wolkowski.summonerfetcher.model.mastery.Mastery;
import pl.wolkowski.summonerfetcher.model.mastery.MasteryList;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;
import pl.wolkowski.summonerfetcher.service.MasteryChampionService;
import pl.wolkowski.summonerfetcher.service.MasteryListService;

@RestController
public class MasteryController {

    private final MasteryChampionService masteryChampionService;
    private final MasteryListService masteryListService;

    public MasteryController(MasteryChampionService masteryChampionService, MasteryListService masteryListService){
        this.masteryChampionService = masteryChampionService;
        this.masteryListService = masteryListService;
    }

    /**
     * Returns a JSON response with a full list of champion mastery details
     * of every champion for provided user.
     * @param username A user from whom the data will be fetched.
     * @return A full list of champion mastery details for provided user.
     */
    @GetMapping(path = "/mastery/{username}")
    ResponseEntity<MasteryList> getMastery(@PathVariable("username") String username) {
        MasteryList masteryBatch = masteryListService.getMasteryListFromUsername(username, new RestTemplate());

        if (masteryBatch.getSummonerState() == SummonerState.NOT_FOUND)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(masteryBatch);
    }

    /**
     * Returns a JSON response with champion mastery details of chosen champion for provided user.
     * @param username A user from whom the data will be fetched.
     * @param champion The id or name of the champion which the data will be fetched.
     * @return A champion mastery details of chosen champion for provided user.
     */
    @GetMapping(path = "/mastery/{username}/{champion}")
    ResponseEntity<Mastery> getMastery(@PathVariable("username") String username, @PathVariable("champion") String champion) {
        Mastery mastery = masteryChampionService.getChampionMastery(username, champion);

        if (mastery.getSummonerState() == SummonerState.NOT_FOUND)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mastery);
    }

}
