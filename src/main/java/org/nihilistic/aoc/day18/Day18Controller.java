package org.nihilistic.aoc.day18;

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
public class Day18Controller {
    Logger logger = LoggerFactory.getLogger(Day18Controller.class);

    @Autowired
    InputService inputService;

    @GetMapping("/day18/part1/{realm}")
    public long execute1(@PathVariable Realm realm, @RequestParam Optional<String> override,
            @RequestParam Optional<Integer> iterations, @RequestParam Optional<Integer> width,
            @RequestParam Optional<Integer> height) throws IOException {
        String input = inputService.getInput(18, realm, override);
        var maze = MemoryMaze.fromString(input);
        var start = new Coordinate(0, 0);
        var end = new Coordinate(70, 70);
        var bytes = 1024;
        if (realm.equals(Realm.TEST)) {
            end = new Coordinate(6, 6);
            bytes = 12;
        }
        return maze.findPath(start, end, bytes).getAsInt();
    }

    @GetMapping("/day18/part2/{realm}")
    public Coordinate execute2(@PathVariable Realm realm, @RequestParam Optional<String> override) throws IOException {
        String input = inputService.getInput(18, realm, override);
        var maze = MemoryMaze.fromString(input);
        var start = new Coordinate(0, 0);
        var end = new Coordinate(70, 70);
        if (realm.equals(Realm.TEST)) {
            end = new Coordinate(6, 6);
        }
        return maze.findLeastBytesThatMakeMazeImpassible(start, end);

    }
}