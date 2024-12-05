package org.nihilistic.aoc.day5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PageSorterTests {
    String testInput;

    @Before
    public void Setup() {
        var testResource = this.getClass().getResourceAsStream("/static/day5/day5_test.txt");
        var reader = new BufferedReader(new InputStreamReader(testResource));
        testInput = reader.lines().collect(Collectors.joining("\n"));

    }

    @Test
    public void testPageSorter_whenFilter_filters() {
        var parts = testInput.split("\n\n");
        var spec = parts[0];
        var pages = parts[1];

        var comparator = PageComparator.fromString(spec);
        var pageSorter = new PageSorter(comparator);

        String actual[] = pageSorter.filterLines(pages).toArray(String[]::new);

        String[] expected = {
                "75,47,61,53,29",
                "97,61,53,29,13",
                "75,29,13",
        };

        Assert.assertArrayEquals(expected, actual);

    }
}
