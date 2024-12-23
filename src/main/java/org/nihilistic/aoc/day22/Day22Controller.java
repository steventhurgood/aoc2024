package org.nihilistic.aoc.day22;

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
public class Day22Controller {
    Logger logger = LoggerFactory.getLogger(Day22Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day22/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(22, realm, override);
        var sequenceFinder = SequenceFinder.fromString(input);
        return sequenceFinder.findOptimalSequence(2000);
    }

    @GetMapping("/day22/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(22, realm, override);
        var sequenceFinder = SequenceFinder.fromString(input);
        sequenceFinder.logDiffs(2000);
        return -1;
    }
}