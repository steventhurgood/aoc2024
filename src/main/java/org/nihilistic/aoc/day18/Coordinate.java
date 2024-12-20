package org.nihilistic.aoc.day18;

public record Coordinate(int x, int y) {
    public static Coordinate fromString(String input) {
        var parts = input.split(",");
        var x = Integer.valueOf(parts[0]);
        var y = Integer.valueOf(parts[1]);
        return new Coordinate(x, y);
    }
}
