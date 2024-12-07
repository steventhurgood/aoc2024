package org.nihilistic.aoc.day6;

import java.util.HashSet;
import java.util.Set;

public class Guard {
    Coordinate position;
    Direction direction;

    private Set<Coordinate> positions_covered = new HashSet<Coordinate>();

    public enum Direction {
        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        private int x_delta;
        private int y_delta;

        Direction(int x_delta, int y_delta) {
            this.x_delta = x_delta;
            this.y_delta = y_delta;
        }

        Coordinate move(Coordinate origin) {
            return new Coordinate(origin.x() + x_delta, origin.y() + y_delta);
        }

        public static Direction fromString(String character) {
            switch (character) {
                case "^":
                    return UP;
                case "v":
                    return DOWN;
                case ">":
                    return RIGHT;
                case "<":
                    return LEFT;
                default:
                    throw new IllegalArgumentException();
            }
        }

        Direction rotate() {
            switch (this) {
                case UP:
                    return RIGHT;
                case RIGHT:
                    return DOWN;
                case DOWN:
                    return LEFT;
                case LEFT:
                    return UP;
                default:
                    throw new IllegalArgumentException("No such direction: " + this);
            }
        }
    };

    public Guard(Coordinate position, Direction direction) {
        this.position = position;
        this.direction = direction;
        this.positions_covered.add(position);
    }

    public Guard(int x, int y, String character) {
        Coordinate position = new Coordinate(x, y);
        Direction direction = Direction.fromString(character);
        this.position = position;
        this.direction = direction;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Guard)) {
            return false;
        }

        Guard g = (Guard) o;

        return g.position.equals(position) && g.direction.equals(direction);
    }

    public boolean moveAndAvoidObstructions(int width, int height, Set<Coordinate> obstructions) {
        var newPosition = direction.move(position);
        if (obstructions.contains(newPosition)) {
            direction = direction.rotate();
        } else {
            if (newPosition.x() < 0 || newPosition.x() >= width || newPosition.y() < 0 || newPosition.y() >= height) {
                return false;
            }
            position = newPosition;
            positions_covered.add(newPosition);
        }
        return true;
    }

    public Set<Coordinate> walkUntilExit(int width, int height, Set<Coordinate> obstructions) {
        while (moveAndAvoidObstructions(width, height, obstructions)) {}
        return new HashSet<Coordinate>(positions_covered);
    }

    public Guard copy() {
        return new Guard(position, direction);
    }

    public Set<Coordinate> walkUntilExitOrLoop(int width, int height, HashSet<Coordinate> obstructions) {
        record State(Coordinate coordinate, Direction direction) {};
        Set<State> previousStates = new HashSet<State>();
        previousStates.add(new State(position, direction));
        while (moveAndAvoidObstructions(width, height, obstructions)) {
            var newState = new State(position, direction);
            if (previousStates.contains(newState)) {
                // we have a loop
                return new HashSet<Coordinate>(positions_covered);
            }
            previousStates.add(new State(position, direction));
        }
        // we have left the area without finding a loop
        return null; // ugh
    }
}
