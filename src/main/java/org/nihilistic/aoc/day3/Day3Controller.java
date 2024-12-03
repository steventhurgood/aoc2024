package org.nihilistic.aoc.day3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Day3Controller {

    @Value("classpath:static/day3/day3.txt")
    Resource inputResource;

    @Value("classpath:static/day3/day3_test.txt")
    Resource testResource;

    @Value("classpath:static/day3/day3_test_part2.txt")
    Resource testResource2;

    @GetMapping("/day3/part1")
    public int execute1() throws IOException {
        var machine = new Machine();
        var input = inputResource.getContentAsString(StandardCharsets.UTF_8);
        var instructions = InstructionFactory.mulFromString(input);
        instructions.stream().forEach(instruction -> instruction.apply(machine));
        return machine.getTotal();
    }

    @GetMapping("/day3/part2")
    public int execute2() throws IOException {
        var machine = new Machine();
        var input = inputResource.getContentAsString(StandardCharsets.UTF_8);
        var instructions = InstructionFactory.fullFromString(input);
        instructions.stream().forEach(instruction -> instruction.apply(machine));
        return machine.getTotal();
    }
}
