package org.nihilistic.aoc.day19;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record PatternMatcher(StateMachine stateMachine, List<String> lines) {
    private static Logger logger = LoggerFactory.getLogger(PatternMatcher.class);

    public static PatternMatcher fromString(String input) {
        String lines[] = input.split("\n");
        var stateMachine = StateMachine.fromString(lines[0]);
        var patterns = Arrays.copyOfRange(lines, 2, lines.length);
        return new PatternMatcher(stateMachine, List.of(patterns));
    }

    public long countMatches() {
        return lines.stream().peek(line -> logger.info("Checking " + line)).mapToLong(stateMachine::matches).sum();
    }
}
