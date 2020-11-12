package com.example.exp3.model;

import lombok.Data;

@Data
public class RegionStates {
    private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDay;
}
