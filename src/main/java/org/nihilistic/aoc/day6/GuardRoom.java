package org.nihilistic.aoc.day6;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GuardRoom {
    private int width;
    private int height;

    private Guard guard;
    private Set<Coordinate> obstructions;

    GuardRoom(int width, int height, Guard guard, Set<Coordinate> obstructions) {
        this.width = width;
        this.height = height;
        this.guard = guard;
        this.obstructions = obstructions;
    }

    public static GuardRoom fromString(String input) {
        var reader = new BufferedReader(new StringReader(input));
        String lines[][] = reader.lines().map(line -> line.split("")).toArray(String[][]::new);

        var height = lines.length;
        var width = lines[0].length;

        var obstructions = new HashSet<Coordinate>();
        Guard guard = null;

        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                var character = lines[y][x];
                if (character.equals(".")) {
                    continue;
                }
                if (character.equals("#")) {
                    obstructions.add(new Coordinate(x, y));
                    continue;
                }
                guard = new Guard(x, y, character);
            }
        }
        if (Objects.isNull(guard)) {
            throw new IllegalArgumentException();
        }
        return new GuardRoom(width, height, guard, obstructions);
    }

    public int walk() {
        var positionsCovered = guard.walkUntilExit(width, height, obstructions);
        return positionsCovered.size(); // because we also walked on one out-of-bounds location
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof GuardRoom)) {
            return false;
        }

        GuardRoom g = (GuardRoom) o;

        if (!g.guard.equals(guard)) {
            return false;
        }

        if (g.obstructions.size() != obstructions.size()) {
            return false;
        }

        for (Coordinate c : obstructions) {
            if (!g.obstructions.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public int findObstructionsThatCauseLoops() {
        var originalGuard = guard.copy();
        // only places where it makes sense to add obstructions are places where the
        // guard walks.
        var positionsCovered = guard.walkUntilExit(width, height, obstructions);

        // don't block the starting position
        positionsCovered.remove(originalGuard.position);

        int loopsFound = 0;
        for (Coordinate toObstruct : positionsCovered) {
            var walkingGuard = originalGuard.copy();
            var newObstructions = new HashSet<Coordinate>(obstructions);
            newObstructions.add(toObstruct);
            var newPositions = walkingGuard.walkUntilExitOrLoop(width, height, newObstructions);
            if (newPositions != null) {
                loopsFound++;
            }
        }
        return loopsFound;
    }

}
