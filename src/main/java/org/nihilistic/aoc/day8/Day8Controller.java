package org.nihilistic.aoc.day8;

import java.io.IOException;
import java.util.Optional;

import org.nihilistic.aoc.InputService;
import org.nihilistic.aoc.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Day8Controller {
    Logger logger = LoggerFactory.getLogger(Day8Controller.class);

    @Value("classpath:static/day8/day8_test.txt")
    Resource testResource;

    @Value("classpath:static/day8/day8.txt")
    Resource inputResource;

    @Autowired
    InputService inputService;

    @GetMapping("/day8/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(8, realm, override);
        var antinodeCounter = AntinodeCounter.countUniqueAntinodes(input);
        return antinodeCounter;
    }

    @GetMapping("/day8/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(8, realm, override);
        var antinodeCounter = AntinodeCounter.countUniqueAntinodesInRange(input);
        return antinodeCounter;
    }
}