/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl;

import com.yatensoft.dcbot.config.SayakaConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.service.skeleton.MusicChannelService;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import com.yatensoft.dcbot.util.BotUtils;
import java.util.List;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Implementation of MusicChannelService interface responsible for handling operations in #music.
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
        final List<UrlArchiveDTO> newUrls = getUrlArchiveRecordsToSave(extractedUrls).parallelStream()
                .filter(record -> !urlArchiveService.checkIfUrlArchiveRecordExists(
                        record.getUrl(), TopicEnum.COMMON, ArchiveTypeEnum.MUSIC))
                .toList();
        /* If new URLs are present store them in DB and post a message in recommendation channel */
        if (!CollectionUtils.isEmpty(newUrls)) {
            urlArchiveService.storeUrlArchiveRecords(newUrls);
            SayakaConfig.getSayaka()
                    .getChannelById(TextChannel.class, ChannelConstant.RECOMMENDED_MUSIC_CHANNEL)
                    .sendMessage(createMessage(newUrls, messageEvent))
                    .queue();
        }
    }

    /** Prepare list of UrlArchive objects for recommended music. */
    private List<UrlArchiveDTO> getUrlArchiveRecordsToSave(final List<String> urls) {
        return urls.stream()
                .map(url -> new UrlArchiveDTO.Builder()
                        .url(url)
                        .type(ArchiveTypeEnum.MUSIC)
                        .topic(TopicEnum.COMMON)
                        .build())
                .toList();
    }

    /** Build a message for recommended music channel. */
    private String createMessage(final List<UrlArchiveDTO> records, final MessageReceivedEvent messageEvent) {
        final List<String> urls =
                records.stream().map(record -> record.getUrl()).toList();
        StringBuilder stringbuilder = new StringBuilder(String.format(
                MessageConstant.POST_NEW_RECOMMENDATIONS_WITH_CREDITS,
                messageEvent.getAuthor().getAsMention()));
        for (String url : urls) {
            stringbuilder.append(url);
            stringbuilder.append('\n');
        }
        return stringbuilder.toString();
    }
}
