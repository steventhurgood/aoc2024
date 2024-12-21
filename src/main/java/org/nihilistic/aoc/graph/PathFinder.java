package org.nihilistic.aoc.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public record PathFinder<G extends Graph<N>, N extends Node>(G graph) {
    public List<N> findShortestPath(N start) {
        var cameFrom = findAllPaths(start);
        var path = new ArrayList<N>();
        var endStates = cameFrom.keySet().stream().filter(graph::isEnd).toList();
        if (endStates.size() == 0) {
            // no end states
            return null;
        }
        var current = endStates.get(0);
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }

        return path.reversed();
    }

    public Map<N, N> findAllPaths(N state) {
        var cameFrom = new HashMap<N, N>();
        var bestCostToNode = new HashMap<N, Long>();
        var bestCostComparator = Comparator.comparing(bestCostToNode::get);
        bestCostToNode.put(graph.start(), 0L);
        var queue = new PriorityQueue<N>(bestCostComparator);
        queue.add(graph.start());
        while (queue.size() > 0) {
            var current = queue.poll();
            var bestCostToCurrent = bestCostToNode.get(current);
            for (var edge : graph.adjacent(current)) {
                var cost = edge.cost();
                var neighbour = edge.to();
                var currentBestCostToNeighbour = bestCostToNode.getOrDefault(neighbour, Long.MAX_VALUE);
                var newCostToNeighbour = bestCostToCurrent + cost;
                if (newCostToNeighbour < currentBestCostToNeighbour) {
                    cameFrom.put(neighbour, current);
                    bestCostToNode.put(neighbour, newCostToNeighbour);
                    queue.add(neighbour);
                }
            }
        }
        return cameFrom;

    }
}
