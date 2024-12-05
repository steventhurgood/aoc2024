package org.nihilistic.aoc.day5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

public class PageComparatorTests {

    String testInput;

    @Before
    public void Setup() {
        var testResource = this.getClass().getResourceAsStream("/static/day5/day5_test.txt");
        var reader = new BufferedReader(new InputStreamReader(testResource));
        testInput = reader.lines().collect(Collectors.joining("\n"));

    }

    @Test
    public void testPageComparator_whenFromString_returnsCorrect() {
        // arrange

        String input = "10|20\n20|30\n10|30\n15|30";
        var expectedOrder = new HashMap<Integer, Set<Integer>>();
        expectedOrder.put(10, Sets.newHashSet(20, 30));
        expectedOrder.put(20, Sets.newHashSet(30));
        expectedOrder.put(15, Sets.newHashSet(30));
        var expected = new PageComparator(expectedOrder);

        var actual = PageComparator.fromString(input);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPageComparator_whenSorting_hasCorrectOrder() {
        // arrange
        String input = "10|20\n20|30\n10|30\n15|30";
        var comparator = PageComparator.fromString(input);

        Integer actual[] = { 20, 10, 30};
        Integer expected[] = { 10, 20, 30};

        // act

        Arrays.sort(actual, comparator);

        // assert

        Assert.assertArrayEquals(expected, actual);

    }
}
