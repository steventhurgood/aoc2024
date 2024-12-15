package org.nihilistic.aoc.day15;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public record WarehouseMap(Set<Coordinate> walls, Set<Coordinate> barrels, RobotAgent agent) {

    public static WarehouseMap fromString(String input) {
        int x = 0;
        int y = 0;
        var walls = new HashSet<Coordinate>();
        var barrels = new HashSet<Coordinate>();
        Coordinate agent = null;

        for (var i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '\n':
                    x = 0;
                    y++;
                    break;
                case '#':
                    walls.add(new Coordinate(x, y));
                    x++;
                    break;
                case 'O':
                    barrels.add(new Coordinate(x, y));
                    x++;
                    break;
                case '@':
                    agent = new Coordinate(x, y);
                    x++;
                    break;
                case '.':
                    x++;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid character: " + input.charAt(i) + "[" + i + "]");
            }
        }

        return new WarehouseMap(walls, barrels, new RobotAgent(agent));
    }

    public void apply(Instruction instruction) {
        var newAgentPosition = agent.considerMove(instruction);
        boolean shouldMove = true;

        if (walls.contains(newAgentPosition)) {
            // do nothing
            shouldMove = false;
        }

        if (barrels.contains(newAgentPosition)) {
            // complicated stuff
            Set<Coordinate> barrelsToMove = Sets.newHashSet(newAgentPosition);
            var newBarrelPosition = instruction.move(newAgentPosition);
            while (true) {
                // #.@OO.# -> #..@OO#
                // #..@OO# -> #..@OO#

                if (walls.contains(newBarrelPosition)) {
                    // nothing moves
                    shouldMove = false;
                    break;
                }
                if (barrels.contains(newBarrelPosition)) {
                    barrelsToMove.add(newBarrelPosition);
                    newBarrelPosition = instruction.move(newBarrelPosition);
                    continue;
                }
                // we can move all of the barrels, although in practice we only
                // need to move the first one to the last position
                barrels.remove(newAgentPosition);
                barrels.add(newBarrelPosition);
                break;
            }
        }

        if (shouldMove) {
            agent.move(instruction);
        }
    }

    @Override
    public String toString() {
        var out = new StringBuilder();

        var maxX = walls.stream().mapToInt(Coordinate::x).max().getAsInt() + 1;
        var maxY = walls.stream().mapToInt(Coordinate::y).max().getAsInt() + 1;

        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var coordinate = new Coordinate(x, y);
                if (walls.contains(coordinate)) {
                    out.append('#');
                    continue;
                }
                if (barrels.contains(coordinate)) {
                    out.append('O');
                    continue;
                }
                if (agent.isAt(coordinate)) {
                    out.append('@');
                    continue;
                }
                out.append('.');
            }
            out.append('\n');
        }
        return out.toString();
    }

    public int gps() {
        return barrels.stream().mapToInt(barrel -> 100 * barrel.y() + barrel.x()).sum();
    }

    public BigWarehouseMap embiggen() {
        var bigWalls = walls.stream().flatMap(Coordinate::widen).collect(Collectors.toSet());
        List<BigBox> bigBoxes = barrels.stream().map(BigBox::fromCoordinate).collect(Collectors.toList());
        var bigAgent = agent.embiggen();

        var boxMap = new HashMap<Coordinate, BigBox>();

        for (var bigBox : bigBoxes) {
            boxMap.put(bigBox.leftSide(), bigBox);
            boxMap.put(bigBox.rightSide(), bigBox);
        }
        return new BigWarehouseMap(bigWalls, boxMap, bigAgent);
    }
}
