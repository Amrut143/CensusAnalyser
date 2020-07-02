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
        public Integer population;

        @CsvBindByName(column = "AreaInSqKm", required = true)
        public Integer areaInSqKm;

        @CsvBindByName(column = "DensityPerSqKm", required = true)
        public Integer densityPerSqKm;

        @Override
        public String toString() {
            return "IndiaCensusCSV{" +
                    "State='" + state + '\'' +
                    ", Population='" + population + '\'' +
                    ", AreaInSqKm='" + areaInSqKm + '\'' +
                    ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                    '}';
        }
    }
