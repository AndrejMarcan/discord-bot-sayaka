package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.service.skeleton.MoviesAndSeriesChannelService;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import com.yatensoft.dcbot.util.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class MoviesAndSeriesChannelServiceImpl implements MoviesAndSeriesChannelService {
    private final UrlArchiveService urlArchiveService;

    public MoviesAndSeriesChannelServiceImpl(@Autowired final UrlArchiveService urlArchiveService) {
        super();
        this.urlArchiveService = urlArchiveService;
    }

    @Override
    public void handleMessageEvent(final MessageReceivedEvent messageEvent) {
        /* Check if message contains URLs */
        final List<String> extractedUrls = BotUtils.collectUrlsFromText(messageEvent.getMessage());
        if(!CollectionUtils.isEmpty(extractedUrls)){
            // store those URLs if they do not exist
            // post them to rec-movie-and-series channel
        }
    }
}
