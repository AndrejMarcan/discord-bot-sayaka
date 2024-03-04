package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.persitence.entity.UrlArchive;
import com.yatensoft.dcbot.service.skeleton.MusicChannelService;
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

/**
 * Implementation of MusicChannelService interface responsible for handling operations in #music
 */
@Service
public class MusicChannelServiceImpl implements MusicChannelService {
    private final UrlArchiveService urlArchiveService;

    public MusicChannelServiceImpl(@Autowired final UrlArchiveService urlArchiveService) {
        super();
        this.urlArchiveService = urlArchiveService;
    }

    /** See {@link MusicChannelService#handleMessageEvent(MessageReceivedEvent)} */
    @Override
    public void handleMessageEvent(final MessageReceivedEvent messageEvent) {
        /* Check if message contains URLs */
        final List<String> extractedUrls = BotUtils.collectUrlsFromText(messageEvent.getMessage());
        final List<UrlArchive> newUrls = getUrlArchiveRecordsToSave(extractedUrls).parallelStream().filter(record -> !urlArchiveService.checkIfUrlArchiveRecordExists(record.getUrl(), TopicEnum.COMMON, ArchiveTypeEnum.MUSIC)).toList();
        /* If new URLs are present store them in DB and post a message in recommendation channel */
        if(!CollectionUtils.isEmpty(newUrls)){
            urlArchiveService.storeUrlArchiveRecords(newUrls);
            DiscordBotConfig.getBotJDA().getChannelById(TextChannel.class, ChannelConstant.RECOMMENDED_MUSIC_CHANNEL)
                    .sendMessage(createMessage(newUrls, messageEvent)).queue();
        }
    }

    /** Prepare list of UrlArchive objects for recommended music */
    private List<UrlArchive> getUrlArchiveRecordsToSave(final List<String> urls) {
        return urls.stream().map(url -> createUrlArchiveRecordRequest(url)).toList();
    }

    /** Create UrlArchive object for recommended music */
    private UrlArchive createUrlArchiveRecordRequest(final String url) {
        UrlArchive urlArchive = new UrlArchive();
        urlArchive.setUrl(url);
        urlArchive.setDateOfCreation(Date.from(Instant.now()));
        urlArchive.setType(ArchiveTypeEnum.MUSIC.getValue());
        urlArchive.setTopic(TopicEnum.COMMON.getShortName());
        return urlArchive;
    }

    /** Build a message for recommended music channel */
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
