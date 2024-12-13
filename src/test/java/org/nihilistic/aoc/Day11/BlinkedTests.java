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

        var actual = blinker.countBlinkedDigits(125L, 6);

        // assert

        assertThat(actual).isEqualTo(7);
    }

    @Test
    public void testBlinker_whenBlinkDigit_isCorrect() {
        // arrange

        var blinker = Blinker.fromString("125 7");

        // act
        var actual = blinker.blinkDigit(125L, 6);

        // assert

        assertThat(actual).containsExactly(
            2097446912L,
            14168L,
            4048L,
            2L,
            0L,
            2L,
            4L
        );
    }
}
