package org.nihilistic.aoc.day15;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class BigWarehouseTests {
    @Test
    public void testBigWarehouse_whenSimulate_isCorrect() {
        var input = """
#######
#...#.#
#.....#
#..OO@#
#..O..#
#.....#
#######

<vv<<^^<<^^
                """;

                var warehouse = Warehouse.fromString(input);
                var bigWarehouse = warehouse.embiggen();

                bigWarehouse.simulate();

                var actual = bigWarehouse.gps();
                var expected = (100 + 5) + (200 + 5) + (300 + 6);

                assertThat(actual).isEqualTo(expected);
    }
}
