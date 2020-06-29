package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
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
     *
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvToBeanBuilder<IndiaStateCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCensusCSV> csvToBean = csvToBeanBuilder.build();

            /*Using stream and lambda expresions to iterate the csv data*/
            Iterator<IndiaStateCensusCSV> censusCSVIterator = csvToBean.iterator();;
            Iterable<IndiaStateCensusCSV> csvIterable = () -> censusCSVIterator;
            int numberOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numberOfEateries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}

