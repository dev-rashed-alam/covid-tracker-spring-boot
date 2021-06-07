package dev.rashed.covidtrackerspringboot.services;

import dev.rashed.covidtrackerspringboot.config.Config;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
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
//    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException {
        StringBuilder responseContent = new StringBuilder();
        BufferedReader bufferedReader;
        URL url = new URL(Config.API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection.getResponseCode() == 200) {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(bufferedReader);
            for (CSVRecord record : records) {
                String state = record.get("Province_State");
                System.out.println(state);
            }
            bufferedReader.close();
        }
        connection.disconnect();
    }
}
