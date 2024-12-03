package org.nihilistic.aoc.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Report(List<Integer> levels) {
    private static Logger logger = LoggerFactory.getLogger(Report.class);

    private static int minChange = 1;
    private static int maxChange = 3;

    public static Report fromString(String input) {
        var levels = Arrays.stream(input.split("\\s+")).map(Integer::valueOf).collect(Collectors.toList());
        return new Report(levels);
    }

    public Boolean safe() {
        logger.info("Testing {} for safety", this);
        Boolean increasing = null;
        for (var i = 0; i < levels.size() - 1; i++) {
            var current = levels.get(i);
            var next = levels.get(i + 1);

            var diff = Math.abs(next - current);
            if (diff < minChange || diff > maxChange) {
                return false;
            }

            if (increasing == null) {
                // we don't currently have a direction
                increasing = (next > current);
                continue;
            }

            if (increasing && current > next) {
                return false;
            }

            if (!increasing && current < next) {
                return false;
            }

        }
        return true;
    }

    public List<Report> variants() {
        List<Report> newVariants = IntStream.range(0, levels.size()).mapToObj(indexToRemove -> {
            List<Integer> newList = new ArrayList<Integer>(levels);
            newList.remove(indexToRemove);
            return newList;
        }).map(Report::new).collect(Collectors.toList());
        newVariants.add(this); // not removing any level is an option
        return newVariants;
    }

    public Boolean hasSafeVariant() {
        var anyMatches = variants().stream().anyMatch(Report::safe);
        return anyMatches;
    }
}
