/** By YamiY Yaten */
package com.yatensoft.dcbot.fab.component.impl;

import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.fab.component.skeleton.FabScheduledTask;
import com.yatensoft.dcbot.fab.component.skeleton.FabWebsiteParser;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import java.io.IOException;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Implementation class of ScheduledTask interface responsible for handling of scheduled tasks
 * related to Flesh And Blood channels.
 */
@Component
public class FabScheduledTaskImpl implements FabScheduledTask {
    private final FabWebsiteParser fabWebsiteParser;
    private final UrlArchiveService urlArchiveService;

    public FabScheduledTaskImpl(
            @Autowired final FabFabWebsiteParserImpl websiteParser,
            @Autowired final UrlArchiveService urlArchiveService) {
        super();
        this.fabWebsiteParser = websiteParser;
        this.urlArchiveService = urlArchiveService;
    }

    /** See {@link FabScheduledTask#checkLatestArticles()} */
    @Override
    @Scheduled(cron = "0 0 */4 * * *")
    public void checkLatestArticles() throws IOException {
        /* Get the latest article URL */
        final String fetchedUrl = fabWebsiteParser.getLatestArticleUrl();
        /* Check if new article URL was fetched */
        if (!urlArchiveService.checkIfUrlArchiveRecordExists(
                fetchedUrl, TopicEnum.FLESH_AND_BLOOD, ArchiveTypeEnum.ARTICLE)) {
            /* Create new record in DB */
            urlArchiveService.createUrlArchiveRecord(new UrlArchiveDTO.Builder()
                    .url(fetchedUrl)
                    .topic(TopicEnum.FLESH_AND_BLOOD)
                    .type(ArchiveTypeEnum.ARTICLE)
                    .build());
            /* Get news discord channel by ID */
            final TextChannel fabNews =
                    DiscordBotConfig.getBotJDA().getTextChannelById(ChannelConstant.FAB_CHANNEL_NEWS);
            /* Send a message to the news channel */
            fabNews.sendMessage(String.format(
                            MessageConstant.TWO_PARTS_MESSAGE_TEMPLATE,
                            MessageConstant.NEW_ARTICLE_WAS_ADDED_FAB,
                            fetchedUrl))
                    .queue();
        }
    }
}
