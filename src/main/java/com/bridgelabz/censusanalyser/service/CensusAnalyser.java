package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.model.CensusDAO;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusCSV;
import com.bridgelabz.opencsvbuilder.exceptions.CSVBuilderException;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.opencsvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.opencsvbuilder.service.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;

/**
 * @Author : Amrut
 * Purpose : Read the indian state census data from csv file
 */
public class CensusAnalyser {

    Map<String, CensusDAO> csvFileMap;
    List<CensusDAO> censusDAOList = null;
    List<IndiaStateCodeCSV> stateCodeCsvList = null;

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
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
       return this.loadCensusData(csvFilePath, IndiaStateCensusCSV.class);
    }

    /**
     * Function to load US census data
     * @param csvFilePath
     * @return
     */
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, USCensusCSV.class);
    }

    /**
     * Function to load both india and us census data
     * @param csvFilePath
     * @param censusCSVClass
     * @param <E>
     * @return
     * @throws CensusAnalyserException
     */
    private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> censusCSVIterable = () -> csvIterator;
            if (censusCSVClass.getSimpleName().equals("IndiaStateCensusCSV")) {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(IndiaStateCensusCSV.class::cast)
                        .forEach(censusCSV -> this.csvFileMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCSVClass.getSimpleName().equals("USCensusCSV")){
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> this.csvFileMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return csvFileMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, "There is some issue related to the file");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be some error related to delimiter");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    /**
     * Function to load india state code data from csv file
     * @param stateCodeCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaStateCode(String stateCodeCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(stateCodeCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> csvFileMap.get(csvState.stateName) != null)
                    .forEach(censusCSV -> csvFileMap.get(censusCSV.stateName).state = censusCSV.stateCode);
            return csvFileMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, "There is some issue related to the file");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be some error related to delimiter or header");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    /**
     * Function to sort indie census state wise
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(((Comparator <CensusDAO>)
                               (census1, census2) -> census2.state.compareTo(census1.state)).reversed());
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
        loadIndiaStateCode(csvFilePath);
        if (censusDAOList == null || censusDAOList.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        censusDAOList.sort(((Comparator<CensusDAO>)
                                (census1, census2) -> census2.stateCode.compareTo(census1.stateCode)).reversed());
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

