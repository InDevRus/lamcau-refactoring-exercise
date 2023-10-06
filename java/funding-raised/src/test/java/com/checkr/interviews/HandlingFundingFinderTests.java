package com.checkr.interviews;

import com.checkr.interviews.beans.utils.filter.HandlingCSVBeanFilterer;

import java.nio.file.Path;

public class HandlingFundingFinderTests implements FundingCSVFinderTests {
    private final Path sourcePath = Path.of("startup_funding.csv");
    private final FundingCSVFinder finder = new FundingCSVFinder(sourcePath, HandlingCSVBeanFilterer.ofClass(FundingCSVBean.class));

    @Override
    public FundingCSVFinder getFinder() {
        return finder;
    }
}
