package org.nihilistic.aoc.day16;
public enum Direction {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    private Coordinate delta;

    private Direction(int x, int y) {
        this.delta = new Coordinate(x, y);
    }

    public Direction clockwise() {
        return values()[(ordinal() + 1) % 4];
    } 

    public Direction counterclockwise() {
        return values()[(ordinal() + 3) % 4];
    }

    public Coordinate move (Coordinate position) {
        return new Coordinate(position.x() + delta.x(), position.y() + delta.y());
    }
}
