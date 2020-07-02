package com.bridgelabz.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;
/**
 * @Author : Amrut
 * Purpose : POJO class for india state census
 */
public class IndiaStateCensusCSV {

        @CsvBindByName(column = "State", required = true)
        public String state;

        @CsvBindByName(column = "Population", required = true)
        public int population;

        @CsvBindByName(column = "AreaInSqKm", required = true)
        public int totalArea;

        @CsvBindByName(column = "DensityPerSqKm", required = true)
        public int populationDensity;

        @Override
        public String toString() {
                return "IndiaStateCensusCSV{" +
                        "state='" + state + '\'' +
                        ", population=" + population +
                        ", areaInSqKm=" + totalArea +
                        ", densityPerSqKm=" + populationDensity +
                        '}';
        }
}