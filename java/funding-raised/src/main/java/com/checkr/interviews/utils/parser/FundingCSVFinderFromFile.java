package com.checkr.interviews.utils.parser;

import com.checkr.interviews.FundingCSVBean;
import com.checkr.interviews.utils.filter.CSVBeanFilterer;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class FundingCSVFinderFromFile extends FundingCSVFinder<FundingCSVBean> {
    private final Path sourcePath;

    private final CSVBeanFilterer<FundingCSVBean> filterer;

    private CSVFileReadingException prepareException(IOException exception) {
        return new CSVFileReadingException(MessageFormat.format(
                CSVFileReadingException.FILE_READING_ERROR_TEMPLATE,
                sourcePath.toAbsolutePath()
        ), exception);
    }

    @Override
    protected CsvToBean<FundingCSVBean> prepareConverter(CSVReader reader, FundingCSVBean filteringBean) {
        return new CsvToBeanBuilder<FundingCSVBean>(reader)
                //.withSkipLines(1)
                .withType(FundingCSVBean.class)
                .withVerifier(incomingBean -> filterer.testFilter(filteringBean, incomingBean))
                .build();
    }

    private CSVReader prepareReader() throws IOException {
        return new CSVReader(Files.newBufferedReader(sourcePath, StandardCharsets.UTF_8));
    }

    @Override
    public List<FundingCSVBean> where(FundingCSVBean filteringBean) {
        try (var reader = prepareReader()) {
            return prepareConverter(reader, filteringBean).parse();
        } catch (IOException exception) {
            throw prepareException(exception);
        }
    }

    @Override
    public Optional<FundingCSVBean> findBy(FundingCSVBean filteringBean) {
        try (var reader = prepareReader()) {
            return prepareConverter(reader, filteringBean).stream().findAny();
        } catch (IOException exception) {
            throw prepareException(exception);
        }
    }

    @Override
    public Optional<FundingCSVBean> findFirst(FundingCSVBean filteringBean) {
        try (var reader = prepareReader()) {
            return prepareConverter(reader, filteringBean).stream().findFirst();
        } catch (IOException exception) {
            throw prepareException(exception);
        }
    }
}