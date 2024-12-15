package org.nihilistic.aoc.day15;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Coordinate(int x, int y) {

    public Coordinate move(Coordinate delta) {
        return new Coordinate(x + delta.x, y + delta.y);
    }

    public Stream<Coordinate> widen() {
        return Stream.of(
                new Coordinate(x * 2, y),
                new Coordinate(x * 2 + 1, y));
    }

    public static Stream<Coordinate> boxStream(int maxX, int maxY) {
        var maxI = maxX * maxY;
        return IntStream.range(0, maxI).mapToObj(i -> {
            var x = i % maxX;
            var y = i / maxX;
            return new Coordinate(x, y);
        });
    }
}
