package org.nihilistic.aoc.day14;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
public class Day14Controller {
    Logger logger = LoggerFactory.getLogger(Day14Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day14/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override,
            @RequestParam Optional<Integer> iterations, @RequestParam Optional<Integer> width,
            @RequestParam Optional<Integer> height) throws IOException {
        String input = inputService.getInput(14, realm, override);
        var robots = Robots.fromString(input, width.orElse(101), height.orElse(103));
        robots = robots.simulate(iterations.orElse(100));
        logger.info("\n" + robots.toString());
        return robots.countByQuadrant();
    }

    @GetMapping("/day14/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override,
            @RequestParam Optional<Integer> iterations, @RequestParam Optional<Integer> width,
            @RequestParam Optional<Integer> height,
            @RequestParam String output) throws IOException {
        String input = inputService.getInput(14, realm, override);
        var robots = Robots.fromString(input, width.orElse(101), height.orElse(103));
        FileWriter fw = new FileWriter(output, true);
        BufferedWriter bw = new BufferedWriter(fw);

        for (var i = 0; i < iterations.orElse(1); i++) {
            bw.write("Iteration: " + i);
            bw.newLine();
            bw.write(robots.toString());
            bw.newLine();
            robots = robots.simulate(1);
        }
        bw.close();
        return -1;
    }
}