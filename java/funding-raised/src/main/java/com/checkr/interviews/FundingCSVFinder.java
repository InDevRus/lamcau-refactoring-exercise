package com.checkr.interviews;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import csv.parsing.ParsedCSVBean;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class FundingCSVFinder {
    private final Path sourcePath;

    public List<FundingCSVBean> where(FundingCSVBean filteringBean) throws IOException {
        try (var reader = new CSVReader(Files.newBufferedReader(sourcePath))) {
            return new CsvToBeanBuilder<FundingCSVBean>(reader)
                    .withSkipLines(1)
                    .withType(FundingCSVBean.class)
                    .withVerifier(incomingBean -> ParsedCSVBean.testFilter(filteringBean, incomingBean))
                    .build()
                    .stream()
                    .toList();
        }
    }

    public FundingCSVBean findBy(FundingCSVBean filteringBean) throws IOException, FundingCSVParsingException {
        try (var reader = new CSVReader(Files.newBufferedReader(sourcePath))) {
            return new CsvToBeanBuilder<FundingCSVBean>(reader)
                    .withSkipLines(1)
                    .withType(FundingCSVBean.class)
                    .withVerifier(incomingBean -> ParsedCSVBean.testFilter(filteringBean, incomingBean))
                    .build()
                    .stream()
                    .findFirst()
                    .orElseThrow(FundingCSVNotFoundException::new);
        }
    }
}