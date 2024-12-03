package org.nihilistic.aoc.day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Day2Controller {
    Logger logger = LoggerFactory.getLogger(Day2Controller.class);

    @Value("classpath:static/day2/day2_test.txt")
    Resource testResource;

    @Value("classpath:static/day2/day2.txt")
    Resource inputResource;

    @GetMapping("/day2/part1")
    public Long execute1() throws IOException {
        var input = inputResource.getContentAsString(StandardCharsets.UTF_8);
        var reports = Reports.fromString(input);
        logger.info(reports.toString());

        var safeCount = reports.countSafe();
        return safeCount;
    }

    @GetMapping("/day2/part2")
    public Long execute2() throws IOException {
        var input = inputResource.getContentAsString(StandardCharsets.UTF_8);
        var reports = Reports.fromString(input);
        logger.info(reports.toString());
        /*
         * reports.reports().forEach(
         * r -> {
         * logger.info("report {} has safe variant: {}", r, r.hasSafeVariant());
         * r.variants().forEach(
         * v -> logger.info("Report {} has variant {} (safe: {})", r, v, v.safe()));
         * }
         * 
         * );
         */
        var safeCount = reports.countSafeVariants();
        return safeCount;
    }
}
