package com.bridgelabz.censusanalyser.model;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(IndiaStateCensusCSV indiaStateCensusCSV) {
        populationDensity = indiaStateCensusCSV.populationDensity;
        totalArea = indiaStateCensusCSV.totalArea;
        population = indiaStateCensusCSV.population;
        state = indiaStateCensusCSV.state;
    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        totalArea = usCensusCSV.totalArea;
        populationDensity = usCensusCSV.populationDensity;
        stateCode = usCensusCSV.StateId;
    }
}
