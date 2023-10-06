package com.checkr.interviews;

import com.checkr.interviews.beans.utils.filter.CSVBeanFilterer;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class FundingCSVFinder {
    private final Path sourcePath;

    private final CSVBeanFilterer<FundingCSVBean> filterer;

    private Stream<FundingCSVBean> prepareFundingStream(CSVReader reader, FundingCSVBean filteringBean) {
        return new CsvToBeanBuilder<FundingCSVBean>(reader)
                .withSkipLines(1)
                .withType(FundingCSVBean.class)
                .withVerifier(incomingBean -> filterer.testFilter(filteringBean, incomingBean))
                .build()
                .stream();
    }

    public List<FundingCSVBean> where(FundingCSVBean filteringBean) throws IOException {
        try (var reader = new CSVReader(Files.newBufferedReader(sourcePath))) {
            return prepareFundingStream(reader, filteringBean).toList();
        }
    }

    public Optional<FundingCSVBean> findBy(FundingCSVBean filteringBean) throws IOException {
        try (var reader = new CSVReader(Files.newBufferedReader(sourcePath))) {
            return prepareFundingStream(reader, filteringBean).findFirst();
        }
    }
}