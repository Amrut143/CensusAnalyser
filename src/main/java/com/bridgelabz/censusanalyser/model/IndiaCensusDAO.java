package com.bridgelabz.censusanalyser.model;

public class IndiaCensusDAO {
    public String state;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;
    public String stateCode;

    public IndiaCensusDAO(IndiaStateCensusCSV indiaStateCensusCSV) {
        densityPerSqKm = indiaStateCensusCSV.densityPerSqKm;
        areaInSqKm = indiaStateCensusCSV.areaInSqKm;
        population = indiaStateCensusCSV.population;
        state = indiaStateCensusCSV.state;
    }
}
