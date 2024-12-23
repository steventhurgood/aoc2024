package org.nihilistic.aoc.day22;

import java.util.stream.Stream;

public class Sequence {
   private long secret;
   
   Sequence(long secret) {
    this.secret = secret;
   }

   long next() {
    var value = secret;
    secret = (secret ^ (secret << 6)) & 16777215;
    // System.out.println(secret);
    secret = (secret ^ (secret >>5)) & 16777215;
    // System.out.println(secret);
    secret = (secret ^ (secret << 11)) & 16777215;
    // System.out.println(secret);
    return value;
   }

   Stream<Long> stream() {
    return Stream.generate(() -> next());
   }

   long nth(int n) {
    for (var i=0; i < n; i++) {
        next();
    }
        return secret;
   }
}