package org.nihilistic.aoc.day8;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class CoordinateTests {
    @Test
    public void testCoordinate_whenGetAntinodesTopLeft_isCorrect() {
        // arrange
        Coordinate existingNode = new Coordinate(4, 3);
        Coordinate newNode = new Coordinate(5, 5);

        Coordinate expected[] = {
                new Coordinate(3, 1),
                new Coordinate(6, 7) };

        // act
        var actual = newNode.getAntinodes(existingNode);

        assertThat(actual).containsExactly(expected[0], expected[1]);

    }

    @Test
    public void testCoordinate_whenGetAntinodesTopRight_isCorrect() {
        // arrange
        Coordinate existingNode = new Coordinate(4, 5);
        Coordinate newNode = new Coordinate(5, 3);

        Coordinate expected[] = {
                new Coordinate(3, 7),
                new Coordinate(6, 1) };

        // act
        var actual = newNode.getAntinodes(existingNode);

        assertThat(actual).containsExactly(expected[0], expected[1]);

    }
}
