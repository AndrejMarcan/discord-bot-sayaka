/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl.ygo;

import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import com.yatensoft.dcbot.component.skeleton.ygo.YgoScheduledTask;
import java.io.IOException;

import com.yatensoft.dcbot.component.skeleton.ygo.YgoWebsiteParser;
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
    private final YgoWebsiteParser websiteParser;
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
            urlArchiveService.createUrlArchiveRecord(new UrlArchiveDTO.Builder()
                    .url(fetchedUrl)
                    .topic(TopicEnum.YUGIOH)
                    .type(ArchiveTypeEnum.BANLIST)
                    .build());
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
}
