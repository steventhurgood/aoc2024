package org.nihilistic.aoc.day4;

import org.junit.Test;

import junit.framework.Assert;

public class WordsearchTests {
   @Test
   public void testWordsearch_whenFromString_returnsCorrectValue() {
        // arrange
        String input = "abc\ndef\ngeh";

        var expected = new Wordsearch("abcdefgeh", 3, 3);

        // act
        var actual = Wordsearch.fromString(input);

        Assert.assertEquals(expected, actual);
   }

   @Test
   public void testWordsearch_whenCountWords_returnsCorrectValue() {
    // arrange
    String input = "abc\nbXb\ncXa";
    Wordsearch wordsearch = Wordsearch.fromString(input);
    int expected = 3;

    // act

    int actual = wordsearch.countWord("abc");
    Assert.assertEquals(expected, actual);
   }

   @Test
   public void testWordsearch_whenCountXWord_returnsCorrectValue() {
    // arrange
    /*
     * zzzz
     * zMzS
     * zzAz
     * zMzS
     */
    String input = "zzzz\nzMzS\nzzAz\nzMzS";
    Wordsearch wordsearch = Wordsearch.fromString(input);
    int expected = 1;

    // act

    int actual = wordsearch.countXWord("MAS");
    Assert.assertEquals(expected, actual);

   }
}
