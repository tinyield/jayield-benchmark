package com.github.tiniyield.sequences.benchmarks.common.data.loader;

import com.github.tiniyield.sequences.benchmarks.common.model.ApiKey;
import com.github.tiniyield.sequences.benchmarks.common.model.artist.Artist;
import com.github.tiniyield.sequences.benchmarks.common.model.artist.TopArtistsLastFmResponse;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.String.format;

public class ArtistsLoader {

    private static final String TOP_ARTIST_BY_COUNTRY_QUERY_TEMPLATE = "method=geo.gettopartists&country=%s&api_key" +
            "=%s&format=json";
    private static final String ARTISTS_PATH_TEMPLATE = "artists/%s.json";

    public void fetch(ApiKey key, String country) {
        try (InputStream is = new URI("http",
                "ws.audioscrobbler.com",
                "/2.0/",
                format(TOP_ARTIST_BY_COUNTRY_QUERY_TEMPLATE, country, key.getKey()),
                null
        ).toURL().openStream();
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

            Gson gson = new Gson();
            TopArtistsLastFmResponse data = gson.fromJson(reader, TopArtistsLastFmResponse.class);

            if (data != null && data.getTopartists() != null && !data.getTopartists().getArtist().isEmpty()) {
                List<Artist> artists = data.getTopartists().getArtist();
                FileWriter writer = new FileWriter(format(ARTISTS_PATH_TEMPLATE, country));
                gson.toJson(artists, writer);
                writer.flush();
                writer.close();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
