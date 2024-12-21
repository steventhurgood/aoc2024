package org.nihilistic.aoc.day19;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Expression(Set<String> patterns) {
    private static Logger logger = LoggerFactory.getLogger(Expression.class);
    public static Expression fromString(String input) {
        var patterns = Set.of(input.split(", "));
        return new Expression(patterns);
    }

    private record PartialMatch(int i) {
        PartialMatch match(String subpattern) {
            return new PartialMatch(i + subpattern.length());
        }
    };

    public boolean matches(String line) {
        var partialMatches = new ArrayList<PartialMatch>();
        partialMatches.add(new PartialMatch(0));
        while (partialMatches.size() > 0) {
            logger.info("Partial matches: " + partialMatches.size());
            var newPartialMatches = new ArrayList<PartialMatch>();
            for (var partialMatch : partialMatches) {
                if (partialMatch.i == line.length()) {
                    return true;
                }
                for (var candidate : patterns) {
                    // does the prefix of line, starting at partialMatch.i, match candidate
                    if (line.startsWith(candidate, partialMatch.i)) {
                        newPartialMatches.add(partialMatch.match(candidate));
                    }
                }
            }
            partialMatches = newPartialMatches;
        }
        return false;
    }
}
/*
 * we need to build a state machine where each state has a valid set of next
 * chars and a flag indicating if it is a valid terminal state
 */
