package org.nihilistic.aoc.day10;

import org.junit.Test;

import com.google.common.collect.Lists;

import static com.google.common.truth.Truth.assertThat;

import java.util.HashMap;
import java.util.Set;

public class TopoMapTests {
    @Test
    public void testTopoMap_fromString_isCorrect() {
        // arrange
        String input = "98\n67";
        var expectedData = Lists.newArrayList(9, 8, 6, 7);// new ArrayList<Integer>();
        var expected = new TopoMap(2, 2, expectedData, new HashMap<TrailheadCacheKey, Set<Coordinate>>(), new HashMap<TrailheadCacheKey, Integer>());
        // act
        TopoMap actual = TopoMap.fromString(input);

        // assert

        assertThat(actual).isEqualTo(expected);

    }
    @Test
    public void testTopoMap_whenScore_isCorrect() {
        // arrange
        String input = "0123\n1234\n8765\n9876";
        TopoMap topoMap = TopoMap.fromString(input);

        // act

        var actual = topoMap.score();

        // assert

        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void testDiskMap_whenScore_isCorrectAtTwo() {
        String input = "...0...\n...1...\n...2...\n6543456\n7.....7\n8.....8\n9.....9";
        TopoMap topoMap = TopoMap.fromString(input);

        // act

        var actual = topoMap.score();

        // assert

        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void testTopoMap_whenTrailheads_isCorrect() {
        // arrange
        String input = "98\n67";
        TopoMap topoMap = TopoMap.fromString(input);

        // act

        var actual = topoMap.trailHeads(6, 0, 1);

        // assert

        assertThat(actual).containsExactly(
            new Coordinate(0, 0)
        );

    }
}
