package pl.wolkowski.summonerfetcher.model.summoner;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Summoner {
    @JsonIgnore
    private SummonerState summonerState = SummonerState.EXISTING;

    @JsonProperty("name")
    private String name;

    @JsonProperty("summonerLevel")
    private int summonerLevel;

    @JsonProperty("id")
    private String id;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("puuid")
    private String puuid;

}
