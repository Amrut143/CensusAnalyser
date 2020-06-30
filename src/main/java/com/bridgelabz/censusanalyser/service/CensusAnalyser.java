package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CSVBuilderException;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.utility.CSVBuilderFactory;
import com.bridgelabz.censusanalyser.utility.ICSVBuilder;
import com.bridgelabz.censusanalyser.utility.OpenCSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

/**
 * @Author : Amrut
 * Purpose : Read the indian state census data from csv file
 */
public class CensusAnalyser {

    /**
     * Function to load the india census data from csv file
     * @param censusCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaCensusData(String censusCsvFilePath) throws CensusAnalyserException {
        try(Reader reader = Files.newBufferedReader(Paths.get(censusCsvFilePath))) {

            /*Using stream and lambda expresions to iterate the csv data*/
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCensusCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, "There is some issue related to the file");
        } catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be some error related to delimiter");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    /**
     * Function to load india state code data from csv file
     * @param stateCodeCsvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaStateCode(String stateCodeCsvFilePath) throws CensusAnalyserException {
        try(Reader reader = Files.newBufferedReader(Paths.get(stateCodeCsvFilePath))) {

            /*Using stream and lambda expresions to iterate the csv data*/
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            return this.getCount(stateCodeCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, "There is some issue related to the file");
        } catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be some error related to delimiter");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    /**
     * Function to count the number of entries
     * @param iterator
     * @param <T>
     * @return
     */
    private <T> int getCount(Iterator<T> iterator) {
        Iterable<T> csvIterable = () -> iterator;
        int numberOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numberOfEntries;
    }
}

