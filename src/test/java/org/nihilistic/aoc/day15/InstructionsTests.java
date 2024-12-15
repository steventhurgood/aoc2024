package org.nihilistic.aoc.day15;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class InstructionsTests {
    @Test
    public void testInstructions_whenFromString_isCorrect() {
        // arrange
        String input = "<^>\nv  v";

        // act
        var actual = Instructions.fromString(input);

        assertThat(actual.instructions()).containsExactly(Instruction.LEFT, Instruction.UP, Instruction.RIGHT,
                Instruction.DOWN, Instruction.DOWN).inOrder();
    }
}
