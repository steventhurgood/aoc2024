package org.nihilistic.aoc.day20;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.nihilistic.aoc.graph.Edge;
import org.nihilistic.aoc.graph.Graph;
import org.nihilistic.aoc.graph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Racetrack(Set<Coordinate> walls, Coordinate start, Coordinate end)
        implements Graph<org.nihilistic.aoc.day20.Racetrack.Coordinate> {

    private static Logger logger = LoggerFactory.getLogger(Racetrack.class);

    public record Coordinate(int x, int y) implements Node {
        public List<Coordinate> adjacent() {
            return List.of(
                    new Coordinate(x + 1, y),
                    new Coordinate(x - 1, y),
                    new Coordinate(x, y + 1),
                    new Coordinate(x, y - 1));
        }

        public List<Coordinate> cheatAdjacent() {
            return List.of(
                    new Coordinate(x + 2, y),
                    new Coordinate(x - 2, y),
                    new Coordinate(x, y + 2),
                    new Coordinate(x, y - 2));
        }

        public List<Coordinate> cheatAdjacent(int picoseconds) {
            // return all coordinates where the x + y offset is < picoseconds
            var coordinates = new ArrayList<Coordinate>();
            for (var xDelta=-picoseconds; xDelta <= picoseconds; xDelta++) {
                for (var yDelta=-picoseconds; yDelta <= picoseconds; yDelta++) {
                    if (Math.abs(xDelta) + Math.abs(yDelta) > picoseconds) {
                        continue;
                    }
                    if (xDelta == 0 && yDelta == 0) {

                    }
                    coordinates.add(new Coordinate(x+xDelta, y+yDelta));

                } 
            } 
            return coordinates;
        }
    };

    public int width() {
        return walls.stream().mapToInt(Coordinate::x).max().getAsInt() + 1;
    }

    public int height() {
        return walls.stream().mapToInt(Coordinate::y).max().getAsInt() + 1;
    }

    public static Racetrack fromString(String input) {
        var walls = new HashSet<Coordinate>();

        Coordinate start = null;
        Coordinate end = null;

        var x = 0;
        var y = 0;
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            switch (c) {
                case '\n':
                    x = 0;
                    y++;
                    break;
                case '.':
                    x++;
                    break;
                case '#':
                    walls.add(new Coordinate(x, y));
                    x++;
                    break;
                case 'S':
                    start = new Coordinate(x, y);
                    x++;
                    break;
                case 'E':
                    end = new Coordinate(x, y);
                    x++;
                    break;
            }
        }
        return new Racetrack(walls, start, end);
    }

    @Override
    public String toString() {
        return toString(Collections.emptySet());
    }

    public String toString(Collection<Coordinate> coordinates) {
        var out = new StringBuilder();

        var pathPositions = new HashSet<Coordinate>(coordinates);

        int width = width();
        int height = height();

        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                var coordinate = new Coordinate(x, y);
                if (pathPositions.contains(coordinate)) {
                    out.append('o');
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
                if (walls.contains(coordinate)) {
                    out.append('#');
                    continue;
                }
                out.append('.');
            }
            out.append('\n');
        }
        return out.toString();
    }

    @Override
    public List<Edge<Coordinate>> adjacent(Coordinate current) {
        var width = width();
        var height = height();

        var adjacent = new ArrayList<Edge<Coordinate>>();
        for (var neighbour : current.adjacent()) {
            if (neighbour.x() >= 0 && neighbour.x() < width && neighbour.y() >= 0 && neighbour.y() < height) {
                if (walls.contains(neighbour)) {
                    continue;
                }
                adjacent.add(new Edge<Coordinate>(current, neighbour, 1));
            }
        }
        return adjacent;
    }

    @Override
    public boolean isEnd(Coordinate pathState) {
        return pathState.equals(end);
    }

    public Map<Coordinate, Integer> pathPositions(List<Coordinate> coordinates) {
        return IntStream.range(0, coordinates.size()).boxed()
                .collect(Collectors.toMap(coordinates::get, Function.identity()));
    }

    public List<Long> cheatDistances(List<Coordinate> coordinates, int picoseconds) {
        var positions = pathPositions(coordinates);
        var distances = new ArrayList<Long>();
        for (var i = 0; i < coordinates.size(); i++) {
            logger.info("Finding cheats at [" + i + "/" + coordinates.size() + "]");
            // can we skip ahead by cheating.
            var current = coordinates.get(i);
            for (var cheat : current.cheatAdjacent(picoseconds)) {
                var cheatCost = Math.abs(cheat.x() - current.x()) + Math.abs(cheat.y() - current.y());
                if (positions.containsKey(cheat)) {
                    var cheatPosition = positions.get(cheat);
                    // we can skip to a new position.
                    if (cheatPosition > i + cheatCost) { // because it takes 2 picoseconds to cheat
                        // logger.info("Found a cheat: " + current + "[" + i + "] - " + cheat + "[" + cheatPosition
                        //         + "] saving " + (cheatPosition - (i + cheatCost)) + " picoseconds");
                        distances.add((long) cheatPosition - (i + cheatCost));
                    }
                }
            }
        }
        return distances;
    }

    public Map<Long, Long> countSavings(List<Coordinate> coordinates, int picoseconds) {
        return cheatDistances(coordinates, picoseconds).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
