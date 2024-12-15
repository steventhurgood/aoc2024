package org.nihilistic.aoc.day13;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record ClawMachine(Coordinate buttonA, Coordinate buttonB, Coordinate prize) {
    private static Logger logger = LoggerFactory.getLogger(ClawMachine.class);
    private static Integer aCost = 3;
    private static Integer bCost = 1;
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
                Integer.valueOf(matcher.group("ax")),
                Integer.valueOf(matcher.group("ay")));
            var buttonB = new Coordinate(
                Integer.valueOf(matcher.group("bx")),
                Integer.valueOf(matcher.group("by")));
            var prize = new Coordinate(
                Integer.valueOf(matcher.group("prizeX")),
                Integer.valueOf(matcher.group("prizeY")));
            return new ClawMachine(buttonA, buttonB, prize);
        }
        throw new IllegalArgumentException("Invalid claw machine: " + input);
    }

    private Long heuristic(Coordinate coordinate) {
        // estimated cost in tokens to reach prize
        return Long.valueOf(prize.x() - coordinate.x()) + (prize.y() - coordinate.y());
    }

    private static int cmp(Coordinate a, Coordinate b, Map<Coordinate, Long> fScore) {
        var aScore = fScore.getOrDefault(a, Long.MAX_VALUE);
        var bScore = fScore.getOrDefault(b, Long.MAX_VALUE);

        if (aScore < bScore) {
            return -1;
        }
        if (aScore > bScore) {
            return 1;
        }
        return 0;
    }

    public Long findPath() {
        var start = new Coordinate(0, 0);
        var cameFrom = new HashMap<Coordinate, Coordinate>();
        var gScore = new HashMap<Coordinate, Long>();
        gScore.put(start, 0L);
        var fScore = new HashMap<Coordinate, Long>();
        PriorityQueue<Coordinate> openSet = new PriorityQueue<Coordinate>((a, b) -> cmp(a, b, fScore));
        openSet.add(start);

        fScore.put(start, heuristic(start));

        while (!openSet.isEmpty()) {
            Coordinate current = openSet.poll();

            if (current.equals(prize)) {
                return gScore.get(current);
            }

            var moveA = current.move(buttonA);

            if(moveA.x() <= prize.x() && moveA.y() <= prize.y()) {
                var currentScore = gScore.getOrDefault(current, Long.MAX_VALUE);
                var aScore = gScore.getOrDefault(moveA, Long.MAX_VALUE);

                var tentativeScore = currentScore + aCost;
                if (tentativeScore < aScore) {
                    // we have found a better path to moveA
                    // cameFrom.put(moveA, current);
                    gScore.put(moveA, tentativeScore);
                    fScore.put(moveA, tentativeScore + heuristic(moveA));
                    if (!openSet.contains(moveA)) {
                        openSet.add(moveA);
                    }
                }
            }

            var moveB = current.move(buttonB);
            if(moveB.x() <= prize.x() && moveB.y() <= prize.y()) {
                var currentScore = gScore.getOrDefault(current, Long.MAX_VALUE);
                var bScore = gScore.getOrDefault(moveB, Long.MAX_VALUE);

                var tentativeScore = currentScore + bCost;
                if (tentativeScore < bScore) {
                    // we have found a better path to moveB
                    cameFrom.put(moveB, current);
                    gScore.put(moveB, tentativeScore);
                    fScore.put(moveB, tentativeScore + heuristic(moveB));
                    if (!openSet.contains(moveB)) {
                        openSet.add(moveB);
                    }
                }
            }
        }
        logger.warn("No way to win: " + this);
        return 0L;
    }

    public Long findLongPath() {
        var start = new Coordinate(0, 0);
        var gScore = new HashMap<Coordinate, Long>();
        gScore.put(start, 0L);
        var fScore = new HashMap<Coordinate, Long>();
        PriorityQueue<Coordinate> openSet = new PriorityQueue<Coordinate>((a, b) -> cmp(a, b, fScore));
        openSet.add(start);
        // var scaledPrize = prize.move(new Coordinate(10000000000000L, 10000000000000L));
        var scaledPrize = prize;

        fScore.put(start, heuristic(start));

        while (!openSet.isEmpty()) {
            Coordinate current = openSet.poll();

            if (current.equals(scaledPrize)) {
                return gScore.get(current);
            }

            // a neighbours
            var currentScore = gScore.getOrDefault(current, Long.MAX_VALUE);

            current.neighbours(buttonA, scaledPrize).forEach(press -> {
                var neighbour = press.coordinate();
                var aScore = gScore.getOrDefault(neighbour, Long.MAX_VALUE);
                var tentativeScore = currentScore + press.pressCount() * aCost;
                if (tentativeScore < aScore) {
                    // we have found a better path to moveA
                    // cameFrom.put(moveA, current);
                    gScore.put(neighbour, tentativeScore);
                    fScore.put(neighbour, tentativeScore + heuristic(neighbour));
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            });

            current.neighbours(buttonB, scaledPrize).forEach(press -> {
                var neighbour = press.coordinate();
                var bScore = gScore.getOrDefault(neighbour, Long.MAX_VALUE);
                var tentativeScore = currentScore + press.pressCount() * aCost;
                if (tentativeScore < bScore) {
                    gScore.put(neighbour, tentativeScore);
                    fScore.put(neighbour, tentativeScore + heuristic(neighbour));
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            });
        }
        logger.warn("No way to win: " + this);
        return 0L;
    }
}
