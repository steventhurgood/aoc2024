package org.nihilistic.aoc.day9;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class DiskMapTests {
    @Test
    public void testDiskMap_whenFromString_isCorrect() {
        // arrange
        String input = "2333133121414131402";

        // act
        DiskMap actual = DiskMap.fromString(input);

        // assert
        // 00...111...2...333.44.5555.6666.777.888899
        assertThat(actual.allocations()).containsExactly(
                new DiskAllocation(0, 2, 0, false),
                new DiskAllocation(2, 5, -1, true),
                new DiskAllocation(5, 8, 1, false),
                new DiskAllocation(8, 11, -1, true),
                new DiskAllocation(11, 12, 2, false),
                new DiskAllocation(12, 15, -1, true),
                new DiskAllocation(15, 18, 3, false),
                new DiskAllocation(18, 19, -1, true),
                new DiskAllocation(19, 21, 4, false),
                new DiskAllocation(21, 22, -1, true),
                new DiskAllocation(22, 26, 5, false),
                new DiskAllocation(26, 27, -1, true),
                new DiskAllocation(27, 31, 6, false),
                new DiskAllocation(31, 32, -1, true),
                new DiskAllocation(32, 35, 7, false),
                new DiskAllocation(35, 36, -1, true),
                new DiskAllocation(36, 40, 8, false),
                new DiskAllocation(40, 42, 9, false));
    }

    @Test
    public void testDiskMap_whenDefragged_isCorrect() {
        // arrange
        String input = "2333133121414131402";
        DiskMap diskMap = DiskMap.fromString(input);
        var expected = "0099811188827773336446555566";

        // act
        DiskMap actualMap = diskMap.defragged();
        var actual = actualMap.toString();


        assertThat(actual).isEqualTo(expected);
        // assert
        // 0099811188827773336446555566
        // got:
        // 00...111...2...333.44.5555.6666.777.888899
        // 0099811188828883338448555586666877788"
        /*
        assertThat(actual.allocations()).containsExactly(
                new DiskAllocation(0, 2, 0, false),
                new DiskAllocation(2, 4, 9, false),
                new DiskAllocation(4, 5, 8, false),
                new DiskAllocation(5, 8, 1, false),
                new DiskAllocation(8, 11, 8, false),
                new DiskAllocation(11, 12, 2, false),
                new DiskAllocation(12, 15, 7, false),
                new DiskAllocation(15, 18, 3, false),
                new DiskAllocation(18, 19, 6, false),
                new DiskAllocation(19, 21, 4, false),
                new DiskAllocation(21, 22, 6, false),
                new DiskAllocation(22, 26, 5, false),
                new DiskAllocation(22, 26, 5, false),
                new DiskAllocation(26, 28, 6, false));
                */

    }

    @Test
    public void testDiskMap_whenChecksum_isCorrect() {
        // arrange
        String input = "2333133121414131402";
        DiskMap diskMap = DiskMap.fromString(input);
        DiskMap actualMap = diskMap.defragged();

        // act
        var actual = actualMap.checksum();

        // assert

        assertThat(actual).isEqualTo(1928);
    }

    @Test
    public void testDiskMap_whenDefragWholeFiles_isCorrect() {
        // arrange
        String input = "2333133121414131402";
        DiskMap diskMap = DiskMap.fromString(input);
        var expected = "00992111777.44.333....5555.6666.....8888..";

        // act
        DiskMap actualMap = diskMap.defragWholeFile();
        var actual = actualMap.toString();

        // assert
        assertThat(actual).isEqualTo(expected);
        assertThat(actualMap.checksum()).isEqualTo(2858);
    }
}
