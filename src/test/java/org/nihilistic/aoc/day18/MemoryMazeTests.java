package org.nihilistic.aoc.day18;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class MemoryMazeTests {
    static String input = """
            5,4
            4,2
            4,5
            3,0
            2,1
            6,3
            2,4
            1,5
            0,6
            3,3
            2,6
            5,1
            1,2
            5,5
            2,5
            6,5
            1,4
            0,4
            6,4
            1,1
            6,1
            1,0
            0,5
            1,6
            2,0
                    """;

    @Test
    public void testMemoryMaze_whenFindPath_isCorrect() {
        var maze = MemoryMaze.fromString(input);
        var actual = maze.findPath(new Coordinate(0, 0), new Coordinate(6, 6), 12);

        assertThat(actual.getAsInt()).isEqualTo(22);
    }
    @Test
    public void testMemoryMaze_whenGet_isCorrect() {
        var maze = MemoryMaze.fromString(input);

        var actual = maze.get(20);
        assertThat(actual).isEqualTo(new Coordinate(6, 1));
    }

    @Test
    public void testMemoryMaze_whenFindImpossiblePath_isCorrect() {
        var maze = MemoryMaze.fromString(input);
        var actual = maze.findPath(new Coordinate(0, 0), new Coordinate(6, 6), 21);
        assertThat(actual).isEmpty();
    }

    @Test
    public void testMemoryMaze_whenJustBeforeFindImpossiblePath_isCorrect() {
        var maze = MemoryMaze.fromString(input);
        var actual = maze.findPath(new Coordinate(0, 0), new Coordinate(6, 6), 20);
        assertThat(actual).isPresent();
    }
}
