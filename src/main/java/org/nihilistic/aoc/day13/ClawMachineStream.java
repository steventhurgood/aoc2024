package org.nihilistic.aoc.day13;

import java.util.Objects;
import java.util.stream.Stream;

public class ClawMachineStream {
    private String input;
    private int firstI;
    private boolean finished = false;

    ClawMachineStream(String input) {
        this.input = input;
        this.firstI = 0;
    }

    public Stream<ClawMachine> stream() {
        return Stream.generate(() -> next()).takeWhile(Objects::nonNull);
    }

    public ClawMachine next() {
        if (finished) {
            return null;
        }

        for (var i = firstI; i < input.length() - 1; i++) {
            if (input.charAt(i) == '\n' && input.charAt(i + 1) == '\n') {
                var oldFirstI = firstI;
                firstI = i + 2;
                return ClawMachine.fromString(input.substring(oldFirstI, i));
            }
        }
        finished = true;
        return ClawMachine.fromString(input.substring(firstI, input.length()));
    }
}