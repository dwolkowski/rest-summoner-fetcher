package pl.wolkowski.summonerfetcher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import pl.wolkowski.summonerfetcher.model.champion.ChampionAdapter;
import pl.wolkowski.summonerfetcher.model.mastery.*;
import pl.wolkowski.summonerfetcher.model.summoner.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class DataService {

    ChampionAdapter championAdapter = new ChampionAdapter();
    Logger logger = LoggerFactory.getLogger(DataService.class);
    HttpHeaders header = getHeader();

    /**
     * Get parsed data of a provided user from the RiotGames API.
     * @param username A user from whom the data will be fetched.
     * @return A {@link Summoner} that will contain data about user.
     */
    public Summoner getSummoner(String username) {
        RestTemplate restTemplate = new RestTemplate();
        Summoner summoner = new Summoner();

        try {
            ResponseEntity<Summoner> summonerResponseEntity = restTemplate.exchange(
                    "https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + username,
                    HttpMethod.GET,
                    new HttpEntity<>(header),
                    new ParameterizedTypeReference<>() {
                    }
            );
            summoner = summonerResponseEntity.getBody();

        } catch (HttpStatusCodeException e) {
            summoner.setSummonerState(SummonerState.NOT_FOUND);
        }

        return summoner;
    }

    /**
     * Get parsed details about users mastery of chosen champion from RiotGames API.
     * @param username A user from whom the data will be fetched.
     * @param championId The id of the champion which the data will be fetched.
     * @return A {@link Mastery} that will contain details about users mastery of chosen champion.
     */
    public Mastery getChampionMasteryFromUsername(String username, int championId) {
        Summoner summoner = getSummoner(username);
        Mastery mastery = new Mastery();
        RestTemplate restTemplate = new RestTemplate();

        if(summoner.getSummonerState() == SummonerState.NOT_FOUND){
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
            mastery.setChampionName(championAdapter.getChampionNameById(championId));

        } catch (HttpStatusCodeException e) {
            mastery.setSummonerState(SummonerState.NOT_FOUND);
        }

        return mastery;
    }

    /**
     * Get parsed list of champion mastery details for every champion of
     * provided user from the RiotGames REST API.
     * @param username A user from whom the data will be fetched.
     * @return A {@link MasteryList} that will contain details about champion mastery of every champion.
     */

    public MasteryList getMasteryListFromUsername(String username) {
        Summoner summoner = getSummoner(username);
        MasteryList masteryList = new MasteryList();
        RestTemplate restTemplate = new RestTemplate();

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

    /**
     * Get HttpHeader which contains RiotGames API key from riotToken.txt file.
     * @return A {@link HttpHeaders} which will contain the key required to use the RiotGames API.
     */
    private HttpHeaders getHeader() {
        HttpHeaders header = new HttpHeaders();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("riotToken.txt"));
            String riotToken = reader.readLine();
            header.set("X-Riot-Token", riotToken);
        }catch (IOException e){
            logger.error("Riot API key not found.");
        }

        return header;
    }

}
