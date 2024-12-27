package org.nihilistic.aoc.day13;

import java.io.IOException;
import java.util.HashMap;
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
public class Day13Controller {
    Logger logger = LoggerFactory.getLogger(Day13Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day13/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(13, realm, override);
        var clawMachineStream = new ClawMachineStream(input);
        var cost = clawMachineStream.stream().map(ClawMachine::solve).filter(Optional::isPresent).mapToLong(Optional::get).sum();
        return cost;
    }

    @GetMapping("/day13/part2/{realm}")
    public long execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(13, realm, override);
        var clawMachineStream = new ClawMachineStream(input);
        var costs = clawMachineStream.stream().map(ClawMachine::embiggen).map(ClawMachine::solve).toList();
        long totalCost=0;
        for (var cost : costs) {
            var thisCost = cost.orElse(0L);
            logger.info("" + thisCost);
            totalCost += thisCost;
        }
        return totalCost;
    }
}