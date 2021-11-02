package pl.wolkowski.summonerfetcher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.wolkowski.summonerfetcher.model.mastery.Mastery;
import pl.wolkowski.summonerfetcher.model.mastery.MasteryList;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;
import pl.wolkowski.summonerfetcher.service.MasteryListService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MasteryChestController {

    private final MasteryListService masteryListService;

    public MasteryChestController(MasteryListService masteryListService){
        this.masteryListService = masteryListService;
    }

    /**
     * Returns a JSON response with a full list of champion where the chest was acquired.
     * for provided user.
     * @param username A user from whom the data will be fetched.
     * @return A list of champion where the chest was acquired for provided user.
     */
    @GetMapping("mastery/{username}/chest/acquired")
    ResponseEntity<List<Mastery>> getMasteryChestAcquired(@PathVariable("username") String username){
        MasteryList masteryList = masteryListService.getMasteryListFromUsername(username);

        if(masteryList.getSummonerState() == SummonerState.NOT_FOUND)
            return ResponseEntity.notFound().build();

        List<Mastery> championChestList = new ArrayList<>();
        for(Mastery champion : masteryList.getMasteries())
            if(champion.isChestGranted())
                championChestList.add(champion);

        return ResponseEntity.ok(championChestList);
    }

    /**
     * Returns a JSON response with a full list of champion where the chest is available
     * for provided user.
     * @param username A user from whom the data will be fetched.
     * @return A list of champion where the chest is available for provided user.
     */
    @GetMapping("mastery/{username}/chest/available")
    ResponseEntity<List<Mastery>> getMasteryChestAvailable(@PathVariable("username") String username){
        MasteryList masteryList = masteryListService.getMasteryListFromUsername(username);

        if(masteryList.getSummonerState() == SummonerState.NOT_FOUND)
            return ResponseEntity.notFound().build();

        List<Mastery> championChestList = new ArrayList<>();
        for(Mastery champion : masteryList.getMasteries())
            if(!champion.isChestGranted())
                championChestList.add(champion);

        return ResponseEntity.ok(championChestList);
    }

}
