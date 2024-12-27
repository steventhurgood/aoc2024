package org.nihilistic.aoc.day13;

import org.junit.Test;
import org.nihilistic.aoc.grid.Coordinate;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;

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
    public void testClawMachine_whenSolve_isCorrect() {
        // this one had an integer solution for b, but not for a.
        var input = """
                Button A: X+26, Y+26
                Button B: X+13, Y+61
                Prize: X=936, Y=3528
                """;
        
        var clawMachine = ClawMachine.fromString(input);

        var cost = clawMachine.embiggen().solve(); 

        assertThat(cost).isEmpty();
    }

    @Test
    public void testClawMachine_whenAmbiguous_throwsException() {
        var input = """
                Button A: X+1, Y+1
                Button B: X+2, Y+2
                Prize: X=10, Y=10
                """;
        var clawMachine = ClawMachine.fromString(input);

        assertThrows(ArithmeticException.class, () -> clawMachine.solve());
    }
}
