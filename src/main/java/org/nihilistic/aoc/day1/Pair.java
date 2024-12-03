package org.nihilistic.aoc.day1;

public record Pair(Integer left, Integer right) {
    private static String space = "\\s+";
    public static Pair fromString(String line) {
        var parts = line.split(space);
        var left = Integer.valueOf(parts[0]);
        var right = Integer.valueOf(parts[1]);
        return new Pair(left, right);
    }
}
