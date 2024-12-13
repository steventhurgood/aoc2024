package org.nihilistic.aoc.day12;

import java.io.IOException;
import java.util.Optional;

import org.nihilistic.aoc.InputService;
import org.nihilistic.aoc.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Day12Controller {
    Logger logger = LoggerFactory.getLogger(Day12Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day12/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(12, realm, override);
        var garden = Garden.fromString(input);
        return garden.fencingCost();
    }

    @GetMapping("/day12/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(12, realm, override);
        var garden = Garden.fromString(input);
        return garden.fencingCost();
    }
}