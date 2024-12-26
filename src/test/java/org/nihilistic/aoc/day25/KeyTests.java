package org.nihilistic.aoc.day25;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class KeyTests {
    @Test
    public void testKey_whenFromString_isCorrect() {
        var input = """
                .....
                #....
                #....
                #...#
                #.#.#
                #.###
                #####
                """;

        var actual = Key.fromString(input);

        assertThat(actual.teeth()).containsExactly(5, 0, 2, 1, 3).inOrder();
    }
}
