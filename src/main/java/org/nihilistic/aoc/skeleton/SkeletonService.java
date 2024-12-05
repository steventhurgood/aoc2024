package org.nihilistic.aoc.skeleton;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

public interface SkeletonService {
    @Async
    public CompletableFuture<Integer> execute(String input);
}
