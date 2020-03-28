package com.github.tiniyield.sequences.benchmarks.common;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.tiniyield.sequences.benchmarks.zip.sequence.benchmark.operations.QueryBenchmark;
import com.github.tiniyield.sequences.benchmarks.zip.sequence.benchmark.operations.JoolBenchmark;
import com.github.tiniyield.sequences.benchmarks.zip.sequence.benchmark.operations.StreamExBenchmark;
import com.github.tiniyield.sequences.benchmarks.zip.sequence.benchmark.operations.GuavaBenchmark;
import com.github.tiniyield.sequences.benchmarks.zip.sequence.benchmark.operations.ProtonpackBenchmark;
import com.github.tiniyield.sequences.benchmarks.zip.sequence.benchmark.operations.StreamBenchmark;
import com.github.tiniyield.sequences.benchmarks.zip.sequence.benchmark.operations.ZiplineBenchmark;

public class SequenceBenchmarkUtils {

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static void assertZipTopArtistAndTrackByCountryBenchmarkValidity() {
        assertSameResults(
                QueryBenchmark.zipTopArtistAndTrackByCountry().toList(),

                StreamBenchmark.zipTopArtistAndTrackByCountry().collect(Collectors.toList()),

                ProtonpackBenchmark.zipTopArtistAndTrackByCountry().collect(Collectors.toList()),

                GuavaBenchmark.zipTopArtistAndTrackByCountry().collect(Collectors.toList()),

                ZiplineBenchmark.zipTopArtistAndTrackByCountry().collect(Collectors.toList()),

                StreamExBenchmark.zipTopArtistAndTrackByCountry().collect(Collectors.toList()),

                JoolBenchmark.zipTopArtistAndTrackByCountry().toList()

        );
    }

    public static void assertArtistsInTopTenWithTopTenTracksByCountryBenchmarkValidity() {
        assertSameResults(
                QueryBenchmark.artistsInTopTenWithTopTenTracksByCountry()
                              .toList(),

                StreamBenchmark.artistsInTopTenWithTopTenTracksByCountry()
                               .collect(Collectors.toList()),

                ProtonpackBenchmark.artistsInTopTenWithTopTenTracksByCountry()
                                   .collect(Collectors.toList()),

                GuavaBenchmark.artistsInTopTenWithTopTenTracksByCountry()
                              .collect(Collectors.toList()),

                ZiplineBenchmark.artistsInTopTenWithTopTenTracksByCountry()
                                .collect(Collectors.toList()),

                StreamExBenchmark.artistsInTopTenWithTopTenTracksByCountry().collect(Collectors.toList()),

                JoolBenchmark.artistsInTopTenWithTopTenTracksByCountry().toList()
        );
    }

    public static void assertZipPrimeWithValueValidity() {
        assertSameResults(
                QueryBenchmark.zipPrimeWithValue()
                              .toList(),

                StreamBenchmark.zipPrimeWithValue()
                               .collect(Collectors.toList()),

                ProtonpackBenchmark.zipPrimeWithValue()
                                   .collect(Collectors.toList()),

                GuavaBenchmark.zipPrimeWithValue()
                              .collect(Collectors.toList()),

                ZiplineBenchmark.zipPrimeWithValue()
                                .collect(Collectors.toList()),

                StreamExBenchmark.zipPrimeWithValue().collect(Collectors.toList()),

                JoolBenchmark.zipPrimeWithValue().toList()
        );
    }

    private static <T> void assertSameResults(List<T>... results) {
        if (results.length < 1) {
            throw new RuntimeException("Insufficient results");
        }
        List<T> first = results[0];
        List<List<T>> otherResults = Arrays.asList(Arrays.copyOfRange(results, 1, results.length));
        if(first.size() < 1) {
            throw new RuntimeException("results are empty");
        }
        boolean sameResultSizes = otherResults.stream()
                                              .map(List::size)
                                              .allMatch(size -> first.size() == size);
        if (!sameResultSizes) {
            throw new RuntimeException("query results do not have the same size");
        }

        boolean sameElements = otherResults.stream().allMatch(result -> result.containsAll(first));

        if (!sameElements) {
            throw new RuntimeException("query results do not have the same elements");
        }

    }
}