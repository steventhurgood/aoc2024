package org.nihilistic.aoc.day1;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
    
@RestController
public class Day1Controller {

    Logger logger = LoggerFactory.getLogger(Day1Controller.class);


    @Value("classpath:static/day1/day1.txt")
    Resource testResource;

    @GetMapping("/day1/part1")
    Integer execute1() throws IOException{

        var input = testResource.getContentAsString(StandardCharsets.UTF_8);
        var pairs = LocationPairs.fromString(input);
        logger.info(pairs.toString());
        
        return pairs.totalSortedDiffernces();
    }
    @GetMapping("/day1/part2")
    Long execute2() throws IOException{

        var input = testResource.getContentAsString(StandardCharsets.UTF_8);
        var pairs = LocationPairs.fromString(input);
        logger.info(pairs.toString());
        
        return pairs.similarity();
    }
}
