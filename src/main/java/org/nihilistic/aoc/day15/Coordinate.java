package org.nihilistic.aoc.day15;

public record Coordinate(int x, int y) {

    public Coordinate move(Coordinate delta) {
        return new Coordinate (x + delta.x, y + delta.y);
    }
    
}
