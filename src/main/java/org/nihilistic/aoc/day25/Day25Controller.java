package org.nihilistic.aoc.day25;

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
public class Day25Controller {
    Logger logger = LoggerFactory.getLogger(Day25Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day25/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(25, realm, override);
        var locks = Locks.fromString(input);
        return locks.countMatchingKeys();
    }

    @GetMapping("/day25/part2/{realm}")
    public Long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(25, realm, override);
        return -1L;
    }
}