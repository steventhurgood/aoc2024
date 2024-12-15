package org.nihilistic.aoc.day15;

import java.util.List;
import com.google.common.collect.Lists;

public record BigBox(Coordinate leftSide) {
    public static BigBox fromCoordinate(Coordinate coordinate) {
        return new BigBox(new Coordinate(coordinate.x() * 2, coordinate.y()));
    }

    public Coordinate rightSide() {
        return new Coordinate(leftSide.x() + 1, leftSide.y());
    }

    public BigBox apply(Instruction instruction) {
        return new BigBox(instruction.move(leftSide));
    }

    public List<Coordinate> coordinates() {
        return Lists.newArrayList(leftSide(), rightSide());
    }
    public List<Coordinate> coordinatesAfterInstruction(Instruction instruction) {
        return Lists.newArrayList(
            instruction.move(leftSide()),
            instruction.move(rightSide())
        );
    }

    public int horizontalDistanceToSide(int maxX) {
        var leftDistance = leftSide.x();
        var rightDistance = maxX - (leftDistance + 1);
        return Math.min(leftDistance, rightDistance);
    }

    public int verticalDistanceToSide(int maxY) {
        var topDistance = leftSide.y();
        var bottomDistance = maxY - topDistance;
        return Math.min(topDistance, bottomDistance);
    }

}
