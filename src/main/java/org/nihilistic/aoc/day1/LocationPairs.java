package org.nihilistic.aoc.day1;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.google.common.collect.Streams;

public record LocationPairs(List<Integer> left, List<Integer> right) {
    public LocationPairs() {
        this(new ArrayList<Integer>(), new ArrayList<Integer>());
    }

    public static LocationPairs fromString(String input) {
        var reader = new BufferedReader(new StringReader(input));

        return reader.lines().map(Pair::fromString).collect(toLocationPairs());
    }

    public int totalSortedDiffernces() {
        var sortedLeft = new ArrayList<Integer>(left);
        Collections.sort(sortedLeft);
        var sortedRight = new ArrayList<Integer>(right);
        Collections.sort(sortedRight);

        return Streams.zip(sortedLeft.stream(), sortedRight.stream(), (left, right) -> Math.abs(left - right))
                .collect(Collectors.summingInt((Integer::intValue)));
    }

    public Long similarity() {
        Map<Integer, Long> rightCount = right.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Long similarity = left.stream().map(leftValue -> leftValue * rightCount.getOrDefault(leftValue, 0L)).collect(Collectors.summingLong(Long::longValue));

        return similarity;
    }

    public void accumulate(Pair pair) {
        this.left.add(pair.left());
        this.right.add(pair.right());
    }

    public void combine(LocationPairs other) {
        this.left.addAll(other.left());
        this.right.addAll(other.right());
    }

    public static Collector<Pair, LocationPairs, LocationPairs> toLocationPairs() {
        return new Collector<Pair, LocationPairs, LocationPairs>() {

            @Override
            public BiConsumer<LocationPairs, Pair> accumulator() {
                return LocationPairs::accumulate;
            }

            @Override
            public BinaryOperator<LocationPairs> combiner() {
                return (pairs1, pairs2) -> {
                    pairs1.combine(pairs2);
                    return pairs1;
                };
            }

            @Override
            public Function<LocationPairs, LocationPairs> finisher() {
                return Function.identity();
            }

            @Override
            public Supplier<LocationPairs> supplier() {
                return LocationPairs::new;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Sets.newHashSet(Collector.Characteristics.IDENTITY_FINISH);
            }
        };
    }
}
