package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.model.CensusDAO;
import com.bridgelabz.censusanalyser.utility.CensusDataLoader;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.utility.SortByField;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : Amrut
 * Purpose : Analyse the census data for country and perform sort operation
 */
public class CensusAnalyser {

    public enum Country {
        INDIA, US;
    }

    private Country country;
    Map<String, CensusDAO> csvFileMap;

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public CensusAnalyser() {
        this.csvFileMap = new HashMap<>();
    }
    /**
     * Function to load the country census data from csv file
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadCountryCensusData(String... csvFilePath) throws CensusAnalyserException {
        csvFileMap = new CensusDataLoader().loadCensusData(country, csvFilePath);
        return csvFileMap.size();
    }

    /**
     *
     * @param parameter
     * @return
     * @throws CensusAnalyserException
     */
    public String getSortedCensusDataBasedOnField(SortByField.Parameter parameter) throws CensusAnalyserException {
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }

        Comparator<CensusDAO> censusComparator = SortByField.getParameter(parameter);
        ArrayList censusDAOList = csvFileMap.values().stream()
                .sorted(censusComparator)
                .map(censusDAO -> censusDAO.getCensusData(country))
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDAOList);
        return sortedStateCensusJson;
    }

    /**
     *
     * @return
     * @throws CensusAnalyserException
     */
    public String getHighestPopulationDensityStateFromIndiaAndUS() throws CensusAnalyserException {
        loadCountryCensusData("./src/test/resources/IndiaStateCensusData.csv");
        CensusDAO[] indiaCensusList = new Gson().fromJson(this.getSortedCensusDataBasedOnField(SortByField.Parameter.DENSITY), CensusDAO[].class);
        loadCountryCensusData("./src/test/resources/IndiaStateCensusData.csv");
        CensusDAO[] usCensusList = new Gson().fromJson(this.getSortedCensusDataBasedOnField(SortByField.Parameter.DENSITY), CensusDAO[].class);
        if (Double.compare(indiaCensusList[indiaCensusList.length - 1].populationDensity, usCensusList[usCensusList.length - 1].populationDensity) > 0)
            return indiaCensusList[indiaCensusList.length - 1].state;
        else
            return usCensusList[usCensusList.length - 1].state;
    }
}


