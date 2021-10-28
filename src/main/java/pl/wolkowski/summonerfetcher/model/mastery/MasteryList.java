package pl.wolkowski.summonerfetcher.model.mastery;

import lombok.Data;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;

import java.util.Collections;
import java.util.List;

@Data
public class MasteryList {

    List<Mastery> masteries = Collections.emptyList();

    SummonerState summonerState = SummonerState.EXISTING;

}
