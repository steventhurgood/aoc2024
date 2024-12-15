package org.nihilistic.aoc.day13;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Coordinate(long x, long y) {

    private static Logger logger = LoggerFactory.getLogger(Coordinate.class);

    public double distanceTo(Coordinate prize) {
        return Math.sqrt(Math.pow(x - prize.x(), 2) + Math.pow(y - prize.y(), 2));
    }

    public Coordinate move(Coordinate velocity) {
        return new Coordinate(x + velocity.x(), y + velocity.y());
    }

    public Coordinate move(Coordinate velocity, Long count) {
        return new Coordinate(x + velocity.x() * count, y + velocity.y() * count);
    }

    public Stream<ButtonPresses> neighbours(Coordinate velocity, Coordinate prize) {
        // we can press the button any numbers of times. Only multiples of 2 or 4 count
        // and we can't press it so many times that we go past the prize.

        var xPresses = (prize.x() - x) / velocity.x();
        var yPresses = (prize.y() - y) / velocity.y();
        var presses = Math.min(xPresses, yPresses);

        // we can only press the button an even number of times.
        return LongStream.range(0, presses).mapToObj(p -> new ButtonPresses(presses - p, move(velocity, presses - p))).peek(p -> logger.info("created press: " + p));
    }

}
