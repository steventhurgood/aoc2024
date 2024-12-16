package org.nihilistic.aoc.day16;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public record Maze(Set<Coordinate> walls, Coordinate start, Coordinate end) {
    public static Maze fromString(String input) {
        var walls = new HashSet<Coordinate>();
        var start = Optional.<Coordinate>empty();
        ;
        var end = Optional.<Coordinate>empty();
        ;
        var x = 0;
        var y = 0;

        for (var i = 0; i < input.length(); i++) {
            var charAt = input.charAt(i);
            var coordinate = new Coordinate(x, y);
            switch (charAt) {
                case '\n':
                    x = 0;
                    y++;
                    break;
                case '.':
                    x++;
                    break;
                case '#':
                    walls.add(coordinate);
                    x++;
                    break;
                case 'S':
                    start = Optional.of(coordinate);
                    x++;
                    break;
                case 'E':
                    end = Optional.of(coordinate);
                    x++;
                    break;
            }
        }
        if (start.isEmpty()) {
            throw new IllegalArgumentException("No start location");
        }
        if (end.isEmpty()) {
            throw new IllegalArgumentException("No end location");

        }
        return new Maze(walls, start.get(), end.get());
    }

    @Override
    public String toString() {
        var maxX = walls.stream().mapToInt(Coordinate::x).max().getAsInt() + 1;
        var maxY = walls.stream().mapToInt(Coordinate::y).max().getAsInt() + 1;
        var out = new StringBuilder();

        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var coordinate = new Coordinate(x, y);
                if (walls.contains(coordinate)) {
                    out.append('#');
                    continue;
                }
                if (coordinate.equals(start)) {
                    out.append('S');
                    continue;
                }
                if (coordinate.equals(end)) {
                    out.append('E');
                    continue;
                }
                out.append('.');
            }
            out.append('\n');
        }
        return out.toString();
    }

    private long heuristic(Reindeer reindeer, Coordinate end) {
        var xDelta = reindeer.position().x() - end.x();
        var yDelta = reindeer.position().y() - end.y();

        return Math.abs(xDelta) + Math.abs(yDelta);
    }

    private static int cmp(Reindeer a, Reindeer b, Map<Reindeer, Long> fScore) {
        var aScore = fScore.getOrDefault(a, Long.MAX_VALUE);
        var bScore = fScore.getOrDefault(b, Long.MAX_VALUE);

        if (aScore < bScore) {
            return -1;
        }

        if (aScore > bScore) {
            return 1;
        }
        return 0;
    }

    public Set<Coordinate> findAllRoutes() {
        var bestDistanceTo = new HashMap<Reindeer, Long>();
        var cameFrom = new HashMap<Reindeer, List<Reindeer>>();

        var queue = new PriorityQueue<Reindeer>((a, b) -> cmp(a, b, bestDistanceTo));

        var current = new Reindeer(start, Direction.EAST);
        bestDistanceTo.put(current, 0L);
        queue.add(current);

        while (!queue.isEmpty()) {
            current = queue.poll();

            if (current.position().equals(end)) {
                // we are not going to find any shorter route, so don't consider neighbours
                continue;
            }

            for (var neighbourCost : current.neighbours(walls)) {
                var neighbour = neighbourCost.reindeer();
                var cost = neighbourCost.cost();

                var bestDistanceToNeighbour = bestDistanceTo.getOrDefault(neighbour, Long.MAX_VALUE);
                var distanceToCurrent = bestDistanceTo.get(current);
                var distanceToNeighbour = distanceToCurrent + cost;

                if (distanceToNeighbour == bestDistanceToNeighbour) {
                    // we have found two best routes to neighbour
                    cameFrom.get(neighbour).add(current);
                }
                if (distanceToNeighbour < bestDistanceToNeighbour) {
                    cameFrom.put(neighbour, Lists.newArrayList(current));
                    queue.add(neighbour);
                    bestDistanceTo.put(neighbour, distanceToNeighbour);
                }
            }
        }
        var endKeys = bestDistanceTo.keySet().stream().filter(reindeer -> reindeer.position().equals(end))
                .collect(Collectors.toList());

        var minScore = endKeys.stream().mapToLong(bestDistanceTo::get).min().getAsLong();

        var bestDistanceKeys = endKeys.stream().filter(key -> bestDistanceTo.get(key) == minScore).collect(Collectors.toList());

        var nodes = bestDistanceKeys.stream().map(key -> nodesAlongPath(key, cameFrom)).flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return nodes;

    }

    public static Set<Coordinate> nodesAlongPath(Reindeer end, Map<Reindeer, List<Reindeer>> cameFrom) {

        var coordinates = Sets.<Coordinate>newHashSet(end.position());

        if (!cameFrom.containsKey(end)) {
            return coordinates;
        }

        for (var previous : cameFrom.get(end)) {
            var previousPositions = nodesAlongPath(previous, cameFrom);
            coordinates.addAll(previousPositions);
        }
        return coordinates;

    }

    public long findRoute() {
        var current = new Reindeer(start, Direction.EAST);

        var gScore = new HashMap<Reindeer, Long>();
        gScore.put(current, 0L);

        var fScore = new HashMap<Reindeer, Long>();
        fScore.put(current, heuristic(current, end));

        var openSet = new PriorityQueue<Reindeer>((a, b) -> cmp(a, b, fScore));
        openSet.add(current);

        while (!openSet.isEmpty()) {
            current = openSet.poll();
            if (current.position().equals(end)) {
                return gScore.get(current);
            }

            for (var neighbour : current.neighbours(walls)) {
                var newReindeer = neighbour.reindeer();
                var cost = neighbour.cost();

                var currentGScore = gScore.get(current);
                var neighbourGScore = gScore.getOrDefault(newReindeer, Long.MAX_VALUE);

                var tentativeGScore = currentGScore + cost;
                if (tentativeGScore < neighbourGScore) {
                    // we have found a better route to neighbour
                    gScore.put(newReindeer, tentativeGScore);
                    fScore.put(newReindeer, tentativeGScore + heuristic(newReindeer, end));

                    if (!openSet.contains(newReindeer)) {
                        openSet.add(newReindeer);
                    }
                }

            }
        }
        throw new IllegalArgumentException("Maze is not solvable");

    }

    public String toStringWithPath(Set<Coordinate> nodes) {
        var maxX = walls.stream().mapToInt(Coordinate::x).max().getAsInt() + 1;
        var maxY = walls.stream().mapToInt(Coordinate::y).max().getAsInt() + 1;
        var out = new StringBuilder();

        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var coordinate = new Coordinate(x, y);
                if (nodes.contains(coordinate)) {
                    out.append('O');
                    continue;
                }
                if (walls.contains(coordinate)) {
                    out.append('#');
                    continue;
                }
                if (coordinate.equals(start)) {
                    out.append('S');
                    continue;
                }
                if (coordinate.equals(end)) {
                    out.append('E');
                    continue;
                }
                out.append('.');
            }
            out.append('\n');
        }
        return out.toString();
    }
}
