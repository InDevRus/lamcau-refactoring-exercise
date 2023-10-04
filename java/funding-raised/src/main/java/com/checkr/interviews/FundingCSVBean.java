package com.checkr.interviews;

import com.opencsv.bean.CsvBindByName;
import csv.parsing.ParsedCSVBean;
import lombok.*;


@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FundingCSVBean extends ParsedCSVBean {
    @CsvBindByName(column = "permalink")
    private String permalink;

    @CsvBindByName(column = "company_name")
    private String companyName;

    @CsvBindByName(column = "number_employees")
    private Integer numberEmployees;

    @CsvBindByName(column = "category")
    private String category;

    @CsvBindByName(column = "city")
    private String city;

    @CsvBindByName(column = "state")
    private String state;

    @CsvBindByName(column = "funded_date")
    private String fundedDate;

    @CsvBindByName(column = "raised_amount")
    private Integer raisedAmount;

    @CsvBindByName(column = "raised_currency")
    private String raisedCurrency;

    @CsvBindByName(column = "round")
    private String round;
}
