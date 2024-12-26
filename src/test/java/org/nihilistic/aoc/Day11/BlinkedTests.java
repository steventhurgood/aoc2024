package org.nihilistic.aoc.Day11;

import org.junit.Test;
import org.nihilistic.aoc.day11.Blinker;

import static com.google.common.truth.Truth.assertThat;

public class BlinkedTests {
    @Test
    public void testBlinker_whenCountBlinkedStones_isCorrect() {
        // arrange

        var blinker = Blinker.fromString("125 17");

        // act

        var actual = blinker.countStones(125L, 6);

        // assert

        assertThat(actual).isEqualTo(7);
    }
}
