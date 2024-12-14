package org.nihilistic.aoc.day13;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.truth.Truth;

public class ClawMachineStreamTests {
    private String shortInput = """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400

            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176

            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450

            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
                        """;

    @Before
    public void loadString() throws IOException {
    }

    @Test
    public void testClawMachineStreams_whenFromString_isCorrect() {
        // arrange

        var clawMachines = new ClawMachineStream(shortInput);

        // act
        var actual = clawMachines.stream().toArray();

        // assert
        Truth.assertThat(actual.length).isEqualTo(4);
    }
}
