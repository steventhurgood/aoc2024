package org.nihilistic.aoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloWorld {

    @Value("classpath:static/test.txt")
    private Resource testResource;

    @Autowired
    FooService fooService;

    @Async
    @GetMapping("/hello/{id}") 
        public CompletableFuture<HelloResponse> hello(@PathVariable Long id, @RequestParam("name") Optional<String> name, @RequestParam("age") Optional<Integer> age, @RequestParam("record") HelloRequest req) throws IOException{
            String responseName = name.orElse("unknown");
            Integer responseAge = age.orElse(-1);

            String text = testResource.getContentAsString(StandardCharsets.UTF_8);
            return fooService.getResponse(id, responseName + req.toString(), responseAge, text);
        }
}