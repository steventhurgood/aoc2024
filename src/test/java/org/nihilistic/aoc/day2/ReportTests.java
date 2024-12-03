package org.nihilistic.aoc.day2;

import org.junit.Test;

import junit.framework.Assert;

public class ReportTests {
    @Test
    public void givenSafeIncrementingReport_whenSafe_thenSafe() {

        // arrange
        Report report = Report.fromString("1 2 3 4");

        // act

        Assert.assertTrue(report.safe());
    }    
}
