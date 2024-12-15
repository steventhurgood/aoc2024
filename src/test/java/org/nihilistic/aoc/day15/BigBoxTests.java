package org.nihilistic.aoc.day15;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class BigBoxTests {
    @Test
    public void testBigBox_whenDistanceToLeftSide_isCorrect() {
        var box = new BigBox(new Coordinate(5, 1));
        var actual = box.horizontalDistanceToSide(14);
        assertThat(actual).isEqualTo(5);
    }

    @Test
    public void testBigBox_whenDistanceToRightSide_isCorrect() {
        var box = new BigBox(new Coordinate(5, 1));
        var actual = box.horizontalDistanceToSide(8);
        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void testBigBox_whenDistanceToTopSide_isCorrect() {
        var box = new BigBox(new Coordinate(5, 3));
        var actual = box.verticalDistanceToSide(14);
        assertThat(actual).isEqualTo(3);
    }

    @Test
    public void testBigBox_whenDistanceToBottomSide_isCorrect() {
        var box = new BigBox(new Coordinate(5, 3));
        var actual = box.verticalDistanceToSide(4);
        assertThat(actual).isEqualTo(1);
    }
}
