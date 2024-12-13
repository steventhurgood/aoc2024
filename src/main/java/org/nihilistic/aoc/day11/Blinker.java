package org.nihilistic.aoc.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public record Blinker(List<Long> digits, BlinkCache cache) {

    public static Blinker fromString(String input) {
        var digits = Arrays.stream(input.split(" ")).map(Long::valueOf).collect(Collectors.toList());
        return new Blinker(digits, new BlinkCache());
    }

    public long countStones() {
        return countStones(25);
    }

    public long countStones(int blinksLeft) {
        long sum = 0;
        for (var digit : digits) {
            sum += countBlinkedDigits(digit, blinksLeft);
        }
        return sum;
    }

    public long countStonesWithCaching(int blinksLeft) {
        long sum = 0;
        for (var digit : digits) {
            var digits = blinkDigit(digit, blinksLeft);
            sum += digits.size();
        }
        return sum;

    }

    public long countBlinkedDigits(Long n, int blinksLeft) {
        if (blinksLeft == 0) {
            return 1;
        }
        if (n == 0) {
            return countBlinkedDigits(1L, blinksLeft - 1);
        }
        var digits = String.valueOf(n);
        if (digits.length() % 2 == 0) {
            var midPoint = digits.length() / 2;
            var left = Long.valueOf(digits.substring(0, midPoint));
            var right = Long.valueOf(digits.substring(midPoint, digits.length()));
            return countBlinkedDigits(left, blinksLeft - 1) + countBlinkedDigits(right, blinksLeft - 1);
        }
        return countBlinkedDigits(n * 2024, blinksLeft - 1);
    }

    public List<Long> blinkDigit(Long n, int blinksLeft) {
        // have we blinked this digit before, and if so how many times?
        if (blinksLeft == 0) {
            return Lists.newArrayList(n);
        }
        // we have to process the number n, blinksLeft more times (eg., 10 more times).
        // if we have already seen n some other number of times (say, 1,2,3,4,5,6) then we can
        // fast foward the process by getting that list, and processing it (blinksLeft - maxCached) = 10-6 - 4 times.
   
        var cachedResult = cache.getMaxCachedValueUpTo(n, blinksLeft);
        if (cachedResult.isPresent()) {
            var result = new ArrayList<Long>();
            var cachedBlinks = cachedResult.get().blinks();
            for (var value : cachedResult.get().values()) {
                result.addAll(blinkDigit(value, blinksLeft - cachedBlinks));
            }
            cache.put(n, blinksLeft, result);
            return result;
        }
        if (n == 0) {
            return blinkDigit(1L, blinksLeft - 1);
        }
        var digits = String.valueOf(n);
        if (digits.length() % 2 == 0) {
            var midPoint = digits.length() / 2;
            var left = Long.valueOf(digits.substring(0, midPoint));
            var right = Long.valueOf(digits.substring(midPoint, digits.length()));
            var leftList = blinkDigit(left, blinksLeft - 1);
            var rightList = blinkDigit(right, blinksLeft - 1);
            List<Long> result = Lists.newArrayListWithCapacity(leftList.size() + rightList.size());
            result.addAll(leftList);
            result.addAll(rightList);
            cache.put(n, blinksLeft, result);
            return result;
        }
        var result = blinkDigit(n * 2024, blinksLeft - 1);
        cache.put(n, blinksLeft, result);
        return result;
    }
}
