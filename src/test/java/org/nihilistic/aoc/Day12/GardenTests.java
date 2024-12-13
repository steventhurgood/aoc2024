package org.nihilistic.aoc.Day12;

import org.junit.Test;
import org.nihilistic.aoc.day12.Garden;
import static com.google.common.truth.Truth.assertThat;

public class GardenTests {
    @Test
    public void testGarden_whenScore_isCorrect() {
        // arrange
        var input = "AAAA\nBBCD\nBBCC\nEEEC";
        var garden = Garden.fromString(input);

        // act
        var actual = garden.fencingCost();

        //
        assertThat(actual).isEqualTo(140);
    }

    @Test
    public void testGarden_whenScoreMap2_isCorrect() {
        // arrange
        var input = "OOOOO\nOXOXO\nOOOOO\nOXOXO\nOOOOO";
        var garden = Garden.fromString(input);

        // act
        var actual = garden.fencingCost();

        //
        assertThat(actual).isEqualTo(772);
    }
}
