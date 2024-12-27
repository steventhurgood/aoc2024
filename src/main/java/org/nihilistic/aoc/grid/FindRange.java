package org.nihilistic.aoc.grid;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FindRange implements Collector<Coordinate, FindRange, FindRange> {
    public Integer minX = Integer.MAX_VALUE;
    public Integer minY = Integer.MAX_VALUE;
    public Integer maxX = Integer.MIN_VALUE;
    public Integer maxY = Integer.MIN_VALUE;

    public void add(Coordinate coordinate) {
        minX = Math.min(minX, coordinate.x());
        minY = Math.min(minY, coordinate.y());

        maxX = Math.max(maxX, coordinate.x());
        maxY = Math.max(maxY, coordinate.y());
    }

    public static FindRange combine(FindRange left, FindRange right) {
        left.minX = Math.min(left.minX, right.minX);
        left.minY = Math.min(left.minY, right.minY);

        left.maxX = Math.max(left.maxX, right.maxX);
        left.maxY = Math.max(left.maxY, right.maxY);
        return left;
    }

    @Override
    public BiConsumer<FindRange, Coordinate> accumulator() {
        return FindRange::add;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }

    @Override
    public BinaryOperator<FindRange> combiner() {
        return FindRange::combine;
    }

    @Override
    public Function<FindRange, FindRange> finisher() {
        return Function.identity();
    }

    @Override
    public Supplier<FindRange> supplier() {
        return FindRange::new;
    }

    
}
