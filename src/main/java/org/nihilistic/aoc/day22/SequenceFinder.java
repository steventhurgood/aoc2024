package org.nihilistic.aoc.day22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record SequenceFinder(List<Long> seeds) {
    private record Offsets(long a, long b, long c, long d) {};

    private static final Logger logger = LoggerFactory.getLogger(SequenceFinder.class);
    public static SequenceFinder fromString(String input) {
        var seeds = input.lines().mapToLong(Long::valueOf).boxed().toList();
        return new SequenceFinder(seeds);
    }

    public void logDiffs(int limit) {
        List<List<Long>> sequences = seeds.stream().map(seed -> new Sequence(seed).stream().limit(limit+1).toList()).toList();
        for (var sequence : sequences) {
            for (var i=0; i < sequence.size(); i++) {
                var price = sequence.get(i) % 10;
                if (i == 0) {
                    logger.info("[%2d] %10s: %d".formatted(i, sequence.get(i), price));
                } else {
                    var previousPrice = sequence.get(i-1) % 10;
                    logger.info("[%2d] %10s: %d (%d)".formatted(i, sequence.get(i), price, price-previousPrice));
                }
            }
        }
    }

    public long findOptimalSequence(int limit) {
        List<List<Long>> sequences = seeds.stream().map(seed -> new Sequence(seed).stream().limit(limit+1).toList()).toList();
        var offsetPrices = new HashMap<Offsets, List<Long>>();

        for (var sequence : sequences) {
            var seenOffsets = new HashSet<Offsets>();
            for (var i=4; i < sequence.size(); i++) {
                var startOffset = i-4;
                var prices = IntStream.range(0, 5).mapToObj(n -> (sequence.get(startOffset + n)) % 10).toList();
                var offsets = new Offsets(
                    prices.get(1) - prices.get(0),
                    prices.get(2) - prices.get(1),
                    prices.get(3) - prices.get(2),
                    prices.get(4) - prices.get(3)
                );
                // only count the first instance of these offsets
                if (seenOffsets.contains(offsets)) {
                    continue;
                }
                seenOffsets.add(offsets);
                if (offsetPrices.containsKey(offsets)) {
                    offsetPrices.get(offsets).add(prices.get(4));
                } else {
                    offsetPrices.put(offsets, new ArrayList<Long>(List.of(prices.get(4))));
                }
            }
        }
        var totalPrices = new HashMap<Offsets, Long>();
        for (var offset : offsetPrices.keySet()) {
            var total = offsetPrices.get(offset).stream().mapToLong(Long::valueOf).sum();
            totalPrices.put(offset, total);
        }
        var maxKey = totalPrices.keySet().stream().max(Comparator.comparing(totalPrices::get)).get();
        return totalPrices.get(maxKey);
    }
}
