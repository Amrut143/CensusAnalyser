package com.bridgelabz.censusanalyser.exception;

/**
 * @Author : Amrut
 * Purpose : Defining custom exceptions
 */
public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        DELIMITER_ISSUE,
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

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
