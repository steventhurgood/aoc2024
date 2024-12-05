package org.nihilistic.aoc.day4;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.stream.Collectors;

public record Wordsearch(String letters, int width, int height) {

    enum Offset {

        RIGHT(1, 0),
        LEFT (-1, 0),
        DOWN (0, 1),
        UP (0, -1),
        DOWNRIGHT (1, 1),
        DOWNLEFT (-1, 1),
        UPRIGHT (1, -1),
        UPLEFT (-1, -1);

        public final int delta_x;
        public final int delta_y;

        Offset(int delta_x, int delta_y) {
            this.delta_x = delta_x;
            this.delta_y = delta_y;
        }
    }

    public static Wordsearch fromString(String input) {
        var reader = new BufferedReader(new StringReader(input));
        var lines = reader.lines().map(String::trim).collect(Collectors.toList());
        var width = lines.get(0).length();
        var height = lines.size();
        var letters = lines.stream().collect(Collectors.joining());

        return new Wordsearch(letters, width, height);
    }

    private char getChar(int x, int y) {
        var offset = y * width + x;
        return letters.charAt(offset);
    }

    public Integer countWord(String word) {
        var matchCount = 0;
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                matchCount += countWord(word, x, y);
            }
        }
        return matchCount;
    }

    private int countWord(String word, int x, int y) {
        var matchCount = 0;
        var offsets = Offset.values();
        for (var i = 0; i < offsets.length; i++) {
            if (findWord(word, x, y, offsets[i])) {
                matchCount++;
            }
        }
        return matchCount;
    }

    private boolean findWord(String word, int x, int y, Offset offset) {
        var x_delta = offset.delta_x;
        var y_delta = offset.delta_y;

        for (var i = 0; i < word.length(); i++) {
            var offset_x = x + i * x_delta;
            var offset_y = y + i * y_delta;

            if (offset_x < 0 || offset_x >= width || offset_y < 0 || offset_y >= height) {
                return false;
            }

            if (word.charAt(i) != getChar(offset_x, offset_y)) {
                return false;
            }
        }
        return true;
    }

    public int countXWord(String word) {
        var matchCount = 0;
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                if (findXWord(word, x, y)) {
                    matchCount++;
                }
            }
        }
        return matchCount;
    }

    public boolean findXWord(String word, int x, int y) {
        // if we are checking 3,4 for a word of length 2, then we need to start at 4, 5

        var downRight = findWord(word, x, y, Offset.DOWNRIGHT);
        var downLeft = findWord(word, x + word.length() - 1, y, Offset.DOWNLEFT);
        var upRight = findWord(word, x, y + word.length() - 1, Offset.UPRIGHT);
        var upLeft = findWord(word, x + word.length() - 1, y + word.length() - 1, Offset.UPLEFT);

        return (downRight || upLeft) && (downLeft || upRight);
    }
}
