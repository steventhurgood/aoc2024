package org.nihilistic.aoc.Day12;

import org.junit.Test;
import org.nihilistic.aoc.day12.Garden;
import org.nihilistic.aoc.grid.Coordinate;

import static com.google.common.truth.Truth.assertThat;

import java.util.Map;
import java.util.Set;

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

    @Test
    public void testGarden_whenCountFences_isCorrect() {
        var garden = new Garden(Map.of());

        var area = Set.of(
            new Coordinate(4, 2),
            new Coordinate(4, 3),
            new Coordinate(5, 3),
            new Coordinate(5, 4)
        );

        var actual = garden.countFences(area);

        assertThat(actual).isEqualTo(8);
    }
}
