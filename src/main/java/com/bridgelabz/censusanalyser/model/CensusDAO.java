package com.bridgelabz.censusanalyser.model;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(IndiaStateCensusCSV next) {
        populationDensity = next.populationDensity;
        totalArea = next.totalArea;
        population = next.population;
        state = next.state;
    }

    public CensusDAO(USCensusCSV next) {
        state = next.State;
        population = next.Population;
        totalArea = next.totalArea;
        populationDensity = next.populationDensity;
        stateCode = next.StateId;
    }
}
