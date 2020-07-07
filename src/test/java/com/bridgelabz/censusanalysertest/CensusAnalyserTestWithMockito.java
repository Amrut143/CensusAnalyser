package com.bridgelabz.censusanalysertest;

import com.bridgelabz.censusanalyser.adapter.CensusAdapter;
import com.bridgelabz.censusanalyser.adapter.CensusAdapterFactory;
import com.bridgelabz.censusanalyser.adapter.IndiaCensusAdapter;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.bridgelabz.censusanalyser.utility.Country;
import com.bridgelabz.censusanalyser.utility.SortByField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CensusAnalyserTestWithMockito {


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

    @Mock
    private CensusAdapterFactory censusAdapterFactory;

    @InjectMocks
    private CensusAnalyser censusAnalyser;

    Map<String, CensusDAO> censusDAOMap;
    Map<SortByField.Parameter, Comparator> sortParameterComparator;

    @Before
    public void setUp() throws Exception {
        this.censusDAOMap = new HashMap<>();
        sortParameterComparator = new HashMap<>();
        this.censusDAOMap.put("Odisha", new CensusDAO("Odisha", 298757, 2987.56, 5678.98, "OD"));
        this.censusDAOMap.put("Maharastra", new CensusDAO("Maharastra", 5489364, 89564.9, 768.44, "MH"));
        this.censusDAOMap.put("Karnataka", new CensusDAO("Karnataka", 7689699, 7686.8, 7568.8, "KA"));
        Comparator<CensusDAO> populationComparator = Comparator.comparing(censusCSV -> censusCSV.population);
        sortParameterComparator.put(SortByField.Parameter.POPULATION, populationComparator);

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void givenIndiaCensusCSVFile_ShouldReturnCorrectRecords() {
        try {
            CensusAdapter censusAdapter = mock(IndiaCensusAdapter.class);
            when(censusAdapter.loadCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH)).thenReturn(this.censusDAOMap);
            CensusAnalyser censusAnalyser = new CensusAnalyser(Country.INDIA);
            censusAnalyser.setCensusAdapter(censusAdapter);
            int censusRecords = censusAnalyser.loadCountryCensusData(Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(3, censusRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
