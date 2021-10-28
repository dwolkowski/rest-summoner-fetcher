package pl.wolkowski.summonerfetcher.model.champion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ChampionAdapter {
    Map<Integer, String> championMap;
    BufferedReader reader;

    public ChampionAdapter() {
        try {
            reader = new BufferedReader(new FileReader("src/main/java/pl/wolkowski/summonerfetcher/model/champion/champions.txt"));
            List<String> list = reader.lines().collect(Collectors.toList());
            championMap = new TreeMap<>();
            String[] splitted;

            for (String line : list) {
                splitted = line.split("=");
                championMap.put(Integer.parseInt(splitted[0]), splitted[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File \"champions.txt\" not found.");
        }
    }

    public String getChampionNameById(int championId) {
        return championMap.getOrDefault(championId, "Unknow champion");
    }

}
