package com.bridgelabz.censusanalyser.utility;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder<T> implements ICSVBuilder {

    /**
     * Function to iterate data from csv file
     * @param reader
     * @param csvClass
     * @param <T>
     * @return
     * @throws CensusAnalyserException
     */
        public Iterator<T> getCSVFileIterator(Reader reader, Class csvClass) throws CensusAnalyserException {
            try {
                CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(csvClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<T> csvToBean = csvToBeanBuilder.build();
                return csvToBean.iterator();
            } catch (IllegalStateException e) {
                throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.getMessage());
            }
        }
    }
