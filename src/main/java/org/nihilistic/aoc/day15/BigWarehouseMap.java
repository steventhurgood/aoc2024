package org.nihilistic.aoc.day15;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public record BigWarehouseMap(Set<Coordinate> walls, Map<Coordinate, BigBox> boxes, RobotAgent agent) {

    private static Logger logger = LoggerFactory.getLogger(BigWarehouseMap.class);

    public void apply(Instruction instruction) {
        var newAgentPosition = agent.considerMove(instruction);

        if (walls.contains(newAgentPosition)) {
            // do nothing
            return;
        }

        boolean move = true;

        var boxesToMove = new HashSet<BigBox>();
        if (boxes.containsKey(newAgentPosition)) {
            boxesToMove.add(boxes.get(newAgentPosition));

            var boxesToConsiderMoving = Lists.newArrayList(boxes.get(newAgentPosition));

            while (boxesToConsiderMoving.size() > 0) {
                var current = boxesToConsiderMoving.removeFirst();
                boxesToMove.add(current);
                for (var newBoxPosition : current.coordinatesAfterInstruction(instruction)) {
                    if (walls.contains(newBoxPosition)) {
                        move = false;
                        break;
                    }
                    if (boxes.containsKey(newBoxPosition)) {
                        var box = boxes.get(newBoxPosition);
                        if (box != current) {
                            boxesToConsiderMoving.add(box);
                        }
                    }
                }
            }
        }

        if (move) {
            agent.move(instruction);

            for (var boxToMove : boxesToMove) {
                boxes.remove(boxToMove.leftSide());
                boxes.remove(boxToMove.rightSide());
            }
            for (var boxToMove : boxesToMove) {
                var movedBox = boxToMove.apply(instruction);
                boxes.put(movedBox.leftSide(), movedBox);
                boxes.put(movedBox.rightSide(), movedBox);
            }
        }
    }

    @Override
    public String toString() {
        int maxX = walls.stream().mapToInt(Coordinate::x).max().getAsInt() + 1;
        int maxY = walls.stream().mapToInt(Coordinate::y).max().getAsInt() + 1;

        Set<Coordinate> leftSides = boxes.values().stream().map(BigBox::leftSide).collect(Collectors.toSet());
        Set<Coordinate> rightSides = boxes.values().stream().map(BigBox::rightSide).collect(Collectors.toSet());

        var out = new StringBuilder();

        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var coordinate = new Coordinate(x, y);
                if (walls.contains(coordinate)) {
                    out.append('#');
                    continue;
                }

                if (leftSides.contains(coordinate)) {
                    out.append('[');
                    continue;
                }

                if (rightSides.contains(coordinate)) {
                    out.append(']');
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
        var gps = 0;
        var uniqueBoxes = Sets.newHashSet(boxes.values());
        for (var box : uniqueBoxes) {
            var xDistance = box.leftSide().x();
            var yDistance = box.leftSide().y();

            gps += (100*yDistance + xDistance);
            logger.info("box: " + box + " [" + xDistance + "," + yDistance + "] (" + (100*yDistance + xDistance) + ")");
            logger.info("gps: " + gps);
        }

        return gps;
    }
}
