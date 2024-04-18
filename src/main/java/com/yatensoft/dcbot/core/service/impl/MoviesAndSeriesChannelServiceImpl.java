/** By YamiY Yaten */
package com.yatensoft.dcbot.core.service.impl;

import com.yatensoft.dcbot.core.config.SayakaConfig;
import com.yatensoft.dcbot.core.constant.MessageConstant;
import com.yatensoft.dcbot.core.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.core.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.core.enumeration.KitchenTableTCGsChannelEnum;
import com.yatensoft.dcbot.core.enumeration.SayakaManagedServerEnum;
import com.yatensoft.dcbot.core.enumeration.TopicEnum;
import com.yatensoft.dcbot.core.service.skeleton.DiscordService;
import com.yatensoft.dcbot.core.service.skeleton.MoviesAndSeriesChannelService;
import com.yatensoft.dcbot.core.service.skeleton.UrlArchiveService;
import com.yatensoft.dcbot.core.util.BotUtils;
import java.util.List;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Implementation of MoviesAndSeriesChannelService interface responsible for handling operations in
 * #movies-and-series channel.
 */
@Service
public class MoviesAndSeriesChannelServiceImpl implements MoviesAndSeriesChannelService {
    private final UrlArchiveService urlArchiveService;
    private final DiscordService discordService;

    public MoviesAndSeriesChannelServiceImpl(
            @Autowired final UrlArchiveService urlArchiveService, @Autowired final DiscordService discordService) {
        super();
        this.urlArchiveService = urlArchiveService;
        this.discordService = discordService;
    }

    /** See {@link MoviesAndSeriesChannelService#handleMessageEvent(MessageReceivedEvent)} */
    @Override
    public void handleMessageEvent(final MessageReceivedEvent messageEvent) {
        /* Check if message contains URLs */
        final List<String> extractedUrls = BotUtils.collectUrlsFromText(messageEvent.getMessage());
        final List<UrlArchiveDTO> newUrls = getUrlArchiveRecordsToSave(extractedUrls).parallelStream()
                .filter(record -> !urlArchiveService.checkIfUrlArchiveRecordExists(
                        record.getUrl(), TopicEnum.COMMON, ArchiveTypeEnum.VIDEO))
                .toList();
        /* If new URLs are present store them in DB and post a message in recommendation channel */
        if (!CollectionUtils.isEmpty(newUrls)) {
            urlArchiveService.storeUrlArchiveRecords(newUrls);
            SayakaConfig.getSayaka()
                    .getTextChannelById(discordService.getChannelIdByServerAndChannelKey(
                            SayakaManagedServerEnum.KITCHEN_TABLE_TCGS,
                            KitchenTableTCGsChannelEnum.ENTERTAINMENT_REC_MOVIES_AND_SERIES.getChannelKey()))
                    .sendMessage(createMessage(newUrls, messageEvent))
                    .queue();
        }
    }

    /** Prepare list of UrlArchive objects for recommended movie or series. */
    private List<UrlArchiveDTO> getUrlArchiveRecordsToSave(final List<String> urls) {
        return urls.stream()
                .map(url -> new UrlArchiveDTO.Builder()
                        .url(url)
                        .type(ArchiveTypeEnum.VIDEO)
                        .topic(TopicEnum.COMMON)
                        .build())
                .toList();
    }

    /** Build a message for recommended movies and series channel. */
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
