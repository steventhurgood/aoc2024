package org.nihilistic.aoc.day25;

import java.util.ArrayList;
import java.util.List;

public record Lock(List<Integer> tumblers) {
    public static Lock fromString(String schema) {
        var tumblers = new ArrayList<Integer>(5);
        for (var x=0; x < 5; x++) {
            var y=0;
            while (schema.charAt(y*6+x) != '.') {
                y++;
            }
            tumblers.add(y-1);
        }
        return new Lock(tumblers);
    }
}
