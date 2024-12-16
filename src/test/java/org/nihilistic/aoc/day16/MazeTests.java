package org.nihilistic.aoc.day16;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MazeTests {

    private static Logger logger = LoggerFactory.getLogger(MazeTests.class);

    private String input = """
            ####
            #.E#
            #S.#
            ####
                    """;

    @Test
    public void testMaze_whenFromString_isCorrect() {

        var maze = Maze.fromString(input);

        assertThat(maze.start()).isEqualTo(new Coordinate(1, 2));
        assertThat(maze.end()).isEqualTo(new Coordinate(2, 1));
        assertThat(maze.walls()).containsExactly(
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                new Coordinate(2, 0),
                new Coordinate(3, 0),
                new Coordinate(0, 1),
                new Coordinate(3, 1),
                new Coordinate(0, 2),
                new Coordinate(3, 2),
                new Coordinate(0, 3),
                new Coordinate(1, 3),
                new Coordinate(2, 3),
                new Coordinate(3, 3));
    }

    @Test
    public void testMaze_whenToString_isCorrect() {
        var maze = Maze.fromString(input);

        var actual = maze.toString();

        assertThat(actual).isEqualTo(input);
    }

    @Test
    public void testMaze_whenFindRouteSmallMaze_isCorrect() {
        var input = """
                ####
                #.E#
                #S##
                ####
                """;
        var maze = Maze.fromString(input);

        var actual = maze.findRoute();

        var expected = 1000 + 1 + 1000 + 1;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testMaze_whenFindRoute_isCorrect() {
        var input = """
                ###############
                #.......#....E#
                #.#.###.#.###.#
                #.....#.#...#.#
                #.###.#####.#.#
                #.#.#.......#.#
                #.#.#####.###.#
                #...........#.#
                ###.#.#####.#.#
                #...#.....#.#.#
                #.#.#.###.#.#.#
                #.....#...#.#.#
                #.###.#.#.#.#.#
                #S..#.....#...#
                ###############

                                """;

        var maze = Maze.fromString(input);
        var actual = maze.findRoute();

        assertThat(actual).isEqualTo(7036);
    }

    @Test
    public void testMaze_whenFindRouteLargeMaze_isCorrect() {
        var input = """
                        #################
                #...#...#...#..E#
                #.#.#.#.#.#.#.#.#
                #.#.#.#...#...#.#
                #.#.#.#.###.#.#.#
                #...#.#.#.....#.#
                #.#.#.#.#.#####.#
                #.#...#.#.#.....#
                #.#.#####.#.###.#
                #.#.#.......#...#
                #.#.###.#####.###
                #.#.#...#.....#.#
                #.#.#.#####.###.#
                #.#.#.........#.#
                #.#.#.#########.#
                #S#.............#
                #################
                                """;

        var maze = Maze.fromString(input);
        var actual = maze.findRoute();

        assertThat(actual).isEqualTo(11048);
    }

    @Test
    public void testMaze_whenFindAllRoutesSmallMaze_isCorrect() {
        var input = """
                ####
                #.E#
                #S##
                ####
                """;
        var maze = Maze.fromString(input);

        var actual = maze.findAllRoutes();

        assertThat(actual).containsExactly(
                new Coordinate(1, 2),
                new Coordinate(1, 1),
                new Coordinate(2, 1));
    }

    @Test
    public void testMaze_whenFindAllRoutes_isCorrectSize() {
        var input = """
                ###############
                #.......#....E#
                #.#.###.#.###.#
                #.....#.#...#.#
                #.###.#####.#.#
                #.#.#.......#.#
                #.#.#####.###.#
                #...........#.#
                ###.#.#####.#.#
                #...#.....#.#.#
                #.#.#.###.#.#.#
                #.....#...#.#.#
                #.###.#.#.#.#.#
                #S..#.....#...#
                ###############

                                """;

        var maze = Maze.fromString(input);
        var nodes = maze.findAllRoutes(); 
        logger.info("\n" + maze.toStringWithPath(nodes));

        assertThat(nodes.size()).isEqualTo(45);
    }
}
