package org.nihilistic.aoc;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

@Service
public class FooServiceImpl implements FooService {
    @Override
    public CompletableFuture<HelloResponse> getResponse(Long id, String responseName, int responseAge, String text) {
        return CompletableFuture.completedFuture(new HelloResponse(id, responseName, responseAge, text));
    }

}
