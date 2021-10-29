package pl.wolkowski.summonerfetcher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wolkowski.summonerfetcher.model.mastery.Mastery;
import pl.wolkowski.summonerfetcher.model.mastery.MasteryList;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;
import pl.wolkowski.summonerfetcher.service.DataService;

@RestController
public class MasteryController {

    DataService dataService = new DataService();

    /**
     * Returns a JSON response with a full list of champion mastery details
     * of every champion for provided user.
     * @param username A user from whom the data will be fetched.
     * @return A full list of champion mastery details for provided user.
     */
    @GetMapping(path = "/maestry/{username}")
    ResponseEntity<MasteryList> getMaestry(@PathVariable("username") String username) {
        MasteryList maestryBatch = dataService.getMasteryListFromUsername(username);

        if (maestryBatch.getSummonerState() == SummonerState.NOT_FOUND)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(maestryBatch);
    }

    /**
     * Returns a JSON response with champion mastery details of chosen champion for provided user.
     * @param username A user from whom the data will be fetched.
     * @param championId The id of the champion which the data will be fetched.
     * @return A champion mastery details of chosen champion for provided user.
     */
    @GetMapping(path = "/maestry/{username}/{championId}")
    ResponseEntity<Mastery> getMaestry(@PathVariable("username") String username, @PathVariable("championId") int championId) {
        Mastery maestry = dataService.getChampionMasteryFromUsername(username, championId);

        if (maestry.getSummonerState() == SummonerState.NOT_FOUND)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(maestry);
    }
}
