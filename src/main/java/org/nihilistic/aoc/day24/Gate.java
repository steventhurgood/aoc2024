package org.nihilistic.aoc.day24;

public record Gate(GateOperation operation, String left, String right, String output) {
    public static Gate fromString(String input) {
        // x00 AND y00 -> z00
        var parts = input.split(" ");
        var operation = GateOperation.valueOf(parts[1]);
        var left = parts[0];
        var right = parts[2];
        var output = parts[4];
        return new Gate(operation, left, right, output);
    }

}
