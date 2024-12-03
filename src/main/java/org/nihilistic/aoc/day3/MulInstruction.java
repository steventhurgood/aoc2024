package org.nihilistic.aoc.day3;

import java.util.regex.Pattern;

public record MulInstruction(int a, int b) implements Instruction {

    private static Pattern mulPattern = Pattern.compile("mul\\(([0-9]+),([0-9]+)\\)");

    @Override
    public void apply(Machine machine) {
        machine.doMul(a, b);
    }

    public static MulInstruction fromString(String input) {
        var matcher = mulPattern.matcher(input);
        if (!matcher.find()) {
            throw new IllegalArgumentException();
        }
        var a = Integer.valueOf(matcher.group(1));
        var b = Integer.valueOf(matcher.group(2));
        return new MulInstruction(a, b);
    }
}
