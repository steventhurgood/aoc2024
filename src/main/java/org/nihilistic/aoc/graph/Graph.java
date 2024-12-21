package org.nihilistic.aoc.graph;

import java.util.List;

public interface Graph<N extends Node> {
    N start();
    boolean isEnd(N node);
    List<Edge<N>> adjacent(N node);
}
