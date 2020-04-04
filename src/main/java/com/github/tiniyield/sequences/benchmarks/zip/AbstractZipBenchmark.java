package com.github.tiniyield.sequences.benchmarks.zip;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.jayield.Query;
import org.jooq.lambda.Seq;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import com.github.tiniyield.sequences.benchmarks.AbstractZipOperationsBenchmark;
import com.github.tiniyield.sequences.benchmarks.IZipBenchmark;

import one.util.streamex.StreamEx;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public abstract class AbstractZipBenchmark<T> extends AbstractZipOperationsBenchmark implements IZipBenchmark {

    protected abstract io.vavr.collection.Stream<T> getVavr();
    protected abstract StreamEx<T> getStreamEx();
    protected abstract Stream<T> getZipline();
    protected abstract Stream<T> getProtonpack();
    protected abstract Stream<T> getStream();
    protected abstract Query<T> getQuery();
    protected abstract Stream<T> getGuava();
    protected abstract Seq<T> getJool();
    protected abstract void init();

    @Setup
    public void setup() {
        super.init();
        this.init();
    }

    @Override
    @Benchmark
    public final void stream(Blackhole bh) { // With Auxiliary Function
        getStream().forEach(bh::consume);
    }

    @Override
    @Benchmark
    public final void streamEx(Blackhole bh) {
        getStreamEx().forEach(bh::consume);
    }

    @Override
    @Benchmark
    public final void jayield(Blackhole bh) {
        getQuery().traverse(bh::consume);
    }

    @Override
    @Benchmark
    public final void jool(Blackhole bh) {
        getJool().forEach(bh::consume);
    }

    // Other Sequences based benchmarks

    @Benchmark
    public final void vavr(Blackhole bh) {
        getVavr().forEach(bh::consume);
    }

    @Override
    @Benchmark
    public final void protonpack(Blackhole bh) {
        getProtonpack().forEach(bh::consume);
    }

    @Override
    @Benchmark
    public final void guava(Blackhole bh) {
        getGuava().forEach(bh::consume);
    }

    @Override
    @Benchmark
    public final void zipline(Blackhole bh) {
        getZipline().forEach(bh::consume);
    }
}
