package org.nihilistic.aoc.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Garden (Map<Coordinate, Integer> areas){
    private static Logger logger = LoggerFactory.getLogger(Garden.class);

    public static Garden fromString(String input) {
        var areas = new HashMap<Coordinate, Integer>();  
        var x=0;
        var y=0;
        for (var i=0; i < input.length(); i++) {
            Integer region = Integer.valueOf(input.charAt(i));
            if (input.charAt(i) == '\n') {
                y++;
                x=0;
                continue;
            }
            areas.put(new Coordinate(x, y), region);
            x++;
        }
        var minX = areas.keySet().stream().mapToInt(Coordinate::x).min();
        var maxX = areas.keySet().stream().mapToInt(Coordinate::x).max();
        var minY = areas.keySet().stream().mapToInt(Coordinate::y).min();
        var maxY = areas.keySet().stream().mapToInt(Coordinate::y).max();

        logger.info("Read in {} points ({}, {}) - ({}, {})", areas.size(), minX, minY, maxX, maxY);
        return new Garden(areas);
    }
    public long fencingCost() {
        var seen = new HashSet<Coordinate>();
        long cost = 0;

        for (var coordinate: areas.keySet()) {
            if (seen.contains(coordinate)) {
                continue;
            }

            // flood fill the area.
            var toProcess = new ArrayList<Coordinate>();
            var region = get(coordinate);
            toProcess.add(coordinate);
            seen.add(coordinate);

            var fenceLength = 0;
            var areaSize = 1;
            while (toProcess.size() > 0) {
                var current = toProcess.removeFirst();
                for (var adjacent: current.adjacent()) {
                    if (get(adjacent) == region) {
                        if (!seen.contains(adjacent)) {
                        toProcess.add(adjacent);
                        seen.add(adjacent);
                        areaSize++;
                        }
                    } else {
                        fenceLength ++;
                    }
                }
            }
            cost += areaSize * fenceLength;

            // we have found a new area.
        }
        return cost;
    }

    Integer get(Coordinate coordinate) {
        logger.info("Coordinate: {}, value: {}", coordinate, areas.get(coordinate));
        return areas.getOrDefault(coordinate, -1);
    }
}
