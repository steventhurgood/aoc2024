package org.nihilistic.aoc.day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import junit.framework.Assert;

public class ReportsTests {
    @Test
    public void testExampleInput_whenCountingSafeLevels_returnsCorrectAnswer() throws IOException {

        // arrange
        var testResource = this.getClass().getResourceAsStream("/static/day2/day2_test.txt");
        var reader = new BufferedReader(new InputStreamReader(testResource));
        var input = reader.lines().collect(Collectors.joining("\n"));
        var expected = new Reports(
                Arrays.asList(
                        new Report(Arrays.asList(7, 6, 4, 2, 1)),
                        new Report(Arrays.asList(1, 2, 7, 8, 9)),
                        new Report(Arrays.asList(9, 7, 6, 2, 1)),
                        new Report(Arrays.asList(1, 3, 2, 4, 5)),
                        new Report(Arrays.asList(8, 6, 4, 4, 1)),
                        new Report(Arrays.asList(1, 3, 6, 7, 9))));

        // act
        var reports = Reports.fromString(input);
        Long safeCount = reports.countSafe();
        Long expectedCount = 2L;

        // assert
        Assert.assertEquals(expected, reports);
        Assert.assertEquals(expectedCount, safeCount);

    }
}
