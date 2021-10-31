package pl.wolkowski.summonerfetcher.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import pl.wolkowski.summonerfetcher.model.summoner.Summoner;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;

@Service
public class SummonerService {

    HttpHeaders header = Header.get();

    /**
     * Get parsed data of a provided user from the RiotGames API.
     * @param username A user from whom the data will be fetched.
     * @return A {@link Summoner} that will contain data about user.
     */
    public Summoner getSummoner(String username) {
        return getSummoner(username, new RestTemplate());
    }

    /**
     * Get parsed data of a provided user from the RiotGames API from a given {@link RestTemplate}.
     * @param username A user from whom the data will be fetched.
     * @return A {@link Summoner} that will contain data about user.
     */
    public Summoner getSummoner(String username, RestTemplate restTemplate){
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

}
