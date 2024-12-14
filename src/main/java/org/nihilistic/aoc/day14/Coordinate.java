package org.nihilistic.aoc.day14;

public record Coordinate(int x, int y) {

    public Coordinate move(Coordinate velocity, int iterations) {
        return new Coordinate(x + velocity.x() * iterations, y + velocity.y() * iterations);
    }
}