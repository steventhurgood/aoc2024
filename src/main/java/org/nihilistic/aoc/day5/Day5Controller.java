package org.nihilistic.aoc.day5;

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
public class Day5Controller {
    Logger logger = LoggerFactory.getLogger(Day5Controller.class);

    @Value("classpath:static/day5/day5_test.txt")
    Resource testResource;

    @Value("classpath:static/day5/day5.txt")
    Resource inputResource;

    @GetMapping("/day5/part1/{release}")
    public Integer execute1(@PathVariable String release) throws IOException {
        var resource = (release.equals("prod") ? inputResource : testResource);
        var input = resource.getContentAsString(StandardCharsets.UTF_8);
        logger.info("Input: {} chars", input.length());
        return null;
    }

    @GetMapping("/day5/part2/{release}")
    public Integer execute2(@PathVariable String release) throws IOException {
        var resource = (release.equals("prod") ? inputResource : testResource);
        var input = resource.getContentAsString(StandardCharsets.UTF_8);
        logger.info("Input: {} chars", input.length());
        return null;
    }
}
