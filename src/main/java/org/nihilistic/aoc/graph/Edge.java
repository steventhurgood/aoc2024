package org.nihilistic.aoc.graph;

public record Edge<N extends Node> (N from, N to, long cost) {
}
