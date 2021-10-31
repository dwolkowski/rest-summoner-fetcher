package pl.wolkowski.summonerfetcher.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import pl.wolkowski.summonerfetcher.model.summoner.Summoner;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;


public class SummonerServiceTests {

    private final HttpHeaders header = Header.get();
    private final SummonerService summonerService = new SummonerService();

    @Test
    @DisplayName("Return EXISTING summoner for valid username.")
    void testGetSummoner_ValidNickname_ReturnsSummoner(){
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        Summoner validSummoner = new Summoner(
                SummonerState.EXISTING,
                "validUser",
                1,
                "testId",
                "testAccouintId",
                "testPuuid"
        );

        Mockito.when(restTemplate.exchange(
                "https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/validUser",
                HttpMethod.GET,
                new HttpEntity<>(header),
                new ParameterizedTypeReference<Summoner>() {})
        ).thenReturn(new ResponseEntity<>(validSummoner, HttpStatus.OK));

        Summoner summoner = summonerService.getSummoner("validUser", restTemplate);

        Assertions.assertSame(SummonerState.EXISTING, summoner.getSummonerState());
        Assertions.assertEquals(validSummoner, summoner);
    }

    @Test
    @DisplayName("Return null for invalid username.")
    void testGetSummoner_InvalidNickname_ReturnsSummoner(){
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        Mockito.when(restTemplate.exchange(
                "https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/invalidUser",
                HttpMethod.GET,
                new HttpEntity<>(header),
                new ParameterizedTypeReference<Summoner>() {})
        ).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        Summoner summoner = summonerService.getSummoner("invalidUser", restTemplate);

        Assertions.assertNull(summoner);
    }

}
