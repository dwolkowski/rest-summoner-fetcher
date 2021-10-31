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
import pl.wolkowski.summonerfetcher.model.summoner.Summoner;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;

@Service
public class MasteryChampionService {

    private final HttpHeaders header = Header.get();
    private final ChampionAdapter championAdapter;
    private final SummonerService summonerService;
    public MasteryChampionService(SummonerService summonerService, ChampionAdapter championAdapter){
        this.summonerService = summonerService;
        this.championAdapter = championAdapter;
    }

    /**
     * Get parsed details about users mastery of chosen champion from RiotGames API.
     * @param username A user from whom the data will be fetched.
     * @param champion The name or id of the champion which the data will be fetched.
     * @return A {@link Mastery} that will contain details about users mastery of chosen champion.
     */
    public Mastery getChampionMastery(String username, String champion) {
        return getChampionMastery(username, champion, new RestTemplate());
    }

    /**
     * Get parsed details about users mastery of chosen champion
     * from RiotGames API from a given {@link RestTemplate}.
     * @param username A user from whom the data will be fetched.
     * @param champion The name or id of the champion which the data will be fetched.
     * @return A {@link Mastery} that will contain details about users mastery of chosen champion.
     */
    public Mastery getChampionMastery(String username, String champion, RestTemplate restTemplate) {
        Summoner summoner = summonerService.getSummoner(username);
        Mastery mastery = new Mastery();
        int championId;

        try {
            championId = Integer.parseInt(champion);

        } catch (NumberFormatException e) {
            championId = championAdapter.getChampionIdByName(champion);
        }

        if (summoner.getSummonerState() == SummonerState.NOT_FOUND
                || mastery.getChampionId() == -1) {
            mastery.setSummonerState(SummonerState.NOT_FOUND);
            return mastery;
        }

        try {
            ResponseEntity<Mastery> masteryResponseEntity = restTemplate.exchange(
                    "https://eun1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries"
                            + "/by-summoner/" + summoner.getId()
                            + "/by-champion/" + championId,
                    HttpMethod.GET,
                    new HttpEntity<>(header),
                    new ParameterizedTypeReference<>() {
                    }
            );

            mastery = masteryResponseEntity.getBody();

            if (mastery != null)
                mastery.setChampionName(championAdapter.getChampionNameById(mastery.getChampionId()));

        } catch (HttpStatusCodeException e) {
            mastery.setSummonerState(SummonerState.NOT_FOUND);
        }

        return mastery;
    }

}
