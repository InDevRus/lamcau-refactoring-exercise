package com.checkr.interviews;

import com.checkr.interviews.utils.filter.ReflectiveCSVBeanFilterer;
import com.checkr.interviews.utils.parser.FundingCSVFinderFromFile;

import java.nio.file.Path;

public class ReflectiveFundingFinderTests implements FundingCSVFinderFromFileTests {
    private final Path sourcePath = Path.of("startup_funding.csv");
    private final FundingCSVFinderFromFile finder = FundingCSVFinderFromFile.builder()
            .sourcePath(sourcePath)
            .filterer(ReflectiveCSVBeanFilterer.ofClass(FundingCSVBean.class))
            .build();

    @Override
    public FundingCSVFinderFromFile getFinder() {
        return finder;
    }
}
