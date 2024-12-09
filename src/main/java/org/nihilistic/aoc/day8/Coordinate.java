package org.nihilistic.aoc.day8;

import java.util.Collection;
import java.util.HashSet;

public record Coordinate(int x, int y) {

    /*
     * existingNode is a
     * newNode is A
     * ..........
     * ......#...
     * ..........
     * .....a....
     * ..........
     * ....A.....
     * ..........
     * ..#.......
     * ..........
     * ..........
     */
    public Collection<Coordinate> getAntinodes(Coordinate existingNode) {
        var newNodeToExistingNode = new Coordinate(existingNode.x - x, existingNode.y - y);

        var antiNodes = new HashSet<Coordinate>();
        antiNodes.add(new Coordinate(x - newNodeToExistingNode.x, y - newNodeToExistingNode.y));
        antiNodes.add(
                new Coordinate(existingNode.x + newNodeToExistingNode.x, existingNode.y + newNodeToExistingNode.y));
        return antiNodes;
    }

    public Collection<Coordinate> getAntinodesWithinRange(Coordinate existingNode, int width, int height) {
        var newNodeToExistingNode = new Coordinate(existingNode.x - x, existingNode.y - y);

        var antiNodes = new HashSet<Coordinate>();
        int i = 1;
        Coordinate newNode = new Coordinate(x - newNodeToExistingNode.x, y - newNodeToExistingNode.y);
        while (newNode.inBounds(width, height)) {
            antiNodes.add(newNode);
            i++;
            newNode = new Coordinate(x - newNodeToExistingNode.x * i, y - newNodeToExistingNode.y * i);
        }
        ;

        i = 1;
        newNode = new Coordinate(existingNode.x + newNodeToExistingNode.x, existingNode.y + newNodeToExistingNode.y);
        while (newNode.inBounds(width, height)) {
            antiNodes.add(newNode);
            i++;
            newNode = new Coordinate(existingNode.x + newNodeToExistingNode.x * i,
                    existingNode.y + newNodeToExistingNode.y * i);
        }
        return antiNodes;
    }

    public boolean inBounds(int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}