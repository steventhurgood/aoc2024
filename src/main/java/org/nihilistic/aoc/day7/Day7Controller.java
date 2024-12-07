package org.nihilistic.aoc.day7;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Day7Controller {
    Logger logger = LoggerFactory.getLogger(Day7Controller.class);

    @Value("classpath:static/day7/day7_test.txt")
    Resource testResource;

    @Value("classpath:static/day7/day7.txt")
    Resource inputResource;

    @GetMapping("/day7/part1/{release}")
    public long execute1(@PathVariable String release, @Nullable @RequestParam String input) throws IOException {
        if (Objects.isNull(input)) {
            var resource = (release.equals("prod") ? inputResource : testResource);
            input = resource.getContentAsString(StandardCharsets.UTF_8);
        }
        return EquationValidator.SumValidEquations(input);
    }

    @GetMapping("/day7/part2/{release}")
    public long execute2(@PathVariable String release, @Nullable @RequestParam String input) throws IOException {
        if (Objects.isNull(input)) {
            var resource = (release.equals("prod") ? inputResource : testResource);
            input = resource.getContentAsString(StandardCharsets.UTF_8);
        }
        return EquationValidator.sumValidEquationsWithConcat(input);
    }
}