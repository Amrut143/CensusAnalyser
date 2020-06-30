package com.bridgelabz.censusanalyser.exception;

/**
 * @Author : Amrut
 * Purpose : Defining custom exceptions
 */
public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        CSV_FILE_INTERNAL_ISSUE,
        UNABLE_TO_PARSE
    }
    public ExceptionType type;

    /**
     *
     * @param message
     * @param name
     */
    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
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
