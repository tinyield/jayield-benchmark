package com.github.tiniyield.sequences.benchmarks.data.providers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.jayield.Query;
import org.jooq.lambda.Seq;

import one.util.streamex.StreamEx;

public abstract class AbstractCountryBasedDataProvider<T> implements ICountryBasedDataProvider<T> {

    protected abstract T[] data(String country);

    @Override
    public boolean hasDataForCountry(String country) {
        return data(country).length > 0;
    }

    @Override
    public List<T> asList(String country) {
        return Arrays.asList(data(country));
    }

    @Override
    public Stream<T> asStream(String country) {
        return Stream.of(data(country));
    }

    @Override
    public StreamEx<T> asStreamEx(String country) {
        return StreamEx.of(data(country));
    }

    @Override
    public Query<T> asQuery(String country) {
        return Query.of(data(country));
    }

    @Override
    public Seq<T> asSeq(String country) {
        return Seq.of(data(country));
    }
}
