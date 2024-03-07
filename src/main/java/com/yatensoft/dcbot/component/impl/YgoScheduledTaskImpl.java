/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl;

import com.yatensoft.dcbot.component.skeleton.WebsiteParser;
import com.yatensoft.dcbot.component.skeleton.YgoScheduledTask;
import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Implementation class of YgoScheduledTask interface responsible for handling of scheduled tasks
 * related to Yu-Gi-Oh! channels.
 */
@Component
public class YgoScheduledTaskImpl implements YgoScheduledTask {
    private final WebsiteParser websiteParser;
    private final UrlArchiveService urlArchiveService;

    public YgoScheduledTaskImpl(
            @Autowired final YgoWebsiteParser websiteParser, @Autowired final UrlArchiveService urlArchiveService) {
        super();
        this.websiteParser = websiteParser;
        this.urlArchiveService = urlArchiveService;
    }

    /** See {@link YgoScheduledTask#checkBanlist()} */
    @Override
    @Scheduled(cron = "0 0 */4 * * *")
    public void checkBanlist() throws IOException {
        /* Get the latest banlist URL */
        final String fetchedUrl = websiteParser.getLatestBanListUrl();
        /* Check if new banlist URL was fetched */
        if (!urlArchiveService.checkIfUrlArchiveRecordExists(fetchedUrl, TopicEnum.YUGIOH, ArchiveTypeEnum.BANLIST)) {
            /* Create new record in DB */
            urlArchiveService.createUrlArchiveRecord(createUrlArchiveRequest(fetchedUrl));
            /* Get news discord channel by ID */
            final TextChannel ygoNews =
                    DiscordBotConfig.getBotJDA().getTextChannelById(ChannelConstant.YGO_CHANNEL_NEWS);
            /* Send a message to the news channel */
            ygoNews.sendMessage(String.format(
                            MessageConstant.TWO_PARTS_MESSAGE_TEMPLATE,
                            MessageConstant.NEW_BANLIST_WAS_ADDED_YGO,
                            fetchedUrl))
                    .queue();
        }
    }

    /** Create UrlArchive object for Yu-Gi-Oh! banlist article */
    private UrlArchiveDTO createUrlArchiveRequest(final String url) {
        UrlArchiveDTO request = new UrlArchiveDTO();
        request.setUrl(url);
        request.setTopic(TopicEnum.YUGIOH);
        request.setType(ArchiveTypeEnum.BANLIST);
        request.setDateOfCreation(Date.from(Instant.now()));
        return request;
    }
}
