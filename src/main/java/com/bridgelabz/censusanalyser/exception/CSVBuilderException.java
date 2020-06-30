package com.bridgelabz.censusanalyser.exception;

public class CSVBuilderException extends Throwable {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        CSV_FILE_INTERNAL_ISSUE,
        UNABLE_TO_PARSE
    }

    public ExceptionType type;

    /**
     * @param message
     * @param type
     */
    public CSVBuilderException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
