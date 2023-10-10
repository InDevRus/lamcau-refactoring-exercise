package com.checkr.interviews;

import com.checkr.interviews.utils.parser.FundingCSVFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.Month;

/**
 * Unit test for simple App.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface FundingCSVFinderFromFileTests {
    FundingCSVFinder<FundingCSVBean> getFinder();

    @Test
    default void testFindVeryFirstRow() {
        var filter = FundingCSVBean.builder()
                .permalink("lifelock")
                .raisedAmount(6850000)
                .build();

        var possibleRow = getFinder().findBy(filter);
        possibleRow.ifPresentOrElse(found -> {
            Assertions.assertEquals(found.getPermalink(), "lifelock");
            Assertions.assertEquals(found.getCompanyName(), "LifeLock");
            Assertions.assertNull(found.getNumberEmployees());
            Assertions.assertEquals(found.getCategory(), "web");
            Assertions.assertEquals(found.getCity(), "Tempe");
            Assertions.assertEquals(found.getState(), "AZ");
            Assertions.assertEquals(found.getFundedDate(), LocalDate.of(2007, Month.MAY, 1));
            Assertions.assertEquals(found.getRaisedAmount(), 6850000);
            Assertions.assertEquals(found.getRaisedCurrency(), "USD");
            Assertions.assertEquals(found.getRound(), "b");
        }, Assertions::fail);
    }


    /**
     * Rigourous Test :-)
     */
    @Test
    default void testWhereGivenCompany() {
        var filter = FundingCSVBean.builder()
                .companyName("Facebook")
                .build();

        Assertions.assertEquals(getFinder().where(filter).size(), 7);
    }

    @Test
    default void testWhereGivenCity() {
        var filter = FundingCSVBean.builder()
                .city("Tempe")
                .build();

        Assertions.assertEquals(getFinder().where(filter).size(), 3);
    }

    @Test
    default void testWhereGivenState() {
        var filter = FundingCSVBean.builder()
                .state("CA")
                .build();

        Assertions.assertEquals(getFinder().where(filter).size(), 873);
    }

    @Test
    default void testWhereGivenRound() {
        var filter = FundingCSVBean.builder()
                .round("a")
                .build();

        Assertions.assertEquals(getFinder().where(filter).size(), 582);
    }

    @Test
    default void testMultipleOptions() {
        var filter = FundingCSVBean.builder()
                .round("a")
                .companyName("Facebook")
                .build();

        Assertions.assertEquals(getFinder().where(filter).size(), 1);
    }

    @Test
    default void testWhereNotExists() {
        var filter = FundingCSVBean.builder()
                .companyName("NotFacebook")
                .build();

        Assertions.assertEquals(getFinder().where(filter).size(), 0);
    }

    @Test
    default void testWhereCorrectKeys() {
        var filter = FundingCSVBean.builder()
                .companyName("Facebook")
                .build();

        var row = getFinder().where(filter).get(0);

        Assertions.assertEquals(row.getPermalink(), "facebook");
        Assertions.assertEquals(row.getCompanyName(), "Facebook");
        Assertions.assertEquals(row.getNumberEmployees(), 450);
        Assertions.assertEquals(row.getCategory(), "web");
        Assertions.assertEquals(row.getCity(), "Palo Alto");
        Assertions.assertEquals(row.getState(), "CA");
        Assertions.assertEquals(row.getFundedDate(), LocalDate.of(2004, Month.SEPTEMBER, 1));
        Assertions.assertEquals(row.getRaisedAmount(), 500000);
        Assertions.assertEquals(row.getRound(), "angel");
    }

    @Test
    default void testFindByGivenCompanyName() {
        var filter = FundingCSVBean.builder()
                .companyName("Facebook")
                .build();

        var possibleRow = getFinder().findBy(filter);

        possibleRow.ifPresentOrElse(row -> {
            Assertions.assertEquals(row.getPermalink(), "facebook");
            Assertions.assertEquals(row.getCompanyName(), "Facebook");
            Assertions.assertEquals(row.getNumberEmployees(), 450);
            Assertions.assertEquals(row.getCategory(), "web");
            Assertions.assertEquals(row.getCity(), "Palo Alto");
            Assertions.assertEquals(row.getState(), "CA");
            Assertions.assertEquals(row.getFundedDate(), LocalDate.of(2004, Month.SEPTEMBER, 1));
            Assertions.assertEquals(row.getRaisedAmount(), 500000);
            Assertions.assertEquals(row.getRound(), "angel");
        }, Assertions::fail);
    }

    @Test
    default void testFindByGivenState() {
        var filter = FundingCSVBean.builder()
                .state("CA")
                .build();


        var possibleRow = getFinder().findBy(filter);

        possibleRow.ifPresentOrElse(row -> {
            Assertions.assertEquals(row.getPermalink(), "digg");
            Assertions.assertEquals(row.getCompanyName(), "Digg");
            Assertions.assertEquals(row.getNumberEmployees(), 60);
            Assertions.assertEquals(row.getCategory(), "web");
            Assertions.assertEquals(row.getCity(), "San Francisco");
            Assertions.assertEquals(row.getState(), "CA");
            Assertions.assertEquals(row.getFundedDate(), LocalDate.of(2006, Month.DECEMBER, 1));
            Assertions.assertEquals(row.getRaisedAmount(), 8500000);
            Assertions.assertEquals(row.getRound(), "b");
        }, Assertions::fail);
    }

    @Test
    default void testFindByMultipleOptions() {
        var filter = FundingCSVBean.builder()
                .companyName("Facebook")
                .round("c")
                .build();

        var possibleRow = getFinder().findBy(filter);

        possibleRow.ifPresentOrElse(row -> {
            Assertions.assertEquals(row.getPermalink(), "facebook");
            Assertions.assertEquals(row.getCompanyName(), "Facebook");
            Assertions.assertEquals(row.getNumberEmployees(), 450);
            Assertions.assertEquals(row.getCategory(), "web");
            Assertions.assertEquals(row.getCity(), "Palo Alto");
            Assertions.assertEquals(row.getState(), "CA");
            Assertions.assertEquals(row.getFundedDate(), LocalDate.of(2007, Month.OCTOBER, 1));
            Assertions.assertEquals(row.getRaisedAmount(), 300000000);
            Assertions.assertEquals(row.getRound(), "c");
        }, Assertions::fail);
    }

    @Test
    default void testFindByNotExists() {
        var filter = FundingCSVBean.builder()
                .companyName("NotFacebook")
                .round("c")
                .build();

        Assertions.assertTrue(getFinder().findBy(filter).isEmpty(), "FundingRaised.findBy should be empty.");
    }

    @Test
    default void testFirstRowIsNotHeader() {
        getFinder().findFirst(FundingCSVBean.builder().build()).ifPresent(firstRow -> {
            Assertions.assertNotEquals(firstRow.getPermalink(), "permalink");
            Assertions.assertNotEquals(firstRow.getCompanyName(), "company_name");
        });
    }

    @Test
    default void testHeaderIsNotFound() {
        Assertions.assertTrue(getFinder()
                .findFirst(FundingCSVBean
                        .builder()
                        .companyName("permalink")
                        .permalink("permalink")
                        .build())
                .isEmpty());
    }
}
