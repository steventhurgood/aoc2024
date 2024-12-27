package org.nihilistic.aoc.day13;

import java.util.Optional;
import java.util.regex.Pattern;

import org.nihilistic.aoc.grid.Coordinate;

public record ClawMachine(Coordinate buttonA, Coordinate buttonB, Coordinate prize) {
    private static long aCost = 3L;
    private static long bCost = 1L;
    private static Pattern pattern = Pattern.compile(
            "Button A: X\\+(?<ax>[0-9]+), Y\\+(?<ay>[0-9]+)\n"
                    + "Button B: X\\+(?<bx>[0-9]+), Y\\+(?<by>[0-9]+)\n"
                    + "Prize: X=(?<prizeX>[0-9]+), Y=(?<prizeY>[0-9]+)");
    /*
     * eg.,
     * Button A: X+94, Y+34
     * Button B: X+22, Y+67
     * Prize: X=8400, Y=5400
     */
    public static ClawMachine fromString(String input) {
        var matcher = pattern.matcher(input);
        if (matcher.find()) {
            var buttonA = new Coordinate(
                    Long.valueOf(matcher.group("ax")),
                    Long.valueOf(matcher.group("ay")));
            var buttonB = new Coordinate(
                    Long.valueOf(matcher.group("bx")),
                    Long.valueOf(matcher.group("by")));
            var prize = new Coordinate(
                    Long.valueOf(matcher.group("prizeX")),
                    Long.valueOf(matcher.group("prizeY")));
            return new ClawMachine(buttonA, buttonB, prize);
        }
        throw new IllegalArgumentException("Invalid claw machine: " + input);
    }

    public ClawMachine embiggen() {
        var newPrize = new Coordinate(prize.x() + 10000000000000L, prize.y() + 10000000000000L);
        return new ClawMachine(buttonA, buttonB, newPrize);
    }

    public Optional<Long> solve() {
        /*
         * AX = buttonA.x()
         * AY = buttonA.y()
         * 
         * BX = buttonB.x()
         * BY = buttonB.y()
         * 
         * PY = prize.y()
         * PX = prize.x()
         * 
         * a * AX + b * BX = PX
         * a * AY + b * BY = PY
         * 
         * a = (PX-bBX)/AX
         * AY(PX-bBX)/AX + bBY = PY
         * AY(PX-bBX) + bBY.AX = PY.AX
         * AY.PX - b.AY.BX + bBY.AX = PY.AX
         * AY.PX + b(BY.AX - AY.BX) = PY.AX
         * b(BY.AX - AY.BX) = PY.AX - AY.PX
         * 
         * b = (PY.AX - AY.PX)/(BY.AX - AY.BX)
         * a = (PX-bBX)/AX
         */
        long ax = buttonA.x();
        long ay = buttonA.y();
        long bx = buttonB.x();
        long by = buttonB.y();
        long px = prize.x();
        long py = prize.y();

        long numerator = (py * ax - ay * px);
        long denominator = (by * ax - ay * bx);
        if (numerator % denominator != 0) {
            return Optional.empty();
        }
        long b = numerator / denominator;
        if ((px - b * bx) % ax  != 0) {
            return Optional.empty();
        }
        long a = (px - b * bx) / ax;
        long cost = aCost * a + bCost * b;
        return Optional.of(cost);
    }
}