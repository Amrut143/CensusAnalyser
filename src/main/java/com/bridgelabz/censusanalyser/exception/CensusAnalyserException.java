package com.bridgelabz.censusanalyser.exception;

/**
 * @Author : Amrut
 * Purpose : Defining custom exceptions
 */
public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        DELIMITER_ISSUE,
        INCORRECT_DATA_ISSUE
    }
    public ExceptionType type;

    /**
     *
     * @param message
     * @param type
     */
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
