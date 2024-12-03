package org.nihilistic.aoc.day3;

public record StartExecutingInstruction() implements Instruction{

    @Override
    public void apply(Machine machine) {
        machine.startExecuting();
    }
}
