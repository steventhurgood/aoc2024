package org.nihilistic.aoc.day5;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Stream;

public record PageSorter(PageComparator cmp) {
    public Integer[] sortLine(String line) {
        Integer parts[] = Arrays.stream(line.split(",")).mapToInt(Integer::valueOf).boxed().toArray(Integer[]::new);
        Arrays.sort(parts, cmp);
        return parts;
    };

    public static Integer middleChar(String line) {
        Integer parts[] = Arrays.stream(line.split(",")).mapToInt(Integer::valueOf).boxed().toArray(Integer[]::new);
        return middleChar(parts);
    }

    public static Integer middleChar(Integer parts[]) {
        return parts[parts.length / 2];
    }


    public Stream<String> filterLines(String input) {
        var lines = new BufferedReader(new StringReader(input));
        return lines.lines().filter(line -> cmp.isSorted(line));
    }

    public Stream<String> filterUnsortedLines(String input) {
        var lines = new BufferedReader(new StringReader(input));
        return lines.lines().filter(line -> !cmp.isSorted(line));
    }

    public int sumOfSortedlines(String input) {
        var lines = new BufferedReader(new StringReader(input));
        return lines.lines().map(PageSorter::toIntArray).filter(parts -> cmp.isSorted(parts)).mapToInt(PageSorter::middleValue).sum();
    }

    public int sumOfOfUnsortedLines(String input) {
        var lines = new BufferedReader(new StringReader(input));
        return lines.lines().map(PageSorter::toIntArray).filter(parts -> !cmp.isSorted(parts)).map(parts -> sortLine(parts)).mapToInt(PageSorter::middleValue).sum();
    }

    public static Integer[] toIntArray(String input) {
        return Arrays.stream(input.split(",")).mapToInt(Integer::valueOf).boxed().toArray(Integer[]::new);
    }

    public static Integer middleValue(Integer[] parts) {
        return parts[parts.length/2];
    }

    public Integer[] sortLine(Integer[] parts) {
        Arrays.sort(parts, cmp);
        return parts;
    };

}
