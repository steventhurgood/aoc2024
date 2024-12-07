package org.nihilistic.aoc.day6;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Day6Controller {
    Logger logger = LoggerFactory.getLogger(Day6Controller.class);

    @Value("classpath:static/day6/day6_test.txt")
    Resource testResource;

    @Value("classpath:static/day6/day6.txt")
    Resource inputResource;

    @GetMapping("/day6/part1/{release}")
    public Integer execute1(@PathVariable String release) throws IOException {
        var resource = (release.equals("prod") ? inputResource : testResource);
        var input = resource.getContentAsString(StandardCharsets.UTF_8);
        var guardRoom = GuardRoom.fromString(input);
        var walk = guardRoom.walk();
        return walk;
    }

    @GetMapping("/day6/part2/{release}")
    public Integer execute2(@PathVariable String release) throws IOException {
        var resource = (release.equals("prod") ? inputResource : testResource);
        var input = resource.getContentAsString(StandardCharsets.UTF_8);
        var guardRoom = GuardRoom.fromString(input);
        var obstructionCount = guardRoom.findObstructionsThatCauseLoops();
        return obstructionCount;
    }
}
