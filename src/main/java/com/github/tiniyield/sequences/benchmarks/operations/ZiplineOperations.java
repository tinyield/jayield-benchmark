package com.github.tiniyield.sequences.benchmarks.operations;

import com.github.tiniyield.sequences.benchmarks.operations.common.SequenceBenchmarkUtils;
import com.github.tiniyield.sequences.benchmarks.operations.model.artist.Artist;
import com.github.tiniyield.sequences.benchmarks.operations.model.country.Country;
import com.github.tiniyield.sequences.benchmarks.operations.model.track.Track;
import com.github.tiniyield.sequences.benchmarks.operations.model.wrapper.Value;
import com.github.tiniyield.sequences.benchmarks.operations.utils.SequenceBenchmarkStreamUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class ZiplineOperations {

    public Stream<Triplet<Country, Artist, Track>> zipTopArtistAndTrackByCountry(
            Stream<Pair<Country, Stream<Artist>>> artists,
            Stream<Pair<Country, Stream<Track>>> tracks) {

        Iterator<Pair<Country, Stream<Artist>>> iter = artists.iterator();
        return tracks.map(r -> {
            Track track = r.getValue1().findFirst().orElse(null);
            Pair<Country, Stream<Artist>> l = iter.next();
            return Triplet.with(l.getValue0(), l.getValue1().iterator().next(), track);
        }).filter(SequenceBenchmarkUtils.distinctByKey(Triplet::getValue1));
    }

    public Stream<Pair<Country, List<Artist>>> artistsInTopTenWithTopTenTracksByCountry(
            Stream<Pair<Country, Stream<Artist>>> artists,
            Stream<Pair<Country, Stream<Track>>> tracks) {

        Iterator<Pair<Country, Stream<Artist>>> iter = artists.iterator();
        return tracks.map(r -> {
            Pair<Country, Stream<Artist>> l = iter.next();
            return Triplet.with(l.getValue0(),
                                l.getValue1(),
                                r.getValue1());
        }).map(SequenceBenchmarkStreamUtils.TO_ARTISTS_IN_TOP_TEN_WITH_SONGS_IN_TOP_TEN_BY_COUNTRY);
    }

    public Stream<Pair<Integer, Value>> zipPrimeWithValue(Stream<Integer> numbers, Stream<Value> values) {
        Iterator<Value> iter = values.iterator();
        return numbers.filter(SequenceBenchmarkUtils::isPrime).map(v -> Pair.with(v, iter.next()));
    }

}
