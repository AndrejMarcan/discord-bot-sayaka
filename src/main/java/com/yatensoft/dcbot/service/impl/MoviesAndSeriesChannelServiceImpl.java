package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.persitence.entity.UrlArchive;
import com.yatensoft.dcbot.service.skeleton.MoviesAndSeriesChannelService;
import com.yatensoft.dcbot.service.skeleton.MusicChannelService;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import com.yatensoft.dcbot.util.BotUtils;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of MoviesAndSeriesChannelService interface responsible for handling operations in
 * #movies-and-series channel.
 */
@Service
public class MoviesAndSeriesChannelServiceImpl implements MoviesAndSeriesChannelService {
    private final UrlArchiveService urlArchiveService;

    public MoviesAndSeriesChannelServiceImpl(@Autowired final UrlArchiveService urlArchiveService) {
        super();
        this.urlArchiveService = urlArchiveService;
    }

    /** See {@link MoviesAndSeriesChannelService#handleMessageEvent(MessageReceivedEvent)} */
    @Override
    public void handleMessageEvent(final MessageReceivedEvent messageEvent) {
        /* Check if message contains URLs */
        final List<String> extractedUrls = BotUtils.collectUrlsFromText(messageEvent.getMessage());
        final List<UrlArchive> newUrls = getUrlArchiveRecordsToSave(extractedUrls).parallelStream().filter(record -> !urlArchiveService.checkIfUrlArchiveRecordExists(record.getUrl(), TopicEnum.COMMON, ArchiveTypeEnum.VIDEO)).toList();
        /* If new URLs are present store them in DB and post a message in recommendation channel */
        if(!CollectionUtils.isEmpty(newUrls)){
            urlArchiveService.storeUrlArchiveRecords(newUrls);
            DiscordBotConfig.getBotJDA().getChannelById(TextChannel.class, ChannelConstant.RECOMMENDED_MOVIES_AND_SERIES_CHANNEL)
                    .sendMessage(createMessage(newUrls, messageEvent)).queue();
        }
    }

    /** Prepare list of UrlArchive objects for recommended movie or series. */
    private List<UrlArchive> getUrlArchiveRecordsToSave(final List<String> urls) {
        return urls.stream().map(url -> createUrlArchiveRecordRequest(url)).toList();
    }

    /** Create UrlArchive object for recommended movie or series. */
    private UrlArchive createUrlArchiveRecordRequest(final String url) {
        UrlArchive urlArchive = new UrlArchive();
        urlArchive.setUrl(url);
        urlArchive.setDateOfCreation(Date.from(Instant.now()));
        urlArchive.setType(ArchiveTypeEnum.VIDEO.getValue());
        urlArchive.setTopic(TopicEnum.COMMON.getShortName());
        return urlArchive;
    }

    /** Build a message for recommended movies and series channel. */
    private String createMessage(final List<UrlArchive> records, final MessageReceivedEvent messageEvent) {
        final List<String> urls = records.stream().map(record -> record.getUrl()).toList();
        StringBuilder stringbuilder = new StringBuilder(String.format(MessageConstant.POST_NEW_RECOMMENDATIONS_WITH_CREDITS, messageEvent.getAuthor().getAsMention()));
        for (String url: urls) {
            stringbuilder.append(url);
            stringbuilder.append('\n');
        }
        return stringbuilder.toString();
    }
}
