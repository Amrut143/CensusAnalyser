package com.bridgelabz.censusanalyser.dao;

import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusCSV;
import com.bridgelabz.censusanalyser.utility.Country;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;

    /**
     *
     * @param indiaStateCensusCSV
     */
    public CensusDAO(IndiaStateCensusCSV indiaStateCensusCSV) {
        state = indiaStateCensusCSV.state;
        population = indiaStateCensusCSV.population;
        totalArea = indiaStateCensusCSV.areaInSqKm;
        populationDensity = indiaStateCensusCSV.densityPerSqKm;
    }

    /**
     *
     * @param usCensusCSV
     */
    public CensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        totalArea = usCensusCSV.totalArea;
        populationDensity = usCensusCSV.populationDensity;
        stateCode = usCensusCSV.StateId;
    }

    /**
     *
     * @param state
     * @param population
     * @param totalArea
     * @param populationDensity
     * @param stateCode
     */
    public CensusDAO(String state, int population, double totalArea, double populationDensity, String stateCode) {
        this.state = state;
        this.population = population;
        this.totalArea = totalArea;
        this.populationDensity = populationDensity;
        this.stateCode = stateCode;
    }

    /**
     *
     * @param country
     * @return
     */
    public Object getCensusDTOS(Country country) {
        if (country.equals(Country.US))
            return new USCensusCSV(state, stateCode, population, populationDensity, totalArea);
        return new IndiaStateCensusCSV(state, population, (int) populationDensity, (int) totalArea);
    }
}
