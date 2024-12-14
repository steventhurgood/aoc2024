package org.nihilistic.aoc.day14;

public enum Quadrant {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, NONE;

    // width and height are always odd
    public static Quadrant get(Coordinate position, int width, int height) {
        var midX = width / 2;
        var midY = height / 2;

        var x = position.x() % width;
        var y = position.y() % height;
        if (x == midX || y == midY) {
            return NONE;
        }

        if (x < midX) {
            // LEFT
            if (y < midY) {
                return TOP_LEFT;
            }
            return BOTTOM_LEFT;
        }
        // RIGHT
        if (y < midY) {
            return TOP_RIGHT;
        }
        return BOTTOM_RIGHT;
    }
}
