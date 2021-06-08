package dev.rashed.covidtrackerspringboot.models;

import lombok.Data;

@Data
public class LocationStats {
    private String state;

    private String country;

    private int confirmedCase;

    private int deaths;

    private int recovered;

    private int active;
}
