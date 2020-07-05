package com.bridgelabz.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;
/**
 * @Author : Amrut
 * Purpose : POJO class for india state census
 */
public class IndiaStateCensusCSV {

    public IndiaStateCensusCSV() {

    }

        @CsvBindByName(column = "State", required = true)
        public String state;

        @CsvBindByName(column = "Population", required = true)
        public int population;

        @CsvBindByName(column = "AreaInSqKm", required = true)
        public int areaInSqKm;

        @CsvBindByName(column = "DensityPerSqKm", required = true)
        public int densityPerSqKm;

    public IndiaStateCensusCSV(String state, int population, int areaInSqKm, int densityPerSqKm) {
        this.state = state;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqKm = densityPerSqKm;
    }
}