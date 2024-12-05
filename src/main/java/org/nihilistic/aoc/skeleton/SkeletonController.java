package org.nihilistic.aoc.skeleton;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkeletonController {
    Logger logger = LoggerFactory.getLogger(SkeletonController.class);

    @Autowired
    SkeletonService skeletonService;

    @Value("classpath:static/skeleton/skeleton_test.txt")
    Resource testResource;

    @Value("classpath:static/skeleton/skeleton.txt")
    Resource inputResource;

    @Async
    @GetMapping("/skeleton/part1/{release}")
    public CompletableFuture<Integer> execute1(@PathVariable String release) throws IOException {
        var resource = (release.equals("prod") ? inputResource : testResource);
        var input = resource.getContentAsString(StandardCharsets.UTF_8);
        return skeletonService.execute(input);
    }

    @Async
    @GetMapping("/skeleton/part2/{release}")
    public CompletableFuture<Integer> execute2(@PathVariable String release) throws IOException {
        var resource = (release.equals("prod") ? inputResource : testResource);
        var input = resource.getContentAsString(StandardCharsets.UTF_8);
        return skeletonService.execute(input);
    }
}
