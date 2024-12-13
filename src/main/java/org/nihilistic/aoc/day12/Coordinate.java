package org.nihilistic.aoc.day12;

import java.util.List;

import com.google.common.collect.Lists;

public record Coordinate(int x, int y) {
    List<Coordinate> adjacent() {
        return Lists.newArrayList(
                new Coordinate(x - 1, y),
                new Coordinate(x + 1, y),
                new Coordinate(x, y - 1),
                new Coordinate(x, y + 1));
    }
}
