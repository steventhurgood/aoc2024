package org.nihilistic.aoc.day15;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class CoordinateTests {
    @Test
    public void testCoordinate_whenBoxStream_isCorrect() {
        // arrange

        // act

        List<Coordinate> coordinates = Coordinate.boxStream(3, 3).collect(Collectors.toList());

        assertThat(coordinates).containsExactly(
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                new Coordinate(2, 0),

                new Coordinate(0, 1),
                new Coordinate(1, 1),
                new Coordinate(2, 1),

                new Coordinate(0, 2),
                new Coordinate(1, 2),
                new Coordinate(2, 2));
    }
}
