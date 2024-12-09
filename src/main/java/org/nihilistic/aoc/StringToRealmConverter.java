package org.nihilistic.aoc;

import org.springframework.core.convert.converter.Converter;

public class StringToRealmConverter implements Converter<String, Realm>{

    @Override
    public Realm convert(String source) {
        return Realm.valueOf(source.toUpperCase());
    }
}
