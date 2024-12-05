package org.nihilistic.aoc.day4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Day4Controller {
    Logger logger = LoggerFactory.getLogger(Day4Controller.class);

    @Value("classpath:static/day4/day4_test.txt")
    Resource testResource;

    @Value("classpath:static/day4/day4.txt")
    Resource inputResource;

    @GetMapping("/day4/part1")
    public Integer execute1() throws IOException {
        var input = inputResource.getContentAsString(StandardCharsets.UTF_8);
        var wordsearch = Wordsearch.fromString(input);
        return wordsearch.countWord("XMAS");
    }

    @GetMapping("/day4/part2")
    public Integer execute2() throws IOException {
        var input = inputResource.getContentAsString(StandardCharsets.UTF_8);
        var wordsearch = Wordsearch.fromString(input);
        return wordsearch.countXWord("MAS");
    }
}
