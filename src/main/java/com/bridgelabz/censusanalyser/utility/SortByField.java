package com.bridgelabz.censusanalyser.utility;

import com.bridgelabz.censusanalyser.model.CensusDAO;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortByField {

    static Map<SortByField.Parameter, Comparator<CensusDAO>> censusSortMap = new HashMap<>();

    public enum Parameter {
        STATE, POPULATION, AREA, DENSITY;
    }

    /**
     *
     * @param parameter
     * @return
     */
    public static Comparator getParameter(Parameter parameter) {

        censusSortMap.put(Parameter.STATE, Comparator.comparing(census -> census.state));
        censusSortMap.put(Parameter.POPULATION, Comparator.comparing(census -> census.totalArea));
        censusSortMap.put(Parameter.AREA, Comparator.comparing(census -> census.population));
        censusSortMap.put(Parameter.DENSITY, Comparator.comparing(census -> census.populationDensity));

        Comparator<CensusDAO> comparator = censusSortMap.get(parameter);

        return comparator;
    }
}
