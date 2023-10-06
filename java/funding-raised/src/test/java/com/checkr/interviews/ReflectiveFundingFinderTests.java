package com.checkr.interviews;

import com.checkr.interviews.beans.utils.filter.ReflectiveCSVBeanFilterer;

import java.nio.file.Path;

public class ReflectiveFundingFinderTests implements FundingCSVFinderTests {
    private final Path sourcePath = Path.of("startup_funding.csv");
    private final FundingCSVFinder finder = new FundingCSVFinder(sourcePath, ReflectiveCSVBeanFilterer.ofClass(FundingCSVBean.class));

    @Override
    public FundingCSVFinder getFinder() {
        return finder;
    }
}
