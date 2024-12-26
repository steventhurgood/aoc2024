package org.nihilistic.aoc.day25;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class LockTests {
    @Test
    public void testLock_whenFromString_isCorrect() {
        var input = """
                #####
                .####
                .####
                .####
                .#.#.
                .#...
                .....
                """;
        var actual = Lock.fromString(input);

        assertThat(actual.tumblers()).containsExactly(0, 5, 3, 4, 3).inOrder();
    }
}
