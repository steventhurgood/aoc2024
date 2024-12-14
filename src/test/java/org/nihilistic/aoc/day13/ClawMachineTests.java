package org.nihilistic.aoc.day13;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ClawMachineTests {
    private static String input = """
    Button A: X+94, Y+34
    Button B: X+22, Y+67
    Prize: X=8400, Y=5400""";

    @Test
    public void testClawMachine_whenFromString_isCorrect() {
        // arrange

        var expected = new ClawMachine(new Coordinate(94, 34), new Coordinate(22, 67), new Coordinate(8400, 5400));

        // act
        var actual = ClawMachine.fromString(input);

        // assert

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testClawMachine_whenFindShortestPath_isCorrect() {
        // arrange
        var clawMachine = ClawMachine.fromString(input);

        // act
        var actual = clawMachine.findPath();

        // assert

        assertThat(actual).isEqualTo(280);
    }
}
