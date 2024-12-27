package org.nihilistic.aoc.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nihilistic.aoc.grid.Coordinate;
import org.nihilistic.aoc.grid.FindRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Garden(Map<Coordinate, Character> areas) {
    private static Logger logger = LoggerFactory.getLogger(Garden.class);

    public static Garden fromString(String input) {
        var areas = new HashMap<Coordinate, Character>();
        var x = 0;
        var y = 0;
        for (var i = 0; i < input.length(); i++) {
            Character region = input.charAt(i);
            if (input.charAt(i) == '\n') {
                y++;
                x = 0;
                continue;
            }
            areas.put(new Coordinate(x, y), region);
            x++;
        }
        return new Garden(areas);
    }

    public int countFences(Set<Coordinate> area) {
        var findRange = new FindRange();
        var range = area.stream().collect(findRange);

        // scan for vertical fences:
        var fences=0;
        for (var x=range.minX; x <= range.maxX; x++) {
            boolean leftFence=false;
            boolean rightFence=false;
            for (var y=range.minY; y <= range.maxY; y++) {
                var coordinate = new Coordinate(x, y);
                var left = new Coordinate(x-1, y);
                var right = new Coordinate(x+1, y);
                var thisLeftFence = (area.contains(coordinate) && !area.contains(left));
                var thisRightFence = (area.contains(coordinate) && !area.contains(right));
                if (thisLeftFence && !leftFence) {
                    // we have found a new left fence
                    leftFence=true;
                }
                if (thisRightFence && !rightFence) {
                    // we have found a new left fence
                    rightFence=true;
                }
                if (!thisLeftFence && leftFence) {
                    // this fence has ended
                    leftFence=false;
                    fences++;
                }
                if (!thisRightFence && rightFence) {
                    // this fence has ended
                    rightFence=false;
                    fences++;
                }
            }
            // we reached the max Y coordinate, and were still in a fence
            if (leftFence) { fences++; }
            if (rightFence) { fences++; }
        }

        // scan for horizontal fences:
        for (var y=range.minY; y <= range.maxY; y++) {
            boolean topFence=false;
            boolean bottomFence=false;
            for (var x=range.minX; x <= range.maxX; x++) {
                var coordinate = new Coordinate(x, y);
                var top = new Coordinate(x, y-1);
                var bottom = new Coordinate(x, y+1);
                var thisTopFence = (area.contains(coordinate) && !area.contains(top));
                var thisBottomFence = (area.contains(coordinate) && !area.contains(bottom));
                if (thisTopFence && !topFence) {
                    // we have found a new top fence
                    topFence=true;
                }
                if (thisBottomFence && !bottomFence) {
                    // we have found a new bottom fence
                    bottomFence=true;
                }
                if (!thisTopFence && topFence) {
                    // this fence has ended
                    topFence=false;
                    fences++;
                }
                if (!thisBottomFence && bottomFence) {
                    // this fence has ended
                    bottomFence=false;
                    fences++;
                }
            }
            // we reached the max Y coordinate, and were still in a fence
            if (topFence) { fences++; }
            if (bottomFence) { fences++; }
        }
        return fences;
    }

    public long fencingCost() {
        var areaMap = findAreas();
        long cost = 0;
        for (var region : areaMap.keySet()) {
            for (var area : areaMap.get(region)) {
                long size = 0;
                long perimeter = 0;
                for (var coordinate : area) {
                    size++;
                    for (var adjacent : coordinate.adjacent()) {
                        if (!area.contains(adjacent)) {
                            perimeter++;
                        }
                    }
                }
                cost += (size * perimeter);
            }
        }
        return cost;
    }

    public Map<Character, List<Set<Coordinate>>> findAreas() {
        var areaMap = new HashMap<Character, List<Set<Coordinate>>>();
        var seen = new HashSet<Coordinate>();

        for (var coordinate : areas.keySet()) {
            if (seen.contains(coordinate)) {
                continue;
            }

            // this coordinate contains an area that we have not yet seen.
            var toProcess = new ArrayList<Coordinate>();
            var region = get(coordinate);
            areaMap.computeIfAbsent(region, unused -> new ArrayList<Set<Coordinate>>());

            toProcess.add(coordinate);
            var thisArea = new HashSet<Coordinate>();

            while (toProcess.size() > 0) {
                var current = toProcess.removeFirst();
                thisArea.add(current);
                for (var adjacent : current.adjacent()) {
                    if (get(adjacent).equals(region) && !seen.contains(adjacent)) {
                        toProcess.add(adjacent);
                        seen.add(adjacent);
                    }
                }
            }
            areaMap.get(region).add(thisArea);
        }
        return areaMap;
    }

    public long fencingCost2() {
        var areaMap = findAreas();
        long cost = 0;
        for (var region : areaMap.keySet()) {
            for (var area : areaMap.get(region)) {
                var fences = countFences(area);
                cost += area.size() * fences;
            }
        }
        return cost;
    }

    Character get(Coordinate coordinate) {
        logger.info("Coordinate: {}, value: {}", coordinate, areas.get(coordinate));
        return areas.getOrDefault(coordinate, Character.valueOf((char) 0));
    }
}
