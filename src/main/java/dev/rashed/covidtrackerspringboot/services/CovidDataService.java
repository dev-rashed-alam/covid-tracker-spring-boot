package dev.rashed.covidtrackerspringboot.services;

import dev.rashed.covidtrackerspringboot.config.Config;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class CovidDataService {

    @PostConstruct
    public void fetchData() throws IOException {
        StringBuilder responseContent = new StringBuilder();
        URL url = new URL(Config.API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (bufferedReader.readLine() != null) {
                responseContent.append(bufferedReader.readLine());
            }
            bufferedReader.close();
        }
        System.out.println(responseContent.toString());
    }
}
