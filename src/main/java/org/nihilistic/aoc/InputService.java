package org.nihilistic.aoc;

import java.io.IOException;
import java.util.Optional;

public interface InputService {
    public String getInput(Integer day, Realm realm) throws IOException;
    public String getInput(Integer day, Realm realm, Optional<String> override) throws IOException;
}
