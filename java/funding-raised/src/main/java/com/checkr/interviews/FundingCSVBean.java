package com.checkr.interviews;

import com.checkr.interviews.beans.FilteringElement;
import com.checkr.interviews.beans.ParsedCSVBean;
import com.opencsv.bean.CsvBindByName;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FundingCSVBean extends ParsedCSVBean {
    @CsvBindByName(column = "permalink")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private String permalink;

    @CsvBindByName(column = "company_name")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private String companyName;

    @CsvBindByName(column = "number_employees")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private Integer numberEmployees;

    @CsvBindByName(column = "category")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private String category;

    @CsvBindByName(column = "city")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private String city;

    @CsvBindByName(column = "state")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private String state;

    @CsvBindByName(column = "funded_date")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private String fundedDate;

    @CsvBindByName(column = "raised_amount")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private Integer raisedAmount;

    @CsvBindByName(column = "raised_currency")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private String raisedCurrency;

    @CsvBindByName(column = "round")
    @Getter(AccessLevel.PUBLIC)
    @FilteringElement
    private String round;
}
