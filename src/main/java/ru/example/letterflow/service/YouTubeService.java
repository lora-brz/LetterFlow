package ru.example.letterflow.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.VideoListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;

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
    public String getChannelId(String channelName) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();
        YouTube.Search.List request = youtubeService.search()
                .list("snippet");
        SearchListResponse response = request.setKey(apiKey)
                .setQ(channelName)
                .execute();
        return response.getItems().get(0).getId().getChannelId();
    }

    @Transactional
    public String getVideoId(String videoName) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();
        YouTube.Search.List request = youtubeService.search()
                .list("snippet");
        SearchListResponse response = request.setKey(apiKey)
                .setMaxResults(1L)
                .setQ(videoName)
                .execute();
        return response.getItems().get(0).getId().getVideoId();
    }

    @Transactional
    public String getNumberWatchers(String videoName) throws GeneralSecurityException, IOException {
        String videoId = getVideoId(videoName);
        return getVideoList(videoId).getItems().get(0).getStatistics().getViewCount().toString();
    }

    @Transactional
    public String getNumberLikes(String videoName) throws GeneralSecurityException, IOException {
        String videoId = getVideoId(videoName);
        return getVideoList(videoId).getItems().get(0).getStatistics().getLikeCount().toString();
    }

    @Transactional
    public List<String> getVideoFromChannel(String channelName) throws GeneralSecurityException, IOException {
        String channelId = getChannelId(channelName);
        YouTube youtubeService = getService();
        YouTube.Search.List request = youtubeService.search()
                .list("snippet");
        SearchListResponse response = request
                .setKey(apiKey)
                .setChannelId(channelId)
                .setMaxResults(5L)
                .setType("video")
                .execute();
        List<String> listLinksVideos = new LinkedList<String>();
        listLinksVideos.add(channelName);
        List<SearchResult> arrInfoVideo = response.getItems();
        for(int i=0; i<arrInfoVideo.size();i++){
            listLinksVideos.add(arrInfoVideo.get(i).getId().getVideoId());
        }
        return listLinksVideos;
    }

    @Transactional
    public List<String> findVideoCommentRanom(String videoName) throws GeneralSecurityException,
            IOException {
        String videoId = getVideoId(videoName);
        YouTube youtubeService = getService();
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet");
        CommentThreadListResponse response = request
                .setKey(apiKey)
                .setVideoId(videoId)
                .setMaxResults(50L)
                .execute();
        Integer randomNum = (int) (Math.random() * 50);
        String authorDisplayName =
                response.getItems().get(randomNum).getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName();
        String textOriginal =
                response.getItems().get(randomNum).getSnippet().getTopLevelComment().getSnippet().getTextOriginal();
        List<String> listRandomComment = new LinkedList<String>();
        listRandomComment.add(authorDisplayName);
        listRandomComment.add(textOriginal);
        return listRandomComment;
    }


    public VideoListResponse getVideoList(String videoId) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        YouTube youtubeService = new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
        YouTube.Videos.List request = youtubeService.videos().list("statistics");
        VideoListResponse response = request.setKey(apiKey)
                .setId(videoId)
                .execute();
        return response;
    }
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
