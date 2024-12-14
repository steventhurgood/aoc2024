package org.nihilistic.aoc.day14;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Robots(List<Robot> robots, int width, int height) {
    public static Robots fromString(String input, int width, int height) {
        var reader = new BufferedReader(new StringReader(input));
        var robots = reader.lines().map(Robot::fromString).collect(Collectors.toList());
        return new Robots(robots, width, height);
    }

    public Robots simulate(int iterations) {
        List<Robot> newRobots = robots.stream().map(r -> r.move(iterations)).collect(Collectors.toList());
        return new Robots(newRobots, width, height);
    }

    public Long countByQuadrant() {
        Map<Quadrant, Long> count = robots.stream()
                .collect(Collectors.groupingBy(r -> r.quadrant(width, height), Collectors.counting()));
        return count.keySet().stream().filter(k -> k != Quadrant.NONE).map(count::get).mapToLong(Long::valueOf).reduce(1, (a, b) -> a*b);
    }

    @Override
    public String toString() {
        Map<Coordinate, Long> countByPosition = robots.stream()
                .collect(Collectors.groupingBy(r -> r.absolutePosition(width, height), Collectors.counting()));
        var out = new StringBuilder();
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                if (countByPosition.containsKey(new Coordinate(x, y))) {
                    out.append(countByPosition.get(new Coordinate(x, y)));
                } else {
                    out.append(".");
                }
            }
            out.append("\n");
        }
        return out.toString();
    }
}
