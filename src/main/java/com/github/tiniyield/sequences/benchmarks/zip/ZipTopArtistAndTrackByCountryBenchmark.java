package com.github.tiniyield.sequences.benchmarks.zip;

import static com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkStreamUtils.getArtists;
import static com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkStreamUtils.getTracks;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.javatuples.Triplet;
import org.jayield.Query;
import org.jooq.lambda.Seq;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.github.tiniyield.sequences.benchmarks.operations.ZiplineOperations;
import com.github.tiniyield.sequences.benchmarks.operations.common.SequenceBenchmarkUtils;
import com.github.tiniyield.sequences.benchmarks.operations.model.artist.Artist;
import com.github.tiniyield.sequences.benchmarks.operations.model.country.Country;
import com.github.tiniyield.sequences.benchmarks.operations.model.track.Track;
import com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkJoolUtils;
import com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkQueryUtils;
import com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkStreamExUtils;
import com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkStreamUtils;
import com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkVavrUtils;

import one.util.streamex.StreamEx;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class ZipTopArtistAndTrackByCountryBenchmark extends AbstractZipBenchmark<Triplet<Country, Artist, Track>> {

    @Setup
    public void init() {
        SequenceBenchmarkUtils.assertZipTopArtistAndTrackByCountryBenchmarkValidity(getGuava(),
                                                                                    getJool(),
                                                                                    getQuery(),
                                                                                    getStream(),
                                                                                    getProtonpack(),
                                                                                    getZipline(),
                                                                                    getStreamEx(),
                                                                                    getVavr());
    }

    protected io.vavr.collection.Stream<Triplet<Country, Artist, Track>> getVavr() {
        return vavr.zipTopArtistAndTrackByCountry(SequenceBenchmarkVavrUtils.getArtists(),
                                                            SequenceBenchmarkVavrUtils.getTracks());
    }

    protected StreamEx<Triplet<Country, Artist, Track>> getStreamEx() {
        return streamEx.zipTopArtistAndTrackByCountry(SequenceBenchmarkStreamExUtils.getArtists(),
                                                                SequenceBenchmarkStreamExUtils.getTracks());
    }

    protected Stream<Triplet<Country, Artist, Track>> getZipline() {
        return zipline.zipTopArtistAndTrackByCountry(getArtists(), getTracks());
    }

    protected Stream<Triplet<Country, Artist, Track>> getProtonpack() {
        return protonpack.zipTopArtistAndTrackByCountry(SequenceBenchmarkStreamUtils.getArtists(),
                                                                  SequenceBenchmarkStreamUtils.getTracks());
    }

    protected Stream<Triplet<Country, Artist, Track>> getStream() {
        return stream.zipTopArtistAndTrackByCountry(getArtists(), getTracks());
    }

    protected Query<Triplet<Country, Artist, Track>> getQuery() {
        return query.zipTopArtistAndTrackByCountry(SequenceBenchmarkQueryUtils.getArtists(),
                                                             SequenceBenchmarkQueryUtils.getTracks());
    }

    protected Seq<Triplet<Country, Artist, Track>> getJool() {
        return jool.zipTopArtistAndTrackByCountry(
                SequenceBenchmarkJoolUtils.getArtists(),
                SequenceBenchmarkJoolUtils.getTracks()
        );
    }

    protected Stream<Triplet<Country, Artist, Track>> getGuava() {
        return guava.zipTopArtistAndTrackByCountry(
                SequenceBenchmarkStreamUtils.getArtists(),
                SequenceBenchmarkStreamUtils.getTracks()
        );
    }
}
