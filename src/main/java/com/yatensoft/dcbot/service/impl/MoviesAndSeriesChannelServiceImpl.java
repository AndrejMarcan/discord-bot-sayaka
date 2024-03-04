package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.persitence.entity.UrlArchive;
import com.yatensoft.dcbot.service.skeleton.MoviesAndSeriesChannelService;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import com.yatensoft.dcbot.util.BotUtils;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
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
            /* Remove already existing urls from request list */
            final List<UrlArchive> recordsToSave = getUrlArchiveRecordsToSave(extractedUrls).parallelStream().filter(record -> !urlArchiveService.checkIfUrlArchiveRecordExists(record.getUrl(), TopicEnum.COMMON, ArchiveTypeEnum.VIDEO)).toList();
            urlArchiveService.storeUrlArchiveRecords(recordsToSave);

            // post them to rec-movie-and-series channel
            DiscordBotConfig.getBotJDA().getChannelById(TextChannel.class, ChannelConstant.RECOMMENDED_MOVIES_AND_SERIES_CHANNEL).sendMessage(createMessage(extractedUrls)).queue();
        }
    }

    private List<UrlArchive> getUrlArchiveRecordsToSave(final List<String> urls) {
        return urls.stream().map(url -> createUrlArchiveRecordRequest(url)).toList();
    }

    private UrlArchive createUrlArchiveRecordRequest(final String url) {
        UrlArchive urlArchive = new UrlArchive();
        urlArchive.setUrl(url);
        urlArchive.setDateOfCreation(Date.from(Instant.now()));
        urlArchive.setType(ArchiveTypeEnum.VIDEO.getValue());
        urlArchive.setTopic(TopicEnum.COMMON.getShortName());
        return urlArchive;
    }

    private String createMessage(final List<String> urls) {
        StringBuilder stringbuilder = new StringBuilder();
        for (String url: urls) {
            stringbuilder.append(url);
            stringbuilder.append("\\n");
        }
        return stringbuilder.toString();
    }
}
