package org.nihilistic.aoc.day5;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

public record PageComparator(Map<Integer, Set<Integer>> order) implements Comparator<Integer>{
    /*
     * input is a series of lines, with pairs separated by a "|"
     */
    public static PageComparator fromString(String input) {
        var lines = new BufferedReader(new StringReader(input));

        var order = lines.lines().map(l -> l.split("\\|")).collect(
            Collectors.toMap(PageComparator::pageComparatorKey, PageComparator::pageComparatorValue, PageComparator::pageComparatorMerge)
        );
        return new PageComparator(order);
    }    

    private static Integer pageComparatorKey(String[] parts) {
        return Integer.valueOf(parts[0]);
    }

    private static Set<Integer> pageComparatorValue(String[] parts) {
        return Sets.newHashSet(Integer.valueOf(parts[1]));
    }

    private static Set<Integer> pageComparatorMerge(Set<Integer> existing, Set<Integer> replacement) {
        existing.addAll(replacement);
        return existing;
    }

    @Override
    public int compare(Integer a, Integer b) {

        if (order.containsKey(a) && order.get(a).contains(b)) {
            return -1;
        }

        if (order.containsKey(b) && order.get(b).contains(a)) {
            return 1;
        }
        return 0;
    }
    public boolean isSorted(Integer[] parts) {
        for (var i=0; i < parts.length-1; i++) {
            int a = parts[i];
            int b = parts[i+1];
            if (compare(a, b) > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isSorted(String line) {
        var parts = Arrays.stream(line.split(",")).mapToInt(Integer::valueOf).toArray();
        for (var i=0; i < parts.length - 1; i++) {
            Integer a = parts[i];
            Integer b = parts[i+1];
            if (compare(a, b) > 0) {
                return false;
            }
        }
        return true;
    }
}