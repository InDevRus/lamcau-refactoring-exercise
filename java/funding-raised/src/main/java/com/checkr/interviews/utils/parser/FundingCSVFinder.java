package com.checkr.interviews.utils.parser;

import com.checkr.interviews.FundingCSVBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;

import java.util.List;
import java.util.Optional;

public abstract class FundingCSVFinder<T extends FundingCSVBean> {
    public abstract List<T> where(T filteringBean);

    public abstract Optional<T> findBy(T filteringBean);

    public abstract Optional<T> findFirst(T filteringBean);

    protected abstract CsvToBean<T> prepareConverter(CSVReader reader, FundingCSVBean filteringBean);
}
