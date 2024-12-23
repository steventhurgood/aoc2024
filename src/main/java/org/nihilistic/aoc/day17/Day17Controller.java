package org.nihilistic.aoc.day17;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class Day17Controller {
    Logger logger = LoggerFactory.getLogger(Day17Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day17/part1/{realm}")
    public String execute1(@PathVariable Realm realm, @RequestParam Optional<String> override,
            @RequestParam Optional<Integer> iterations, @RequestParam Optional<Integer> width,
            @RequestParam Optional<Integer> height) throws IOException {
        String input = inputService.getInput(17, realm, override);
        Computer computer = Computer.fromString(input);
        logger.info(computer.toString());
        var output = computer.run();
        return output.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @GetMapping("/day17/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(17, realm, override);
        return -1L;
    }
}