package org.nihilistic.aoc.day15;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class WarehouseMapTests {
    @Test
    public void testWarehouseMap_whenFromString_isCorrect() {
        // arrange

        String input = """
               #### 
               #..#
               #@O#
               ####
                """;

        // act
        var actual = WarehouseMap.fromString(input);

        // assert

        assertThat(actual.agent()).isEqualTo(new RobotAgent(new Coordinate(1, 2)));
        assertThat(actual.barrels()).containsExactly(new Coordinate(2, 2));

    }
}
