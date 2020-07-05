package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.model.CensusDAO;
import com.bridgelabz.censusanalyser.utility.CensusDataLoader;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;

/**
 * @Author : Amrut
 * Purpose : Analyse the census data for country and perform sort operation
 */
public class CensusAnalyser {

    public enum Country {
        INDIA, US;
    }
    Map<String, CensusDAO> csvFileMap;
    List<CensusDAO> censusDAOList;

    public CensusAnalyser() {

        this.csvFileMap = new HashMap<>();
        this.censusDAOList = new ArrayList<>();
    }
    /**
     * Function to load the country census data from csv file
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadCountryCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        csvFileMap = new CensusDataLoader().loadCensusData(country, csvFilePath);
        return csvFileMap.size();
    }

    /**
     * Function to sort indie census state wise
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusData(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        censusDAOList = csvFileMap.values().stream()
                                           .sorted(((Comparator<CensusDAO>)
                                            (census1, census2) -> census2.state.compareTo(census1.state)).reversed())
                                           .collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }

    /**
     *
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getStateCodeWiseSortedData(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        if (censusDAOList == null || censusDAOList.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        censusDAOList = csvFileMap.values().stream()
                                           .sorted(((Comparator<CensusDAO>) (census1, census2) -> census2
                                           .stateCode.compareTo(census1.stateCode)).reversed())
                                           .collect(Collectors.toList());
        String sortedStateCodeData = new Gson().toJson(censusDAOList);
        return sortedStateCodeData;
    }

    /**
     *
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationWiseSortedCensusData(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(censusComparator);
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }

    /**
     *
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationDensityWiseSortedCensusData(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = comparing(census -> census.populationDensity);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(censusComparator);
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }

    /**
     *
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getAreaWiseSortedCensusData(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(censusComparator);
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }

    /**
     *
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationWiseSortedCensusDataForUS(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(censusComparator);
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }

    /**
     *
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusDataForUS(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(censusComparator);
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }

    /**
     *
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getDensityWiseSortedCensusDataForUS(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.populationDensity);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(censusComparator);
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }

    /**
     *
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getAreaWiseSortedCensusDataForUS(Country country, String csvFilePath) throws CensusAnalyserException {
        loadCountryCensusData(country, csvFilePath);
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(censusComparator);
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }

    /**
     *
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getHighestPopulationDensityStateFromIndiaAndUS(Country country, String csvFilePath)
            throws CensusAnalyserException
    {
        this. loadCountryCensusData(country, csvFilePath);
        CensusDAO[] indiaCensusList = new Gson().fromJson(this.getPopulationDensityWiseSortedCensusData(country, csvFilePath), CensusDAO[].class);
        this. loadCountryCensusData(country, csvFilePath);
        CensusDAO[] usCensusList = new Gson().fromJson(this.getDensityWiseSortedCensusDataForUS(country, csvFilePath), CensusDAO[].class);
        if (Double.compare(indiaCensusList[indiaCensusList.length - 1].populationDensity, usCensusList[usCensusList.length - 1].populationDensity) > 0)
            return indiaCensusList[indiaCensusList.length - 1].state;
        else
            return usCensusList[usCensusList.length - 1].state;
    }
}

