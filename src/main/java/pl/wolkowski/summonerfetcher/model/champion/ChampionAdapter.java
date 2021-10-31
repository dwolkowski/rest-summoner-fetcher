package pl.wolkowski.summonerfetcher.model.champion;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ChampionAdapter {
    Map<Integer, String> championMapById;
    Map<String, Integer> championMapByName;
    BufferedReader reader;

    public ChampionAdapter() {
        try {
            reader = new BufferedReader(new FileReader("src/main/java/pl/wolkowski/summonerfetcher/model/champion/champions.txt"));
            List<String> list = reader.lines().collect(Collectors.toList());
            championMapById = new HashMap<>();
            championMapByName = new HashMap<>();
            String[] splitted;

            for (String line : list) {
                splitted = line.split("=");
                championMapById.put(Integer.parseInt(splitted[0]), splitted[1]);
                championMapByName.put(splitted[1].toLowerCase(), Integer.parseInt(splitted[0]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File \"champions.txt\" not found.");
        }
    }

    public String getChampionNameById(int championId) {
        return championMapById.getOrDefault(championId, "Unknow champion");
    }

    // Returns -1 if champion is unknown.
    public int getChampionIdByName(String championName) {
        championName = championName.toLowerCase();
        return championMapByName.getOrDefault(championName, -1);
    }

}
