package com.checkr.interviews;

import com.checkr.interviews.beans.FilteringComponent;
import com.checkr.interviews.beans.ParsedCSVBean;
import com.opencsv.bean.CsvBindByName;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FundingCSVBean extends ParsedCSVBean {
    @CsvBindByName(column = "permalink")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private String permalink;

    @CsvBindByName(column = "company_name")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private String companyName;

    @CsvBindByName(column = "number_employees")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private Integer numberEmployees;

    @CsvBindByName(column = "category")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private String category;

    @CsvBindByName(column = "city")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private String city;

    @CsvBindByName(column = "state")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private String state;

    @CsvBindByName(column = "funded_date")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private String fundedDate;

    @CsvBindByName(column = "raised_amount")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private Integer raisedAmount;

    @CsvBindByName(column = "raised_currency")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private String raisedCurrency;

    @CsvBindByName(column = "round")
    @Getter(AccessLevel.PUBLIC)
    @FilteringComponent
    private String round;
}
