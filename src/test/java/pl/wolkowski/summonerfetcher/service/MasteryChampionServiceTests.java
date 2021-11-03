package pl.wolkowski.summonerfetcher.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import pl.wolkowski.summonerfetcher.model.champion.ChampionAdapter;
import pl.wolkowski.summonerfetcher.model.mastery.Mastery;
import pl.wolkowski.summonerfetcher.model.summoner.Summoner;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;

public class MasteryChampionServiceTests {

    private final HttpHeaders header = Header.get();
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final MasteryChampionService masteryChampionService  = new MasteryChampionService(new SummonerService(), new ChampionAdapter());

    private final Summoner validSummoner = new Summoner(
            SummonerState.EXISTING,
            "validUser",
            1,
            "testId",
            "testAccouintId",
            "testPuuid"
    );

    private final Mastery validMasteryChampion = new Mastery(
            SummonerState.EXISTING,
            "Annie",
            1,
            1,
            1,
            false
    );

    @Test
    @DisplayName("Return EXISTING champion mastery for valid championId.")
    void testGetChampionMastery_ValidChampionId_ReturnsMastery(){
        Mockito.when(restTemplate.exchange(
                "https://eun1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries"
                        + "/by-summoner/" + "testId"
                        + "/by-champion/" + "1",
                HttpMethod.GET,
                new HttpEntity<>(header),
                new ParameterizedTypeReference<Mastery>() {})
        ).thenReturn(new ResponseEntity<>(validMasteryChampion, HttpStatus.OK));

        Mastery testMasteryChampion = masteryChampionService.getChampionMastery(validSummoner,"1",  restTemplate);

        Assertions.assertSame(SummonerState.EXISTING, testMasteryChampion.getSummonerState());
        Assertions.assertEquals(validMasteryChampion, testMasteryChampion);

    }

    @Test
    @DisplayName("Return EXISTING champion mastery for valid championName.")
    void testGetChampionMastery_ValidChampionName_ReturnsMastery(){
        Mockito.when(restTemplate.exchange(
                "https://eun1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries"
                        + "/by-summoner/" + "testId"
                        + "/by-champion/" + "1",
                HttpMethod.GET,
                new HttpEntity<>(header),
                new ParameterizedTypeReference<Mastery>() {})
        ).thenReturn(new ResponseEntity<>(validMasteryChampion, HttpStatus.OK));

        Mastery testMasteryChampion = masteryChampionService.getChampionMastery(validSummoner,"invalidChampion",  restTemplate);

        Assertions.assertSame(SummonerState.EXISTING, testMasteryChampion.getSummonerState());
        Assertions.assertEquals(validMasteryChampion, testMasteryChampion);

    }

    @Test
    @DisplayName("Return NULL for invalid champion.")
    void testGetChampionMastery_InvalidChampionName_ReturnsNull(){
        Mockito.when(restTemplate.exchange(
                "https://eun1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries"
                        + "/by-summoner/" + "testId"
                        + "/by-champion/" + "-1",
                HttpMethod.GET,
                new HttpEntity<>(header),
                new ParameterizedTypeReference<Mastery>() {})
        ).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        Mastery testMasteryChampion = masteryChampionService.getChampionMastery(validSummoner,"Anniee",  restTemplate);

        Assertions.assertNull(testMasteryChampion);

    }
}
