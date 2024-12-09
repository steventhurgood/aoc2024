package org.nihilistic.aoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class InputServiceImpl implements InputService {
    @Autowired
    ResourceLoader loader;

    @Override
    public String getInput(Integer day, Realm realm, Optional<String> override) throws IOException {
        if (override.isPresent()) {
            return override.get();
        }
        String resourcePath = String.format("classpath:static/day%d/%s.txt", day, realm.toString().toLowerCase());
        Resource resource = loader.getResource(resourcePath);
        var input = resource.getContentAsString(StandardCharsets.UTF_8);
        return input;
    }

    @Override
    public String getInput(Integer day, Realm realm) throws IOException {
        return getInput(day, realm, null);
    }
}
