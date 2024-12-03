package org.nihilistic.aoc.day3;

public record StopExecutingInstruction() implements Instruction{

    @Override
    public void apply(Machine machine) {
        machine.stopExecuting();
    }
    
}
