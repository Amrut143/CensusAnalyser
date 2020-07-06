package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.USCensusCSV;
import com.bridgelabz.censusanalyser.utility.Country;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    /**
     *
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    @Override
    public Map<String, CensusDAO> loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        return super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
    }
}
