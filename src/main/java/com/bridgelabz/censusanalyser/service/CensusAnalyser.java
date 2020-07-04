package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.model.CensusDAO;
import com.bridgelabz.censusanalyser.model.USCensusCSV;
import com.bridgelabz.censusanalyser.utility.CensusDataLoader;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;

/**
 * @Author : Amrut
 * Purpose : Read the indian state census data from csv file
 */
public class CensusAnalyser {

    Map<String, CensusDAO> csvFileMap;
    List<CensusDAO> censusDAOList;

    public CensusAnalyser() {

        this.csvFileMap = new HashMap<>();
        this.censusDAOList = new ArrayList<>();
    }
    /**
     * Function to load the india census data from csv file
     *
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
       csvFileMap = new CensusDataLoader().loadCensusData(IndiaStateCensusCSV.class, csvFilePath);
       return csvFileMap.size();
    }

    /**
     * Function to load US census data
     * @param csvFilePath
     * @return
     */
    public int loadUSCensusData(String... csvFilePath) throws CensusAnalyserException {
        csvFileMap = new CensusDataLoader().loadCensusData(USCensusCSV.class, csvFilePath);
        return csvFileMap.size();
    }


    /**
     * Function to sort indie census state wise
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        censusDAOList = csvFileMap.values().stream()
                                           .sorted(((Comparator <CensusDAO>)
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
    public String getStateCodeWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
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
    public String getPopulationWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
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
    public String getPopulationDensityWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
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
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getAreaWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
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
     * @param usCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getPopulationWiseSortedCensusDataForUS(String usCsvFilePath) throws CensusAnalyserException {
        loadUSCensusData(usCsvFilePath);
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
     * @param usCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusDataForUS(String usCsvFilePath) throws CensusAnalyserException {
        loadUSCensusData(usCsvFilePath);
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
     * @param usCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getDensityWiseSortedCensusDataForUS(String usCsvFilePath) throws CensusAnalyserException {
        loadUSCensusData(usCsvFilePath);
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
     * @param usCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getAreaWiseSortedCensusDataForUS(String usCsvFilePath) throws CensusAnalyserException {
        loadUSCensusData(usCsvFilePath);
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
     * @param indiaCsvFilePath
     * @param usCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getHighestPopulationDensityStateFromIndiaAndUS(String indiaCsvFilePath, String usCsvFilePath)
            throws CensusAnalyserException
    {
        this.loadIndiaCensusData(indiaCsvFilePath);
        CensusDAO[] indiaCensusList = new Gson().fromJson(this.getPopulationDensityWiseSortedCensusData(indiaCsvFilePath), CensusDAO[].class);
        this.loadUSCensusData(usCsvFilePath);
        CensusDAO[] usCensusList = new Gson().fromJson(this.getDensityWiseSortedCensusDataForUS(usCsvFilePath), CensusDAO[].class);
        if (Double.compare(indiaCensusList[indiaCensusList.length - 1].populationDensity, usCensusList[usCensusList.length - 1].populationDensity) > 0)
            return indiaCensusList[indiaCensusList.length - 1].state;
        else
            return usCensusList[usCensusList.length - 1].state;
    }
}

