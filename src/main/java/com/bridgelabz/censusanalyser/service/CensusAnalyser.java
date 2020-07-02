package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.model.IndiaCensusDAO;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
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
public class CensusAnalyser<T> {

    Map<String, IndiaCensusDAO> csvFileMap = null;
    List<IndiaCensusDAO> censusDAOList = null;
    List<IndiaStateCodeCSV> stateCodeCsvList = null;

    public CensusAnalyser() {
        this.csvFileMap = new HashMap<>();
    }
    /**
     * Function to load the india census data from csv file
     *
     * @param censusCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaCensusData(String censusCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(censusCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCensusCSV.class);
            Iterable<IndiaStateCensusCSV> censusCSVIterable = () -> csvIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(),false)
                         .forEach(censusCSV -> csvFileMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
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
     *
     * @param stateCodeCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaStateCode(String stateCodeCsvFilePath) throws CensusAnalyserException {
        int count = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(stateCodeCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (stateCodeCSVIterator.hasNext()){
                count++;
                IndiaStateCodeCSV indiaStateCodeCSV = stateCodeCSVIterator.next();
                IndiaCensusDAO indiaCensusDAO = csvFileMap.get(indiaStateCodeCSV.stateName);
                if(indiaCensusDAO == null) continue;
                indiaCensusDAO.stateCode = indiaStateCodeCSV.stateCode;
            }
            return count;
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, "There is some issue related to the file");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be some error related to delimiter or header");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    /**
     *
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(((Comparator <IndiaCensusDAO>)
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
        if (stateCodeCsvList == null || stateCodeCsvList.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        loadIndiaStateCode(csvFilePath);
        stateCodeCsvList.sort(((Comparator<IndiaStateCodeCSV>)
                                (census1, census2) -> census2.stateCode.compareTo(census1.stateCode)).reversed());
        String sortedStateCodeData = new Gson().toJson(stateCodeCsvList);
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
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
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
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
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
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        censusDAOList = csvFileMap.values().stream().collect(Collectors.toList());
        censusDAOList.sort(censusComparator);
        String sortedCensusData = new Gson().toJson(censusDAOList);
        return sortedCensusData;
    }
}

