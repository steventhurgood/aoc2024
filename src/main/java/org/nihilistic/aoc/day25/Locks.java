package org.nihilistic.aoc.day25;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public record Locks (Collection<Lock> locks, Collection<Key> keys) {

    public static Locks fromString(String input) {
        var locks = new ArrayList<Lock>();
        var keys = new ArrayList<Key>();

        var schemas = input.split("\n\n");
        for (var schema : schemas) {
            // schema should be a 30 char (5+'\n' x5) string
            if (schema.charAt(0) == '#') {
                // this is a lock
                locks.add(Lock.fromString(schema));
            } else {
                // this is a key
                keys.add(Key.fromString(schema));
            }
        }

        return new Locks(locks, keys);
    }

    public long countMatchingKeys() {
        var count=0;
        for (var key : keys) {
            for (var lock : locks) {
                if (!key.overlaps(lock)) {
                    count ++;
                }
            }
        }
        return count;
    }
}
