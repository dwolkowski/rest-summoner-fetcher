package pl.wolkowski.summonerfetcher.model.mastery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;

import java.util.Collections;
import java.util.List;

@Data
public class MasteryList {

    List<Mastery> masteries = Collections.emptyList();

    @JsonIgnore
    SummonerState summonerState = SummonerState.EXISTING;

}
