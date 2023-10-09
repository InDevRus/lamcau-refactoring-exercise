package com.checkr.interviews;

import com.checkr.interviews.utils.filter.HandlingCSVBeanFilterer;
import com.checkr.interviews.utils.parser.FundingCSVFinderFromFile;

import java.nio.file.Path;

public class HandlingFundingFinderTests implements FundingCSVFinderFromFileTests {
    private final Path sourcePath = Path.of("startup_funding.csv");
    private final FundingCSVFinderFromFile finder = FundingCSVFinderFromFile.builder()
            .sourcePath(sourcePath)
            .filterer(HandlingCSVBeanFilterer.ofClass(FundingCSVBean.class))
            .build();

    @Override
    public FundingCSVFinderFromFile getFinder() {
        return finder;
    }
}
