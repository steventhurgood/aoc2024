package org.nihilistic.aoc.day10;

public record Coordinate(int x, int y) {

    public boolean inBounds(int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
