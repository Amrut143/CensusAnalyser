package com.bridgelabz.censusanalyser.exception;

/**
 * @Author : Amrut
 * Purpose : Defining custom exceptions
 */
public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        CSV_FILE_INTERNAL_ISSUE
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
