package org.nihilistic.aoc.day15;

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
public class Day15Controller {
    Logger logger = LoggerFactory.getLogger(Day15Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day15/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override,
            @RequestParam Optional<Integer> iterations, @RequestParam Optional<Integer> width,
            @RequestParam Optional<Integer> height) throws IOException {
        String input = inputService.getInput(15, realm, override);
        var warehouse= Warehouse.fromString(input);
        warehouse.simulate();

        return warehouse.gps();
    }

    @GetMapping("/day15/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(15, realm, override);
        var warehouse= Warehouse.fromString(input);
        var bigWarehouse = warehouse.embiggen();
        bigWarehouse.simulate();

        return bigWarehouse.gps();
    }
}