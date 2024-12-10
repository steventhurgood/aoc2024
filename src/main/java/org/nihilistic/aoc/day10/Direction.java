package org.nihilistic.aoc.day10;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate move(Coordinate origin) {
        return new Coordinate(x + origin.x(), y + origin.y());
    }

    public static Stream<Coordinate> adjacent(Coordinate origin, int width, int height) {
        return Arrays.stream(values()).map(direction -> direction.move(origin)).filter(coordinate -> coordinate.inBounds(width, height));
    }
}
