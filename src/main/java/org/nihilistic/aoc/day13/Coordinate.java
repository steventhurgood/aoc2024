package org.nihilistic.aoc.day13;

public record Coordinate(int x, int y) {

    public double distanceTo(Coordinate prize) {
        return Math.sqrt(Math.pow(x - prize.x(), 2) + Math.pow(y-prize.y(), 2));
    }

    public Coordinate move(Coordinate velocity) {
        return new Coordinate(x + velocity.x(), y + velocity.y());
    }

}
