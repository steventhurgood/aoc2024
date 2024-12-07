package org.nihilistic.aoc.day6;

import java.util.HashSet;

import org.junit.Test;

import junit.framework.Assert;

public class GuardRoomTests {
    @Test
    public void testGuardRoom_whenFromString_returnsData() {
        // arrange

        var testInput = "#.#\n.^.\n.#.";

        var expectedGuard = new Guard(new Coordinate(1, 1), Guard.Direction.UP);
        var expectedObstructions = new HashSet<Coordinate>();
        expectedObstructions.add(new Coordinate(0, 0));
        expectedObstructions.add(new Coordinate(2, 0));
        expectedObstructions.add(new Coordinate(1, 2));
        var expected = new GuardRoom(3, 3, expectedGuard, expectedObstructions);

        // act
        var actual = GuardRoom.fromString(testInput);

        // assert

        Assert.assertEquals(expected, actual);

    }
}
