package org.nihilistic.aoc.day8;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CharMatcher;

public class AntinodeCounter {
    private static Logger logger = LoggerFactory.getLogger(AntinodeCounter.class);
    public static long countUniqueAntinodes(String input) throws IOException{
        int x=0;
        int y=0;
        int width=0;
        int height=0;

        var antennae = new HashMap<String, Set<Coordinate>>();
        var antinodes = new HashSet<Coordinate>();
        for (var i=0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == '.') {
                x++;
                continue;
            }
            if (c == '\n') {
                if (width == 0) {
                    width = x;
                }
                x = 0;
                y++;
                continue;
            }
            var newNode = new Coordinate(x, y);
            String key = String.valueOf(c);
            if (!antennae.containsKey(key)) {
                antennae.put(key, new HashSet<Coordinate>());
            }
            var existingNodes = antennae.get(key);
            for (var existingNode : existingNodes) {
                antinodes.addAll(newNode.getAntinodes(existingNode));
            }
            antennae.get(key).add(newNode);
            x++;
        }
        height = y+1;
        var filter = new CoordinateFilter(width, height);
        return antinodes.stream().filter(c -> filter.filter(c)).count();
    }

    public static long countUniqueAntinodesInRange(String input) throws IOException{
        int x=0;
        int y=0;
        int width=input.indexOf("\n", 0);
        int height= CharMatcher.is('\n').countIn(input)+1;

        var antennae = new HashMap<String, Set<Coordinate>>();
        var antinodes = new HashSet<Coordinate>();
        for (var i=0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == '.') {
                x++;
                continue;
            }
            if (c == '\n') {
                x = 0;
                y++;
                continue;
            }
            var newNode = new Coordinate(x, y);
            antinodes.add(newNode);
            String key = String.valueOf(c);
            if (!antennae.containsKey(key)) {
                antennae.put(key, new HashSet<Coordinate>());
            }
            var existingNodes = antennae.get(key);
            for (var existingNode : existingNodes) {
                antinodes.addAll(newNode.getAntinodesWithinRange(existingNode, width, height));
            }
            antennae.get(key).add(newNode);
            x++;
        }
        logger.info(coordinatesToString(antinodes, width, height));
        return antinodes.size();
    }

    static String coordinatesToString(Collection<Coordinate> coordinates, int width, int height)  {
        var builder = new StringBuilder();
        builder.append("\n");
        for (int y=0; y<height; y++) {
            for (int x=0; x < width; x++) {
                var key = new Coordinate(x, y); 
                if (coordinates.contains(key)) {
                    builder.append("#");
                } else {
                    builder.append(".");
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
