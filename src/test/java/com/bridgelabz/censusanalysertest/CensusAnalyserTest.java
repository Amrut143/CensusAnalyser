package com.bridgelabz.censusanalysertest;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";

    CensusAnalyser censusAnalyser;

    @Before
    public void create_ObjectOf_CensusAnalyser() {
        censusAnalyser = new CensusAnalyser();
    }

    @Test
    public void givenIndianStateCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }
}