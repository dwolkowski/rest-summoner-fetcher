package pl.wolkowski.summonerfetcher.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import pl.wolkowski.summonerfetcher.model.champion.ChampionAdapter;
import pl.wolkowski.summonerfetcher.model.mastery.Mastery;
import pl.wolkowski.summonerfetcher.model.mastery.MasteryList;
import pl.wolkowski.summonerfetcher.model.summoner.Summoner;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;

import java.util.List;

@Service
public class MasteryListService {

    private final HttpHeaders header = Header.get();
    private final ChampionAdapter championAdapter;
    private final SummonerService summonerService;

    public MasteryListService(SummonerService summonerService, ChampionAdapter championAdapter){
        this.summonerService = summonerService;
        this.championAdapter = championAdapter;
    }

    /**
     * Get parsed list of champion mastery details for every champion of
     * provided user from the RiotGames REST API.
     * @param username A user from whom the data will be fetched.
     * @return A {@link MasteryList} that will contain details about champion mastery of every champion.
     */
    public MasteryList getMasteryListFromUsername(String username) {
        return getMasteryListFromUsername(username, new RestTemplate());
    }

    /**
     * Get parsed list of champion mastery details for every champion of
     * provided user from the RiotGames REST API from a given {@link RestTemplate}.
     * @param username A user from whom the data will be fetched.
     * @return A {@link MasteryList} that will contain details about champion mastery of every champion.
     */
    public MasteryList getMasteryListFromUsername(String username, RestTemplate restTemplate){
        Summoner summoner = summonerService.getSummoner(username);
        MasteryList masteryList = new MasteryList();

        try {
            ResponseEntity<List<Mastery>> masteryListResponseEntity = restTemplate.exchange(
                    "https://eun1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + summoner.getId(),
                    HttpMethod.GET,
                    new HttpEntity<>(header),
                    new ParameterizedTypeReference<>() {
                    }
            );
            masteryList.setMasteries(masteryListResponseEntity.getBody());

        } catch (HttpStatusCodeException e) {
            masteryList.setSummonerState(SummonerState.NOT_FOUND);
            return masteryList;
        }

        for (Mastery o : masteryList.getMasteries()) {
            o.setChampionName(championAdapter.getChampionNameById(o.getChampionId()));
        }

        return masteryList;
    }

}
