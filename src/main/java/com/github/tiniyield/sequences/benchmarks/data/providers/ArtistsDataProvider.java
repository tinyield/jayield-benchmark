package com.github.tiniyield.sequences.benchmarks.data.providers;

import static com.github.tiniyield.sequences.benchmarks.common.SequenceBenchmarkConstants.SILENT;

import java.util.HashMap;
import java.util.Map;

import com.github.tiniyield.sequences.benchmarks.data.loader.FileLoader;
import com.github.tiniyield.sequences.benchmarks.model.artist.Artist;

public class ArtistsDataProvider extends AbstractCountryBasedDataProvider<Artist> {
    private final Map<String, Artist[]> data;

    public ArtistsDataProvider(CountriesDataProvider countries) {
        data = new HashMap<>();
        countries.asStream().forEach(country -> {
            String name = country.getName();
            Artist[] countryArtists = new Artist[0];
            try {
                countryArtists = new FileLoader().loadArtists(name);
            } catch (RuntimeException e) {
                if (!SILENT) {
                    System.out.println(String.format("data for country %s was ignored.", name));
                }
            }
            data.put(name, countryArtists);
        });

    }

    @Override
    protected Artist[] data(String country) {
        return this.data.computeIfAbsent(country, (name) -> new Artist[0]);
    }
}
