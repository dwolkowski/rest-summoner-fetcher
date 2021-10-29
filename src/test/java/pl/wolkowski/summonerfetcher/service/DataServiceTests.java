package pl.wolkowski.summonerfetcher.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import pl.wolkowski.summonerfetcher.model.summoner.Summoner;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataServiceTests {

    private final DataService dataService = new DataService();
    private final HttpHeaders header = getHeader();


    // getSummoner method
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

        Summoner summoner = dataService.getSummoner("validUser", restTemplate);

        Assertions.assertSame(SummonerState.EXISTING, summoner.getSummonerState());
        Assertions.assertEquals(validSummoner, summoner);
    }

    // getSummoner method
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

        Summoner summoner = dataService.getSummoner("invalidUser", restTemplate);

        Assertions.assertNull(summoner);
    }

    private HttpHeaders getHeader() {
        HttpHeaders header = new HttpHeaders();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("riotToken.txt"));
            String riotToken = reader.readLine();
            header.set("X-Riot-Token", riotToken);
        }catch (IOException e){
            System.out.println("File \"riotToken.txt\" not found");
            return null;
        }

        return header;
    }
}
