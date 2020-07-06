package com.bridgelabz.censusanalyser.utility;

import com.bridgelabz.censusanalyser.dao.CensusDAO;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortByField {

    static Map<SortByField.Parameter, Comparator> censusSortMap = new HashMap<>();

    public enum Parameter {
        STATE, POPULATION, AREA, DENSITY, STATE_CODE;
    }

    /**
     *
     * @param parameter
     * @return
     */
    public static Comparator getParameter(Parameter parameter) {

        Comparator<CensusDAO> stateComparator = Comparator.comparing(census -> census.state);
        Comparator<CensusDAO> populationComparator = Comparator.comparing(census -> census.population);
        Comparator<CensusDAO> areaComparator = Comparator.comparing(census -> census.totalArea);
        Comparator<CensusDAO> densityComparator = Comparator.comparing(census -> census.populationDensity);
        Comparator<CensusDAO> stateCodeComparator = Comparator.comparing(census -> census.stateCode);

        censusSortMap.put(Parameter.STATE, stateComparator);
        censusSortMap.put(Parameter.POPULATION, populationComparator);
        censusSortMap.put(Parameter.AREA, areaComparator);
        censusSortMap.put(Parameter.DENSITY, densityComparator);
        censusSortMap.put(Parameter.STATE_CODE, stateCodeComparator);

        Comparator<CensusDAO> comparator = censusSortMap.get(parameter);

        return comparator;
    }
}
