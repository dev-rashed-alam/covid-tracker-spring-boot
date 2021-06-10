package dev.rashed.covidtrackerspringboot.services;

import dev.rashed.covidtrackerspringboot.config.Config;
import dev.rashed.covidtrackerspringboot.models.LocationStats;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class CovidDataService {

    public List<LocationStats> allLocationStats = new ArrayList<>();

    public List<LocationStats> getAllLocationStats() {
        allLocationStats.sort(new Comparator<LocationStats>() {
            @Override
            public int compare(LocationStats o1, LocationStats o2) {
                return Integer.compare(o2.getActive(), o1.getActive());
            }
        });
        return allLocationStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * * 1 * *")
    public void fetchData() throws IOException {
        List<LocationStats> locationStatsList = new ArrayList<>();
        BufferedReader bufferedReader;
        URL url = new URL(Config.API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection.getResponseCode() == 200) {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(bufferedReader);
            for (CSVRecord record : records) {
                LocationStats locationStats = new LocationStats();
                locationStats.setState(record.get("Province_State"));
                locationStats.setCountry(record.get("Country_Region"));
                if (!record.get("Confirmed").equals("")) {
                    locationStats.setConfirmedCase(Integer.parseInt(record.get("Confirmed")));
                }
                if (!record.get("Deaths").equals("")) {
                    locationStats.setDeaths(Integer.parseInt(record.get("Deaths")));
                }
                if (!record.get("Recovered").equals("")) {
                    locationStats.setRecovered(Integer.parseInt(record.get("Recovered")));
                }
                if (!record.get("Active").equals("")) {
                    locationStats.setActive(Integer.parseInt(record.get("Active")));
                } else {
                    locationStats.setActive(0);
                }
                locationStatsList.add(locationStats);
            }
            this.allLocationStats = locationStatsList;
            bufferedReader.close();
        }
        connection.disconnect();
    }
}
