package org.nihilistic.aoc.day18;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.PriorityQueue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record MemoryMaze(List<Coordinate> corrupted) {
    private static Logger logger = LoggerFactory.getLogger(MemoryMaze.class);
    public static MemoryMaze fromString(String input) {
        var corrupted = input.lines().map(Coordinate::fromString).toList();
        return new MemoryMaze(corrupted);
    }

    public boolean pathIsBlocked(Coordinate start, Coordinate end, int firstBytes) {
        return findPath(start, end, firstBytes).isEmpty();
    }

    public OptionalInt findPath(Coordinate start, Coordinate end) {
        int width = end.x(); // inclusive
        int height = end.y(); // inclusive
        var cameFrom = new HashMap<Coordinate, Coordinate>();
        var walls = new HashSet<Coordinate>(corrupted);
        var bestDistanceTo = new HashMap<Coordinate, Integer>();
        bestDistanceTo.put(start, 0);
        var distanceComparator = Comparator.comparingInt(bestDistanceTo::get);
        var queue = new PriorityQueue<Coordinate>(distanceComparator);
        queue.add(start);

        while (queue.size() > 0) {
            var current = queue.poll();
            if (current.equals(end)) {
                // logPath(cameFrom, end);
                return OptionalInt.of(bestDistanceTo.get(current));
            }
            var bestDistanceToCurrent = bestDistanceTo.get(current);
            for (var neighbour: neighbours(current, walls, width, height )) {
                var tentativeDistanceToNeighbour = bestDistanceToCurrent + 1;
                var bestDistanceToNeighbour = bestDistanceTo.getOrDefault(neighbour, Integer.MAX_VALUE);

                if (tentativeDistanceToNeighbour < bestDistanceToNeighbour) {
                    bestDistanceTo.put(neighbour, tentativeDistanceToNeighbour);
                    cameFrom.put(neighbour, current);
                    queue.add(neighbour);
                }
            }
        }
        return OptionalInt.empty();
    }

    private static Coordinate offsets[] = {
            new Coordinate(0, -1), // UP
            new Coordinate(0, 1), // DOWN
            new Coordinate(-1, 0), // LEFT
            new Coordinate(1, 0), // RIGHT
    };

    private Iterable<Coordinate> neighbours(Coordinate current, Set<Coordinate> walls, int width, int height) {
        var newCoordinates = new ArrayList<Coordinate>(4);
        for (var offset : offsets) {
            var newCoordinate = new Coordinate(current.x() + offset.x(), current.y() + offset.y());
            if (newCoordinate.x() < 0 || newCoordinate.x() > width || newCoordinate.y() < 0
                    || newCoordinate.y() > height) {
                continue;
            }
            if (walls.contains(newCoordinate)) {
                continue;
            }
            newCoordinates.add(newCoordinate);

        }
        return newCoordinates;
    }

    public OptionalInt findPath(Coordinate start, Coordinate end, int firstBytes) {
        var maze = new MemoryMaze(corrupted.subList(0, firstBytes));
        return maze.findPath(start, end);
    }

    private static void logPath(Map<Coordinate, Coordinate>cameFrom, Coordinate end) {
        logger.info(end.toString());
        var current = end;
        while (cameFrom.containsKey(current)) {
            var prev = cameFrom.get(current);
            logger.info(prev.toString() + " -> " + current.toString());
            current = prev;
        }
        logger.info(current.toString());
    }
    public Optional<Coordinate> findLeastBytesThatMakeMazeImpassibleBinarySearch(Coordinate start, Coordinate end) {
        var alreadyCalculated = new HashMap<Integer, Boolean>();
        // we need to find i such that findpath(..., i) succeeds but findpath(..., i+1) does not.
        var left = 1;
        var right = corrupted.size();
        while (left <= right) {
            // we know that right+1 is blocked, and left-1 is not
            var middle = (left + right)/2;
            if (middle >= corrupted.size() - 1) {
                // all routes succeed
                return Optional.empty();
            }
            var beforeMiddle = middle-1;

            var middleBlocked = alreadyCalculated.computeIfAbsent(middle, key -> pathIsBlocked(start, end, middle));
            var beforeMiddleBlocked = alreadyCalculated.computeIfAbsent(beforeMiddle, key -> pathIsBlocked(start, end, beforeMiddle));

            if (!beforeMiddleBlocked && middleBlocked) {
                /*
                 * We have found what we want.
                 * 
                 * return the -1th item from the list, because if it is the nth
                 * drop that blocks the maze, the that drop is at index n-1
                 */
                return Optional.of(corrupted.get(middle-1));
            }
            if (beforeMiddleBlocked && middleBlocked) {
                // the target value is less than middle
                right = middle-1;
                continue;
            }
            if (!beforeMiddleBlocked && !middleBlocked) {
                // the target value is greater than middle
                left = middle+1;
                continue;
            }
        }
        return Optional.empty();
    }

    public Coordinate findLeastBytesThatMakeMazeImpassible(Coordinate start, Coordinate end) {
        // find i such that i is blocked, but i-1 is not
        for (var i=0; i < corrupted.size(); i++) {
            var pathLength = findPath(start, end, i);
            if (pathLength.isEmpty()) {
                logger.info("Path after " + i + " bytes is impossible");
                logger.info("blocking coordinate is " + corrupted.get(i-1));
                return corrupted.get(i-1);
            }
            logger.info("Path after " + i + " bytes is " + pathLength.getAsInt());
        }
        throw new IllegalArgumentException("No valid solution");
    }

    public Coordinate get(int i) {
        return corrupted.get(i);
    }
}
