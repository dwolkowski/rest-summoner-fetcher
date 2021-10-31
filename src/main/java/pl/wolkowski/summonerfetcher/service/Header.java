package pl.wolkowski.summonerfetcher.service;

import org.springframework.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Header {

    /**
     * Get HttpHeader which contains RiotGames API key from "riotToken.txt" file.
     * @return A {@link HttpHeaders} which will contain the key required to access the RiotGames API.
     */
    protected static HttpHeaders get(){
        HttpHeaders header = new HttpHeaders();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("riotToken.txt"));
            String riotToken = reader.readLine();
            header.set("X-Riot-Token", riotToken);
        }catch (IOException e){
            System.out.println("Riot API key not found.");
        }

        return header;
    }
}
