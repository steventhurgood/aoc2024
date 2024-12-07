package org.nihilistic.aoc.day7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Optional;

public class EquationValidator {
    public static long SumValidEquations(String input) throws IOException {
        var lines = new BufferedReader(new StringReader(input));
        var sum = lines.lines().map(EquationValidator::validFilter).filter(Optional::isPresent).mapToLong(Optional::get).sum();
        return sum;
    }

    public static boolean isValid(String line, VariantBuilder variantBuilder) {
        var parts = line.split(": ");
        var target = Long.valueOf(parts[0]);
        var numbers = Arrays.stream(parts[1].split(" ")).mapToLong(Long::valueOf).toArray();
        return NumberTrees.hasValidSolution(target, numbers, variantBuilder);
    }

    public static Optional<Long> validFilter(String line) {
        var parts = line.split(": ");
        var target = Long.valueOf(parts[0]);
        var numbers = Arrays.stream(parts[1].split(" ")).mapToLong(Long::valueOf).toArray();
        VariantBuilder variantBuilder = new PlusTimesVariantBuilder();
        if (NumberTrees.hasValidSolution(target, numbers, variantBuilder)) {
            return Optional.of(target);
        } 
        return Optional.empty();
    }

    public static long sumValidEquationsWithConcat(String input) {
        var lines = new BufferedReader(new StringReader(input));
        var sum = lines.lines().map(EquationValidator::validWithConcatFilter).filter(Optional::isPresent).mapToLong(Optional::get).sum();
        return sum;
    }
    
    public static Optional<Long> validWithConcatFilter(String line) {
        var parts = line.split(": ");
        var target = Long.valueOf(parts[0]);
        var numbers = Arrays.stream(parts[1].split(" ")).mapToLong(Long::valueOf).toArray();
        VariantBuilder variantBuilder = new PlusTimesConcatVariantBuilder();
        if (NumberTrees.hasValidSolution(target, numbers, variantBuilder)) {
            return Optional.of(target);
        } 
        return Optional.empty();
    }


}
