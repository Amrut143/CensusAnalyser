package com.bridgelabz.censusanalysertest;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.CensusDAO;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.bridgelabz.censusanalyser.utility.SortByField;
import com.google.gson.Gson;
import org.junit.Assert;
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


    @Test
    public void givenIndianStateCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(WRONG_CSV_FILE_PATH, WRONG_STATE_CODE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileExtention_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(WRONG_FILE_EXTENTION, WRONG_STATE_CODE_FILE_EXTENTION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenCorrect_ButDelimiterIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_WRONG_DELIMITER, INDIA_STATE_CODE_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenCorrect_ButCsvHeaderIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

       @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.STATE);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnLastStateFromSortedList() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.STATE);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("West Bengal", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        String sortedStateCodeData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedStateCodeData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.STATE);
            CensusDAO[] stateCodeCSV = new Gson().fromJson(sortedStateCodeData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", stateCodeCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnFirstPopulatedState_First() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.POPULATION);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnLastPopulatedStateFromList() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.POPULATION);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals(607688, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.DENSITY);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Bihar", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByArea_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCountryCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.AREA);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Rajasthan", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            int numOfRecords = censusAnalyser.loadCountryCensusData(US_CSV_FILE_PATH);
            Assert.assertEquals(51, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(WRONG_US_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnFirstPopulatedState_First() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCountryCensusData(US_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.POPULATION);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("California", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCountryCensusData(US_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.STATE);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Wyoming", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnDensity_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCountryCensusData(US_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.DENSITY);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("District of Columbia", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCountryCensusData(US_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.AREA);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Alaska", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenCorrect_ButDelimiterIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCountryCensusData(US_CSV_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusData_ShouldReturnMostPopulationDensityState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            String mostPopulationDensity = censusAnalyser.getHighestPopulationDensityStateFromIndiaAndUS();
            Assert.assertEquals("District of Columbia", mostPopulationDensity);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
