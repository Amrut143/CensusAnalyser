package com.bridgelabz.censusanalysertest;

import com.bridgelabz.censusanalyser.adapter.IndiaCensusAdapter;
import com.bridgelabz.censusanalyser.adapter.USCensusAdapter;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusCSV;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.bridgelabz.censusanalyser.utility.Country;
import com.bridgelabz.censusanalyser.utility.SortByField;
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


    CensusAnalyser indiaCensusAnalyser;
    CensusAnalyser usCensusAnalyser;

    @Before
    public void createObject() {

        indiaCensusAnalyser = new CensusAnalyser(Country.INDIA);
        usCensusAnalyser = new CensusAnalyser(Country.US);
    }
    @Test
    public void givenIndianStateCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            int numOfRecords = indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, WRONG_CSV_FILE_PATH, WRONG_STATE_CODE_CSV_PATH);
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
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, WRONG_FILE_EXTENTION, WRONG_STATE_CODE_FILE_EXTENTION);
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
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_WRONG_DELIMITER, INDIA_STATE_CODE_WRONG_DELIMITER);
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
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

       @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = indiaCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.STATE);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnLastStateFromSortedList() {
        String sortedCensusData;
        try {
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = indiaCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.STATE);
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
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedStateCodeData = indiaCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.STATE_CODE);
            IndiaStateCensusCSV[] stateCodeCSV = new Gson().fromJson(sortedStateCodeData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", stateCodeCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnFirstPopulatedState_First() {
        String sortedCensusData;
        try {
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = indiaCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.POPULATION);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnLastPopulatedStateFromList() {
        String sortedCensusData;
        try {
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = indiaCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.POPULATION);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals(607688, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = indiaCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.DENSITY);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByArea_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            indiaCensusAnalyser.setCensusAdapter(new IndiaCensusAdapter());
            indiaCensusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            sortedCensusData = indiaCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.AREA);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            usCensusAnalyser.setCensusAdapter(new USCensusAdapter());
            int numOfRecords = usCensusAnalyser.loadCountryCensusData(Country.US, US_CSV_FILE_PATH);
            Assert.assertEquals(51, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            usCensusAnalyser.setCensusAdapter(new USCensusAdapter());
            usCensusAnalyser.loadCountryCensusData(Country.US, WRONG_US_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnFirstPopulatedState_First() {
        String sortedCensusData;
        try {
            usCensusAnalyser.setCensusAdapter(new USCensusAdapter());
            usCensusAnalyser.loadCountryCensusData(Country.US, US_CSV_FILE_PATH);
            sortedCensusData = usCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            usCensusAnalyser.setCensusAdapter(new USCensusAdapter());
            usCensusAnalyser.loadCountryCensusData(Country.US, US_CSV_FILE_PATH);
            sortedCensusData = usCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.STATE);
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
            usCensusAnalyser.setCensusAdapter(new USCensusAdapter());
            usCensusAnalyser.loadCountryCensusData(Country.US, US_CSV_FILE_PATH);
            sortedCensusData = usCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        String sortedCensusData;
        try {
            usCensusAnalyser.setCensusAdapter(new USCensusAdapter());
            usCensusAnalyser.loadCountryCensusData(Country.US, US_CSV_FILE_PATH);
            sortedCensusData = usCensusAnalyser.getSortedCensusDataBasedOnField(SortByField.Parameter.AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenCorrect_ButDelimiterIncorrect_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            usCensusAnalyser.setCensusAdapter(new USCensusAdapter());
            usCensusAnalyser.loadCountryCensusData(Country.US, US_CSV_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusData_ShouldReturnMostPopulationDensityState() {
        try {
            usCensusAnalyser.setCensusAdapter(new USCensusAdapter());
            String mostPopulationDensity = usCensusAnalyser.getHighestPopulationDensityStateFromIndiaAndUS(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH, US_CSV_FILE_PATH);
            Assert.assertEquals("District of Columbia", mostPopulationDensity);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
