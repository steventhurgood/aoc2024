package org.nihilistic.aoc.day9;

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
public class Day9Controller {
    Logger logger = LoggerFactory.getLogger(Day9Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day9/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(9, realm, override);
        var diskMap = DiskMap.fromString(input);
        logger.info("{}", diskMap.toString());
        var defragged = diskMap.defragged();
        logger.info("{}", defragged.toString());
        return defragged.checksum();
    }

    @GetMapping("/day9/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(9, realm, override);
        // comment
        return -1L;
    }
}