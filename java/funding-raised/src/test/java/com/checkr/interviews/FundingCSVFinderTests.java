package com.checkr.interviews;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Unit test for simple App.
 */
public class FundingCSVFinderTests {
    private FundingCSVFinder finder;

    @BeforeEach
    public void initializeSourcePath() {
        var sourcePath = Path.of("startup_funding.csv");
        finder = new FundingCSVFinder(sourcePath);
    }
    

    /**
     * Rigourous Test :-)
     */
    @Test
    public void testWhereGivenCompany() throws IOException {
        var filter = FundingCSVBean.builder()
                .companyName("Facebook")
                .build();

        Assertions.assertEquals(finder.where(filter).size(), 7);
    }

    @Test
    public void testWhereGivenCity() throws IOException {
        var filter = FundingCSVBean.builder()
                .city("Tempe")
                .build();

        Assertions.assertEquals(finder.where(filter).size(), 3);
    }

    @Test
    public void testWhereGivenState() throws IOException {
        var filter = FundingCSVBean.builder()
                .state("CA")
                .build();

        Assertions.assertEquals(finder.where(filter).size(), 873);
    }

    @Test
    public void testWhereGivenRound() throws IOException {
        var filter = FundingCSVBean.builder()
                .round("a")
                .build();

        Assertions.assertEquals(finder.where(filter).size(), 582);
    }

    @Test
    public void testMultipleOptions() throws IOException {
        var filter = FundingCSVBean.builder()
                .round("a")
                .companyName("Facebook")
                .build();
        
        Assertions.assertEquals(finder.where(filter).size(), 1);
    }

    @Test
    public void testWhereNotExists() throws IOException {
        var filter = FundingCSVBean.builder()
                .companyName("NotFacebook")
                .build();

        Assertions.assertEquals(finder.where(filter).size(), 0);
    }

    @Test
    public void testWhereCorrectKeys() throws IOException {
        var filter = FundingCSVBean.builder()
                .companyName("Facebook")
                .build();

        var row = finder.where(filter).get(0);

        Assertions.assertEquals(row.getPermalink(), "facebook");
        Assertions.assertEquals(row.getCompanyName(), "Facebook");
        Assertions.assertEquals(row.getNumberEmployees(), (Integer) 450);
        Assertions.assertEquals(row.getCategory(), "web");
        Assertions.assertEquals(row.getCity(), "Palo Alto");
        Assertions.assertEquals(row.getState(), "CA");
        Assertions.assertEquals(row.getFundedDate(), "1-Sep-04");
        Assertions.assertEquals(row.getRaisedAmount(), (Integer) 500000);
        Assertions.assertEquals(row.getRound(), "angel");
    }

    @Test
    public void testFindByGivenCompanyName() throws FundingCSVParsingException, IOException {
        var filter = FundingCSVBean.builder()
                .companyName("Facebook")
                .build();

        var row = finder.findBy(filter);

        Assertions.assertEquals(row.getPermalink(), "facebook");
        Assertions.assertEquals(row.getCompanyName(), "Facebook");
        Assertions.assertEquals(row.getNumberEmployees(), (Integer) 450);
        Assertions.assertEquals(row.getCategory(), "web");
        Assertions.assertEquals(row.getCity(), "Palo Alto");
        Assertions.assertEquals(row.getState(), "CA");
        Assertions.assertEquals(row.getFundedDate(), "1-Sep-04");
        Assertions.assertEquals(row.getRaisedAmount(), (Integer) 500000);
        Assertions.assertEquals(row.getRound(), "angel");


    }

    @Test
    public void testFindByGivenState() throws FundingCSVParsingException, IOException {
        var filter = FundingCSVBean.builder()
                .state("CA")
                .build();


        var row = finder.findBy(filter);

        Assertions.assertEquals(row.getPermalink(), "digg");
        Assertions.assertEquals(row.getCompanyName(), "Digg");
        Assertions.assertEquals(row.getNumberEmployees(), (Integer) 60);
        Assertions.assertEquals(row.getCategory(), "web");
        Assertions.assertEquals(row.getCity(), "San Francisco");
        Assertions.assertEquals(row.getState(), "CA");
        Assertions.assertEquals(row.getFundedDate(), "1-Dec-06");
        Assertions.assertEquals(row.getRaisedAmount(), (Integer) 8500000);
        Assertions.assertEquals(row.getRound(), "b");
    }

    @Test
    public void testFindByMultipleOptions() throws FundingCSVParsingException, IOException {
        var filter = FundingCSVBean.builder()
                .companyName("Facebook")
                .round("c")
                .build();

        var row = finder.findBy(filter);

        Assertions.assertEquals(row.getPermalink(), "facebook");
        Assertions.assertEquals(row.getCompanyName(), "Facebook");
        Assertions.assertEquals(row.getNumberEmployees(), (Integer) 450);
        Assertions.assertEquals(row.getCategory(), "web");
        Assertions.assertEquals(row.getCity(), "Palo Alto");
        Assertions.assertEquals(row.getState(), "CA");
        Assertions.assertEquals(row.getFundedDate(), "1-Oct-07");
        Assertions.assertEquals(row.getRaisedAmount(), (Integer) 300000000);
        Assertions.assertEquals(row.getRound(), "c");
    }

    @Test
    public void testFindByNotExists() throws IOException {
        var filter = FundingCSVBean.builder()
                .companyName("NotFacebook")
                .round("c")
                .build();
        try {
            finder.findBy(filter);
            Assertions.fail("FundingRaised.findBy should throw exception");
        } catch (FundingCSVParsingException ignored) {

        }
    }
}
