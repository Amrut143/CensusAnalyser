package com.bridgelabz.censusanalyser.exception;

/**
 * @Author : Amrut
 * Purpose : Defining custom exceptions
 */
public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM
    }
    ExceptionType type;

    /**
     *
     * @param message
     * @param type
     */
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    /**
     *
     * @param message
     * @param type
     * @param cause
     */
    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
