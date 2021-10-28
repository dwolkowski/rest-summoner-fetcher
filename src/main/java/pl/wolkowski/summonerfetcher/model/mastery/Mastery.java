package pl.wolkowski.summonerfetcher.model.mastery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import pl.wolkowski.summonerfetcher.model.summoner.SummonerState;

@AllArgsConstructor
@NoArgsConstructor
@Data

@JsonComponent
public class Mastery {

    @JsonIgnore
    SummonerState summonerState = SummonerState.EXISTING;

    String championName = "";

    @JsonProperty("championId")
    int championId = 0;

    @JsonProperty("championLevel")
    int championLevel;

    @JsonProperty("championPoints")
    int championPoints;

    @JsonProperty("chestGranted")
    boolean chestGranted;


}
