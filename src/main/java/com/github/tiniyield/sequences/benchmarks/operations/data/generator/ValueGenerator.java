package com.github.tiniyield.sequences.benchmarks.operations.data.generator;

import java.util.stream.Stream;

import com.github.tiniyield.sequences.benchmarks.operations.model.wrapper.Value;

public class ValueGenerator {

    public static Value[] get(int size) {
        return Stream.of(IntegerArrayGenerator.get(size))
                     .map(Value::new)
                     .toArray(Value[]::new);
    }
}