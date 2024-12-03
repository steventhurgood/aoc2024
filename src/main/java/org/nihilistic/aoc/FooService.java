package org.nihilistic.aoc;

import java.util.concurrent.CompletableFuture;

public interface FooService {
    public CompletableFuture<HelloResponse> getResponse(Long id, String responseName, int responseAge, String text);
}
