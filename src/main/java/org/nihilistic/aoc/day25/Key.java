package org.nihilistic.aoc.day25;

import java.util.ArrayList;
import java.util.List;

public record Key (List<Integer> teeth) {

    public static Key fromString(String schema) {
        var teeth = new ArrayList<Integer>(5);
        for (var x=0; x < 5; x++) {
            var y=0;
            while (schema.charAt((6-y)*6+x) != '.') {
                y++;
            }
            teeth.add(y-1);
        }
        return new Key(teeth);
    }

    public boolean overlaps(Lock lock) {
        for (var i=0; i < 5; i++) {
            if (teeth.get(i) + lock.tumblers().get(i) >= 6)  {
                return true;
            }
        }
        return false;
    }
}
