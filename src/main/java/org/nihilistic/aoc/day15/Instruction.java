package org.nihilistic.aoc.day15;

public enum Instruction {
    UP('^', new Coordinate(0, -1)),
    DOWN('v', new Coordinate(0, 1)),
    LEFT('<', new Coordinate(-1, 0)),
    RIGHT('>', new Coordinate(1, 0));

    private int directionint;
    private Coordinate delta;

    private Instruction(int directionint, Coordinate delta) {
        this.directionint = directionint;
        this.delta = delta;
    }

    public Coordinate move(Coordinate coordinate) {
        return coordinate.move(delta);
    }

    public static Instruction fromChar(int directionint) {
        for (var instruction: Instruction.values()) {
            if (instruction.directionint == directionint) {
                return instruction;
            }
        }
        throw new IllegalArgumentException("Invalid direction: " + directionint);
    }
}
