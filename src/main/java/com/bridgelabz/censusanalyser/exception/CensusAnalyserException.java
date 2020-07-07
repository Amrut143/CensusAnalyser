package com.bridgelabz.censusanalyser.exception;

/**
 * @Author : Amrut
 * Purpose : Defining custom exceptions
 */
public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        CSV_FILE_INTERNAL_ISSUE,
        NO_CENSUS_DATA,
        INVALID_COUNTRY;
    }
    public ExceptionType type;

    /**
     *
     * @param message
     * @param type
     */
    public CensusAnalyserException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
