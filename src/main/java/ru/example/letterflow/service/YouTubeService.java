package ru.example.letterflow.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
public class YouTubeService {

    @Value("API_KEY")
    private final String apiKey;
    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public YouTubeService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Transactional
    public String getLinkChannel(String channelId){
        return "https://www.youtube.com/channel/" + channelId;
    }

    @Transactional
    public String getLinkVideo(String videoId){
        return "https://www.youtube.com/watch?v=" + videoId;
    }

    @Transactional
    public String getNumberWatchers(String videoId) throws GeneralSecurityException, IOException {
        return getVideoList(videoId, apiKey).getItems().get(0).getStatistics().getViewCount().toString();
    }

    @Transactional
    public String getNumberLikes(String videoId) throws GeneralSecurityException, IOException {
        return getVideoList(videoId, apiKey).getItems().get(0).getStatistics().getLikeCount().toString();
    }

    public static VideoListResponse getVideoList(String videoId, String apiKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        YouTube youtubeService = new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
        YouTube.Videos.List request = youtubeService.videos().list("statistics");
        VideoListResponse response = request.setKey(apiKey).setId(videoId).execute();
        return response;
    }
}
