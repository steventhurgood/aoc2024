package org.nihilistic.aoc.day2;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;

public record Reports(List<Report> reports) {
    static Logger logger = LoggerFactory.getLogger(Reports.class);

    public static Reports fromString(String input) {
        var lines = new BufferedReader(new StringReader(input));
        var reports = lines.lines().map(Report::fromString).collect(Collectors.toList());
        return new Reports(reports);
    }

    public Long countSafe() {
        var safeCount = reports.stream().peek(r -> logger.info("report: " + r)).filter(Report::safe)
                .peek(r -> logger.info("  is safe")).collect(Collectors.counting());
        return safeCount;
    }

    public Long countSafeVariants() {
        var safeCount = reports.stream().filter(Report::hasSafeVariant).collect(Collectors.counting());
        return safeCount;
    }
}
