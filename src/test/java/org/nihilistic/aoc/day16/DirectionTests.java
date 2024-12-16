package org.nihilistic.aoc.day16;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class DirectionTests {
    @Test
    public void testDirection_whenClockwise_isCorrect() {
        Direction directions[] = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
       
        var actual = Arrays.stream(directions).map(Direction::clockwise).collect(Collectors.toList());

        assertThat(actual).containsExactly(
            Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH
        ).inOrder();
    } 

    @Test
    public void testDirection_whenCounterclockwise_isCorrect() {
        Direction directions[] = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
       
        var actual = Arrays.stream(directions).map(Direction::counterclockwise).collect(Collectors.toList());

        assertThat(actual).containsExactly(
            Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH
        ).inOrder();
    } 
}
