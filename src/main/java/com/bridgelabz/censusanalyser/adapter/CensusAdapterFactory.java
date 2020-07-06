package com.bridgelabz.censusanalyser.adapter;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.utility.Country;

import java.util.Map;

public class CensusAdapterFactory {

    /**
     * Function to load country census data
     *
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public Map<String, CensusDAO> getCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        if (country.equals(Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(country, csvFilePath);
        else if (country.equals(Country.US))
            return new USCensusAdapter().loadCensusData(country, csvFilePath);
        else
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INVALID_COUNTRY, "Incorrect Country");
    }
}
