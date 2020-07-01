package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.opencsvbuilder.exceptions.CSVBuilderException;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.opencsvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.opencsvbuilder.service.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

/**
 * @Author : Amrut
 * Purpose : Read the indian state census data from csv file
 */
public class CensusAnalyser<T> {

    List<IndiaStateCensusCSV> censusCsvFileList = null;
    List<IndiaStateCodeCSV> stateCodeCsvList = null;
    /**
     * Function to load the india census data from csv file
     *
     * @param censusCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaCensusData(String censusCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(censusCsvFilePath))) {

            /*Using stream and lambda expresions to iterate the csv data*/
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCsvFileList = csvBuilder.getCSVFileList(reader, IndiaStateCensusCSV.class);
            return censusCsvFileList.size();
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
        try (Reader reader = Files.newBufferedReader(Paths.get(stateCodeCsvFilePath))) {

            /*Using stream and lambda expresions to iterate the csv data*/
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCodeCsvList = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return stateCodeCsvList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, "There is some issue related to the file");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be some error related to delimiter");
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
        if (censusCsvFileList == null || censusCsvFileList.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        censusCsvFileList.sort(((Comparator <IndiaStateCensusCSV>)
                               (census1, census2) -> census2.state.compareTo(census1.state)).reversed());
        String sortedCensusData = new Gson().toJson(censusCsvFileList);
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
        stateCodeCsvList.sort(((Comparator<IndiaStateCodeCSV>)
                                (census1, census2) -> census2.stateCode.compareTo(census1.stateCode)).reversed());
        String sortedStateCodeData = new Gson().toJson(stateCodeCsvList);
        return sortedStateCodeData;
    }

    public String getPopulationWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (censusCsvFileList == null || censusCsvFileList.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        censusCsvFileList.sort((census1, census2) -> census2.population.compareTo(census1.population));
        String sortedCensusData = new Gson().toJson(censusCsvFileList);
        return sortedCensusData;
    }
}

