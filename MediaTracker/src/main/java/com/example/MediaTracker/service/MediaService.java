package com.example.MediaTracker.service;


import com.example.MediaTracker.model.Media;
import com.example.MediaTracker.repository.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final WebClient webClient;

    private final String TMDB_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWQ3ZWQ5NmU3ODM1Y2M1ZjhiNTRjNTQzNDcxZDBkOSIsIm5iZiI6MTc3NTExNDc5MC4zNTY5OTk5LCJzdWIiOiI2OWNlMWEyNjg1ZmQ4YTQzNDY0ZDFhMGMiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.oRPnnLD-Id_ZLe63JB3scwvszinqBKhjQQ9QiyoPdhM";

    public MediaService (MediaRepository mediaRepository, WebClient.Builder webClientBuilder) {
        this.mediaRepository = mediaRepository;
        this.webClient = webClientBuilder.baseUrl("https://api.themoviedb.org/3").build();
    }

    public void fetchAndSaveMovies(String genreId) {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("with_genres", genreId)
                        .build())
                .header("Authorization", "Bearer " + TMDB_TOKEN.trim()) // Spațiul de aici e critic!
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (List<Map<String, Object>>) response.get("results"))
                .subscribe(movies -> {
                    for (Map<String, Object> movieData : movies) {
                        String title = (String) movieData.get("title");

                        if (!mediaRepository.existsBytitle(title)) {
                            Media media = new Media();
                            media.setTitle((String) movieData.get("title"));
                            media.setType("Movie");
                            media.setStatus("Recommended");
                            // Corectat: average (cu e)
                            media.setRating(((Number) movieData.get("vote_average")).intValue());

                            mediaRepository.save(media);
                        } else {
                            System.out.println(">>> Skipping duplicate: " + title);
                        }
                    }
                });
    }
}
