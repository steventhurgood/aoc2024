package org.nihilistic.aoc.day16;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record Reindeer(Coordinate position, Direction direction) {

    public List<ReindeerNeighbour> neighbours(Set<Coordinate> walls) {
        var neighbours = new ArrayList<ReindeerNeighbour>();

        var forward = direction.move(position);
        if (!walls.contains(forward)) {
            var newReindeer = new Reindeer(forward, direction);
            neighbours.add(new ReindeerNeighbour(newReindeer, 1));
        }
        var clockwiseReindeer = new Reindeer(position, direction.clockwise());
        var counterclockwiseReindeer = new Reindeer(position, direction.counterclockwise());
        neighbours.add(new ReindeerNeighbour(clockwiseReindeer, 1000));
        neighbours.add(new ReindeerNeighbour(counterclockwiseReindeer, 1000));
        return neighbours;
    }
}
