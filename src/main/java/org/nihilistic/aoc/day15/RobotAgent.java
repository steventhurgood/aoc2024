package org.nihilistic.aoc.day15;

import com.google.common.annotations.VisibleForTesting;

public class RobotAgent {
    @VisibleForTesting
    private Coordinate position;

    RobotAgent(Coordinate position) {
        this.position = position;
    }

    public Coordinate considerMove(Instruction instruction) {
        return instruction.move(position);
    }

    public void move(Instruction instruction) {
        this.position = instruction.move(position);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof RobotAgent) {
            var otherRobotAgent = (RobotAgent) obj;
            return otherRobotAgent.position.equals(position);
        }

        return false;
    }

    public boolean isAt(Coordinate coordinate) {
        return position.equals(coordinate);
    }
}
