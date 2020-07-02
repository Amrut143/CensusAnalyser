package com.bridgelabz.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "State", required = true)
    public String State;

    @CsvBindByName(column = "State Id", required = true)
    public String StateId;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Total area", required = true)
    public Double totalArea;

    @CsvBindByName(column = "Population Density", required = true)
    public Double populationDensity;

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "State='" + State + '\'' +
                ", StateId='" + StateId + '\'' +
                ", Population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }
}
