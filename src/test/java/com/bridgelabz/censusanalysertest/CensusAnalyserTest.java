package com.bridgelabz.censusanalysertest;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusCSV;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_EXTENTION = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/IndiaStateCensusWrongDelimiter.csv";
    private static final String INDIA_STATE_CODE_CSV_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_STATE_CODE_WRONG_DELIMITER = "./src/test/resources/IndiaStateCodeWrongDelimiter.csv";
    private static final String WRONG_STATE_CODE_CSV_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_FILE_EXTENTION = "./src/main/resources/IndiaStateCode.txt";
    private static final String US_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String WRONG_US_CSV_FILE_PATH = "./src/main/resources/USCensusData.csv";
    private static final String US_CSV_WRONG_DELIMITER = "./src/test/resources/USCensusDataWrongDelimiter.csv";

    CensusAnalyser censusAnalyser;

    @Before
    public void create_ObjectOf_CensusAnalyser() {

        censusAnalyser = new CensusAnalyser();
    }

    @Test
    public void givenIndianStateCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH, WRONG_STATE_CODE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileExtention_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, WRONG_FILE_EXTENTION, WRONG_STATE_CODE_FILE_EXTENTION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenCorrect_ButDelimiterIncorrect_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_WRONG_DELIMITER, INDIA_STATE_CODE_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenCorrect_ButCsvHeaderIncorrect_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

       @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnLastStateFromSortedList() {
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("West Bengal", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        String sortedStateCodeData;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedStateCodeData = censusAnalyser.getStateCodeWiseSortedData(CensusAnalyser.Country.INDIA, INDIA_STATE_CODE_CSV_PATH);
            IndiaStateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedStateCodeData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AD", stateCodeCSV[stateCodeCSV.length - 1].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnFirstPopulatedState_First() {
        String sortedCensusData;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals(199812341, censusCSV[censusCSV.length - 1].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnLastPopulatedStateFromList() {
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals(607688, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getPopulationDensityWiseSortedCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals(1102, censusCSV[censusCSV.length - 1].populationDensity);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByArea_ShouldReturnSortedResult() {
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getAreaWiseSortedCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals(342239, censusCSV[censusCSV.length - 1].totalArea);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            Assert.assertEquals(51, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.US, WRONG_US_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnFirstPopulatedState_First() {
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusDataForUS(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals(37253956, censusCSV[censusCSV.length - 1].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedCensusDataForUS(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnDensity_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getDensityWiseSortedCensusDataForUS(CensusAnalyser.Country.INDIA, US_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals((Double) 3805.61, censusCSV[censusCSV.length - 1].populationDensity);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getAreaWiseSortedCensusDataForUS(CensusAnalyser.Country.INDIA, US_CSV_FILE_PATH);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals((Double) 1723338.01, censusCSV[censusCSV.length - 1].totalArea);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenCorrect_ButDelimiterIncorrect_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(CensusAnalyser.Country.US, US_CSV_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusData_ShouldReturnMostPopulationDensityState() {
        String mostPopulationDensityIndia;
        String mostPopulationDensityUS;
        try {
            mostPopulationDensityIndia = censusAnalyser.getHighestPopulationDensityStateFromIndiaAndUS(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            mostPopulationDensityUS = censusAnalyser.getHighestPopulationDensityStateFromIndiaAndUS(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            Assert.assertEquals("District of Columbia", mostPopulationDensityUS);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
