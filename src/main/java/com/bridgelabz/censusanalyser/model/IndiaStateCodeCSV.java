package com.bridgelabz.censusanalyser.model;

/**
 * @Author : Amrut
 */

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {

    @CsvBindByName(column = "SrNo", required = true)
    public String srNo;

    @CsvBindByName(column = "stateName")
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode")
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "srNo='" + srNo + '\'' +
                ", stateName=" + stateName +
                ", tin=" + tin +
                ", stateCode=" + stateCode +
                '}';
    }
}
