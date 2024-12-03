package org.nihilistic.aoc.day3;

import org.junit.Test;

import junit.framework.Assert;

public class MultiplierTests {

    @Test
    public void testMulitplier_whenFromString_returnsCorrectResults() {
        // arrange
        var input = "mul(12,17)";
        var expected = new Multiplier(12, 17);

        // act
        var actual = Multiplier.fromString(input);

        // assert
        Assert.assertEquals(expected, actual);
    }
}
