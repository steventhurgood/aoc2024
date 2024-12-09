package org.nihilistic.aoc.day8;

public record CoordinateFilter(int width, int height) {
    public boolean filter(Coordinate c) {
        return c.inBounds(width, height);
    }
}
