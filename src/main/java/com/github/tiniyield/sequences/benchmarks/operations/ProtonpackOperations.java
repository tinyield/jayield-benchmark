package com.github.tiniyield.sequences.benchmarks.operations;

import com.github.tiniyield.sequences.benchmarks.operations.common.SequenceBenchmarkUtils;
import com.github.tiniyield.sequences.benchmarks.operations.model.artist.Artist;
import com.github.tiniyield.sequences.benchmarks.operations.model.country.Country;
import com.github.tiniyield.sequences.benchmarks.operations.model.track.Track;
import com.github.tiniyield.sequences.benchmarks.operations.model.wrapper.Value;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.zip;
import static com.github.tiniyield.sequences.benchmarks.operations.common.SequenceBenchmarkUtils.distinctByKey;
import static com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkStreamUtils.TO_ARTISTS_IN_TOP_TEN_WITH_SONGS_IN_TOP_TEN_BY_COUNTRY;
import static com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkStreamUtils.TO_DATA_TRIPLET_BY_COUNTRY;
import static com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkStreamUtils.TO_TOP_BY_COUNTRY_TRIPLET;

public class ProtonpackOperations {

    public Stream<Triplet<Country, Artist, Track>> zipTopArtistAndTrackByCountry(Stream<Pair<Country, Stream<Artist>>> artists,
                                                                                 Stream<Pair<Country, Stream<Track>>> tracks) {
        return zip(artists, tracks, TO_TOP_BY_COUNTRY_TRIPLET).filter(distinctByKey(Triplet::getValue1));
    }

    public Stream<Pair<Country, List<Artist>>> artistsInTopTenWithTopTenTracksByCountry(Stream<Pair<Country,
            Stream<Artist>>> artists, Stream<Pair<Country, Stream<Track>>> tracks) {
        return zip(artists, tracks, TO_DATA_TRIPLET_BY_COUNTRY)
                .map(TO_ARTISTS_IN_TOP_TEN_WITH_SONGS_IN_TOP_TEN_BY_COUNTRY);
    }

    public Stream<Pair<Integer, Value>> zipPrimeWithValue(Stream<Integer> numbers, Stream<Value> values) {
        return zip(numbers.filter(SequenceBenchmarkUtils::isPrime), values, Pair::with);
    }

}
