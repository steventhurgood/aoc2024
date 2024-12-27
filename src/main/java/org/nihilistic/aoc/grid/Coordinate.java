package org.nihilistic.aoc.grid;

import java.util.List;

public record Coordinate(Long x, Long y) {
    public Coordinate(int x, int y) {
        this((long)x, (long)y);
    }

    public List<Coordinate> adjacent() {
        return List.of(
                new Coordinate(x - 1, y),
                new Coordinate(x + 1, y),
                new Coordinate(x, y - 1),
                new Coordinate(x, y + 1));
    }
}
