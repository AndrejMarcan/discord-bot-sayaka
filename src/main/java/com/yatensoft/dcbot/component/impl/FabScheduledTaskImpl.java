/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl;

import com.yatensoft.dcbot.component.skeleton.FabScheduledTask;
import com.yatensoft.dcbot.component.skeleton.WebsiteParser;
import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.persitence.entity.UrlArchive;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
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
    private final WebsiteParser websiteParser;
    private final UrlArchiveService urlArchiveService;

    public FabScheduledTaskImpl(
            @Autowired final FabWebsiteParser websiteParser, @Autowired final UrlArchiveService urlArchiveService) {
        super();
        this.websiteParser = websiteParser;
        this.urlArchiveService = urlArchiveService;
    }

    /** See {@link FabScheduledTask#checkLatestArticles()} */
    @Override
    @Scheduled(cron = "0 0 */4 * * *")
    public void checkLatestArticles() throws IOException {
        /* Get the latest article URL */
        final String fetchedUrl = websiteParser.getLatestArticleUrl();
        /* Check if new article URL was fetched */
        if (!urlArchiveService.checkIfUrlArchiveRecordExists(
                fetchedUrl, TopicEnum.FLESH_AND_BLOOD, ArchiveTypeEnum.ARTICLE)) {
            /* Create new record in DB */
            urlArchiveService.createUrlArchiveRecord(createUrlArchiveRequest(fetchedUrl));
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
    /** Create UrlArchive object for Flesh And Blood latest article */
    private UrlArchive createUrlArchiveRequest(final String url) {
        UrlArchive request = new UrlArchive();
        request.setUrl(url);
        request.setTopic(TopicEnum.FLESH_AND_BLOOD.getShortName());
        request.setType(ArchiveTypeEnum.ARTICLE.getValue());
        request.setDateOfCreation(Date.from(Instant.now()));
        return request;
    }
}
