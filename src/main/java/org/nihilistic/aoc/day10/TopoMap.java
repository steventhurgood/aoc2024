package org.nihilistic.aoc.day10;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

public record TopoMap(int width, int height, List<Integer> data, Map<TrailheadCacheKey, Set<Coordinate>> headCache, Map<TrailheadCacheKey, Integer> routeCountCache) {
    private static Logger logger = LoggerFactory.getLogger(TopoMap.class);
    public static TopoMap fromString(String input) {

        int width = input.indexOf("\n");
        int height = input.length() / width;
        var data = input.chars().filter(c -> c != '\n').map(c -> c - '0').boxed().collect(Collectors.toList());
        var headCache = new HashMap<TrailheadCacheKey, Set<Coordinate>>();
        var routeCountCache = new HashMap<TrailheadCacheKey, Integer>();

        return new TopoMap(width, height, data, headCache, routeCountCache);
    }

    public long score() {
        long score = 0;
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                int altitude = getAltitude(x, y);
                if (altitude == 0) {
                    score += trailHeads(0, x, y).size();
                }
            }
        }
        return score;
    }

    public Set<Coordinate> trailHeads(int altitude, int x, int y) {
        return trailHeads(altitude, new Coordinate(x, y));
    }

    public Set<Coordinate> trailHeads(int altitude, Coordinate coordinate) {
        logger.info("Counting trail heads starting from altitude {} at {}", altitude, coordinate);
        var key = new TrailheadCacheKey(altitude, coordinate);
        if (headCache.containsKey(key)) {
            return headCache.get(key);
        }
        if (altitude == 9) {
            return Sets.newHashSet(coordinate);
        }
        
        Set<Coordinate> heads = Direction.adjacent(coordinate, width, height)
                .filter(adjacentCoordinate -> isAtAltitude(altitude + 1, adjacentCoordinate))
                .map(
                        adjacentCoordinate -> trailHeads(altitude + 1, adjacentCoordinate))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        if (!headCache.containsKey(key)) {
            headCache.put(key, new HashSet<Coordinate>());
        }
        headCache.get(key).addAll(heads);
        return heads;
    }

    public boolean isAtAltitude(int altitude, Coordinate coordinate) {
        return getAltitude(coordinate) == altitude;
    }

    public int getAltitude(int x, int y) {
        return data.get(y * width + x);
    }

    public int getAltitude(Coordinate c) {
        return getAltitude(c.x(), c.y());
    }

    public Integer countDistinctTrailHeads(int altitude, int x, int y) {
        return countDistinctTrailHeads(altitude, new Coordinate(x, y));
    }

    public Integer countDistinctTrailHeads(int altitude, Coordinate coordinate) {
        logger.info("Counting distinct trail heads starting from altitude {} at {}", altitude, coordinate);
        var key = new TrailheadCacheKey(altitude, coordinate);
        if (routeCountCache.containsKey(key)) {
            return routeCountCache.get(key);
        }
        if (altitude == 9) {
            return 1;
        }
        
        Integer count = Direction.adjacent(coordinate, width, height)
                .filter(adjacentCoordinate -> isAtAltitude(altitude + 1, adjacentCoordinate))
                .mapToInt(
                        adjacentCoordinate -> countDistinctTrailHeads(altitude + 1, adjacentCoordinate))
                .sum();

        routeCountCache.put(key, count);
        return count;
    }

    public long score2() {
        long score = 0;
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                int altitude = getAltitude(x, y);
                if (altitude == 0) {
                    score += countDistinctTrailHeads(0, x, y);
                }
            }
        }
        return score;
    }
}
