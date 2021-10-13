package ru.example.letterflow.service;

import com.google.api.client.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class YouTubeService {

    @Value("API_KEY")
    private final String apiKey;

    public YouTubeService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Transactional
    public String getLinkChannel(Long channelId, String apiKey){
        Json result = null;
        return null;
    }

    @Transactional
    public String getLinkVideo(Long videoId, String apiKey){
        return null;
    }

    @Transactional
    public String getNumberWatchers(Long videoId, String apiKey){
        return null;
    }

    @Transactional
    public String getNumberLikes(Long videoId, String apiKey){
        return null;
    }
}
