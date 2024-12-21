package org.nihilistic.aoc.day20;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Optional;

import org.nihilistic.aoc.InputService;
import org.nihilistic.aoc.Realm;
import org.nihilistic.aoc.graph.PathFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Day20Controller {
    Logger logger = LoggerFactory.getLogger(Day20Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day20/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override,
            @RequestParam Optional<Integer> iterations, @RequestParam Optional<Integer> width,
            @RequestParam Optional<Integer> height) throws IOException {
        String input = inputService.getInput(20, realm, override);
        var racetrack = Racetrack.fromString(input);
        logger.info("\n" + racetrack.toString());
        var pathFinder = new PathFinder<Racetrack, Racetrack.Coordinate>(racetrack);
        logger.info("Finding path");
        var path = pathFinder.findShortestPath(racetrack.start());
        logger.info("Finding cheats");
        var savingsCount = racetrack.countSavings(path, 20);
        for (var savings : savingsCount.keySet()) {
            var count = savingsCount.get(savings);
            logger.info("There are " + count + " cheats that save " + savings + " picoseconds");
        }
        return savingsCount.entrySet().stream().filter(entry -> entry.getKey() >= 100).mapToLong(Entry::getValue).sum();
    }

    @GetMapping("/day20/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(20, realm, override);
        return -1;
    }
}