package com.github.tiniyield.sequences.benchmarks.operations;


import static com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkJoolUtils.TO_ARTISTS_IN_TOP_TEN_WITH_SONGS_IN_TOP_TEN_BY_COUNTRY;
import static com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkJoolUtils.TO_DATA_TRIPLET_BY_COUNTRY;
import static com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkJoolUtils.TO_TOP_BY_COUNTRY_TRIPLET;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.jooq.lambda.Seq;

import com.github.tiniyield.sequences.benchmarks.operations.common.SequenceBenchmarkUtils;
import com.github.tiniyield.sequences.benchmarks.operations.model.artist.Artist;
import com.github.tiniyield.sequences.benchmarks.operations.model.country.Country;
import com.github.tiniyield.sequences.benchmarks.operations.model.track.Track;
import com.github.tiniyield.sequences.benchmarks.operations.model.wrapper.Value;

public class JoolOperations {

    public Seq<Triplet<Country, Artist, Track>> zipTopArtistAndTrackByCountry(Seq<Pair<Country, Seq<Artist>>> artists,
                                                                              Seq<Pair<Country, Seq<Track>>> tracks) {
        return artists.zip(tracks).map(TO_TOP_BY_COUNTRY_TRIPLET).distinct(Triplet::getValue1);
    }

    public Seq<Pair<Country, List<Artist>>> artistsInTopTenWithTopTenTracksByCountry(Seq<Pair<Country, Seq<Artist>>> artists,
                                                                                     Seq<Pair<Country, Seq<Track>>> tracks) {
        return artists.zip(tracks)
                      .map(TO_DATA_TRIPLET_BY_COUNTRY)
                      .map(TO_ARTISTS_IN_TOP_TEN_WITH_SONGS_IN_TOP_TEN_BY_COUNTRY);
    }


    public Seq<Pair<Integer, Value>> zipPrimeWithValue(Seq<Integer> numbers, Seq<Value> values) {
        return numbers.filter(SequenceBenchmarkUtils::isPrime).zip(values, Pair::with);
    }

    public boolean isEveryEven(Seq<Integer> numbers) {
        return numbers.allMatch(SequenceBenchmarkUtils::isEven);
    }

    public Optional<Integer> findFirst(Seq<Integer> numbers) {
        return numbers.filter(SequenceBenchmarkUtils::isOdd).findFirst();
    }


    public <T, U> boolean every(Seq<T> q1, Seq<U> q2, BiPredicate<T, U> predicate) {
        return q1.zip(q2, predicate::test).allMatch(Boolean.TRUE::equals);
    }

    public <T> T find(Seq<T> q1, Seq<T> q2, BiPredicate<T, T> predicate) {
        return q1.zip(q2, (t1, t2) -> predicate.test(t1, t2) ? t1 : null)
                 .filter(Objects::nonNull)
                 .findFirst()
                 .orElse(null);
    }

    public Integer flatMapAndReduce(Seq<Seq<Integer>> input) {
        return input.flatMap(i -> i).reduce(Integer::sum).orElseThrow(RuntimeException::new);
    }
}
