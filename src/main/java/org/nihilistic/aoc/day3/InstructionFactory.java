package org.nihilistic.aoc.day3;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InstructionFactory {
    static Pattern mulPattern = Pattern.compile("mul\\([0-9]+,[0-9]+\\)");
    static Pattern fullPattern = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\([0-9]+,[0-9]+\\)");

    public static List<Instruction> mulFromString(String input) {
        return fromPattern(mulPattern, input);
    }

    public static List<Instruction> fullFromString(String input) {
        return fromPattern(fullPattern, input);
    }

    private static List<Instruction> fromPattern(Pattern pattern, String input) {
            var matcher = pattern.matcher(input);
            var instructions = Stream.generate(() -> {
                if (matcher.find()) {
                    return matcher.group();
                }
                return null;
            }).takeWhile(Objects::nonNull).map(InstructionFactory::fromString).collect(Collectors.toList());
            return instructions;

    }

    public static Instruction fromString(String input) {
        switch(input) {
            case "do()":
              return new StartExecutingInstruction();
            case "don't()":
              return new StopExecutingInstruction();
            default:
                return MulInstruction.fromString(input);
        }
    }
}
