package org.nihilistic.aoc.day14;

import java.util.regex.Pattern;

public record Robot(Coordinate position, Coordinate velocity) {
    private static Pattern pattern = Pattern.compile("p=(?<px>[0-9]+),(?<py>[0-9]+) v=(?<vx>-?[0-9]+),(?<vy>-?[0-9]+)");

    // p=0,4 v=3,-3
    public static Robot fromString(String input) {
        var matcher = pattern.matcher(input);
        if (matcher.find()) {
            var px = Integer.valueOf(matcher.group("px"));
            var py = Integer.valueOf(matcher.group("py"));
            var vx = Integer.valueOf(matcher.group("vx"));
            var vy = Integer.valueOf(matcher.group("vy"));
            return new Robot(new Coordinate(px, py), new Coordinate(vx, vy));
        }
        throw new IllegalArgumentException("invalid robot: " + input);
    }

    public Robot move(int iterations) {
        return new Robot(position.move(velocity, iterations), velocity);
    }

    public Quadrant quadrant(int width, int height) {
        return Quadrant.get(absolutePosition(width, height), width, height);
    }

    public Coordinate absolutePosition(int width, int height) {
        var x = position.x() % width;
        if (x < 0) {
            x += width;
        }
        var y = position.y() % height;
        if (y < 0) {
            y += height;
        }
        return new Coordinate(x, y);
    }
}
