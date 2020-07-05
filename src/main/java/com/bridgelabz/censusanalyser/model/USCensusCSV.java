package com.bridgelabz.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    public USCensusCSV() {
        
    }

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "State Id", required = true)
    public String StateId;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Total area", required = true)
    public Double totalArea;

    @CsvBindByName(column = "Population Density", required = true)
    public Double populationDensity;

    public USCensusCSV(String state, String stateId, int population, double populationDensity, double totalArea) {
        this.state = state;
        StateId = stateId;
        this.population = population;
        this.populationDensity = populationDensity;
        this.totalArea = totalArea;
    }
}
