package org.nihilistic.aoc.day3;

import java.util.regex.Pattern;

public record Multiplier(Integer left, Integer right) {

    private static Pattern pattern = Pattern.compile("mul\\(([0-9]+),([0-9]+)\\)");
    /*
     * format: mul(a, b)
     */ 
    public static Multiplier fromString(String input)  {
        var match = pattern.matcher(input);
        match.find();
        var left = Integer.valueOf(match.group(0));
        var right = Integer.valueOf(match.group(1));
        return new Multiplier(left, right);
   }
}
