package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.adapter.CensusAdapter;
import com.bridgelabz.censusanalyser.adapter.CensusAdapterFactory;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.utility.Country;
import com.bridgelabz.censusanalyser.utility.SortByField;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author : Amrut
 * Purpose : Analyse the census data for country and perform sort operation
 */
public class CensusAnalyser {

    Map<String, CensusDAO> csvFileMap;
    private CensusAdapter censusAdapter;
    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public CensusAnalyser() {
        this.csvFileMap = new HashMap<>();
    }

    public void setCensusAdapter(CensusAdapter censusAdapter) {
        this.censusAdapter = censusAdapter;
    }

    /**
     * Function to load the country census data from csv file
     *
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadCountryCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        csvFileMap = censusAdapter.loadCensusData(country, csvFilePath);
        return csvFileMap.size();
    }

    /**
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
                .map(censusDAO -> censusDAO.getCensusDTOS(country))
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDAOList);
        return sortedStateCensusJson;
    }

    /**
     * @return
     * @throws CensusAnalyserException
     */
    public String getHighestPopulationDensityStateFromIndiaAndUS(String... csvFilePath) throws CensusAnalyserException {
        this.loadCountryCensusData(Country.INDIA, csvFilePath[0], csvFilePath[1]);
        CensusDAO[] indiaCensusList = new Gson().fromJson(this.getSortedCensusDataBasedOnField(SortByField.Parameter.DENSITY), CensusDAO[].class);
        this.loadCountryCensusData(Country.US, csvFilePath[2]);
        CensusDAO[] usCensusList = new Gson().fromJson(this.getSortedCensusDataBasedOnField(SortByField.Parameter.DENSITY), CensusDAO[].class);
        if (Double.compare(indiaCensusList[indiaCensusList.length - 1].populationDensity, usCensusList[usCensusList.length - 1].populationDensity) > 0)
            return indiaCensusList[indiaCensusList.length - 1].state;
        else
            return usCensusList[usCensusList.length - 1].state;
    }
}


