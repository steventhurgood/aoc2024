package org.nihilistic.aoc.day25;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LocksTests {
    private static String input = """
            #####
            .####
            .####
            .####
            .#.#.
            .#...
            .....

            #####
            ##.##
            .#.##
            ...##
            ...#.
            ...#.
            .....

            .....
            #....
            #....
            #...#
            #.#.#
            #.###
            #####

            .....
            .....
            #.#..
            ###..
            ###.#
            ###.#
            #####

            .....
            .....
            .....
            #....
            #.#..
            #.#.#
            #####
            """;

    @Test
    public void testLocks_whenCountMatchingKeys_isCorrect() {
        var locks = Locks.fromString(input);
        var actual = locks.countMatchingKeys();

        assertThat(actual).isEqualTo(3);
    }

    @Test
    public void testLocks_whenMatchingKeys_isCorrect() {
        var locks = Locks.fromString(input);
        var actual = new ArrayList<Boolean>();

        assertThat(locks.locks()).containsExactly(
            new Lock(List.of(0, 5, 3, 4, 3)),
            new Lock(List.of(1, 2, 0, 5, 3))
        );

        assertThat(locks.keys()).containsExactly(
            new Key(List.of(5, 0, 2, 1, 3)),
            new Key(List.of(4, 3, 4, 0, 2)),
            new Key(List.of(3, 0, 2, 0, 1))
        );

        for (var lock : locks.locks()) {
            for (var key : locks.keys()) {
                var overlaps = key.overlaps(lock);
                actual.add(overlaps);
            }
        }
 
        assertThat(actual).containsExactly(
            true, true, false, true, false, false).inOrder();
    }
}
