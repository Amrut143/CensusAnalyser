package com.bridgelabz.censusanalyser.model;

import com.bridgelabz.censusanalyser.service.CensusAnalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(IndiaStateCensusCSV indiaStateCensusCSV) {
        populationDensity = indiaStateCensusCSV.densityPerSqKm;
        totalArea = indiaStateCensusCSV.areaInSqKm;
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

    public Object getCensusData(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state, stateCode, population, populationDensity, totalArea);
        return new IndiaStateCensusCSV(state, population, (int) populationDensity, (int) totalArea);
    }
}
