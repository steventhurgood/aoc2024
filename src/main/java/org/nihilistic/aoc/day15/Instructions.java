package org.nihilistic.aoc.day15;

import java.util.List;
import java.util.stream.Collectors;

public record Instructions(List<Instruction> instructions) {

    public static Instructions fromString(String input) {
        var instructions = input.chars().filter(c -> !Character.isWhitespace(c)).mapToObj(Instruction::fromChar)
                .collect(Collectors.toList());
        return new Instructions(instructions);
    }

}
