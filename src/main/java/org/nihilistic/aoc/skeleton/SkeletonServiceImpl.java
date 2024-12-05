package org.nihilistic.aoc.skeleton;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

@Service
public class SkeletonServiceImpl implements SkeletonService {

    @Override
    public CompletableFuture<Integer> execute(String input) {
        var random = new Random();
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            if (random.nextDouble() < 0.5) {
                throw new IllegalArgumentException();
            }
            return Integer.valueOf(input);
        });
        return result.exceptionally(ex -> {
            return -10000;
        });
    }

}
