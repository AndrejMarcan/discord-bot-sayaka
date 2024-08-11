/** By YamiY Yaten */
package com.yatensoft.dcbot.plugin.ygo.component.impl;

import com.yatensoft.dcbot.core.config.SayakaConfig;
import com.yatensoft.dcbot.core.constant.MessageConstant;
import com.yatensoft.dcbot.core.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.core.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.core.enumeration.KitchenTableTCGsChannelEnum;
import com.yatensoft.dcbot.core.enumeration.SayakaManagedServerEnum;
import com.yatensoft.dcbot.core.enumeration.TopicEnum;
import com.yatensoft.dcbot.core.service.skeleton.DiscordService;
import com.yatensoft.dcbot.core.service.skeleton.UrlArchiveService;
import com.yatensoft.dcbot.plugin.ygo.component.skeleton.YgoScheduledTask;
import com.yatensoft.dcbot.plugin.ygo.component.skeleton.YgoWebsiteParser;
import java.io.IOException;
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
    private final DiscordService discordService;

    public YgoScheduledTaskImpl(
            @Autowired final YgoWebsiteParser websiteParser,
            @Autowired final UrlArchiveService urlArchiveService,
            @Autowired final DiscordService discordService) {
        super();
        this.websiteParser = websiteParser;
        this.urlArchiveService = urlArchiveService;
        this.discordService = discordService;
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
            final TextChannel ygoNews = SayakaConfig.getSayaka()
                    .getTextChannelById(discordService.getChannelIdByServerAndChannelKey(
                            SayakaManagedServerEnum.KITCHEN_TABLE_TCGS,
                            KitchenTableTCGsChannelEnum.YU_GI_OH_NEWS.getChannelKey()));
            /* Send a message to the news channel */
            ygoNews.sendMessage(String.format(
                            MessageConstant.TWO_PARTS_MESSAGE_TEMPLATE,
                            MessageConstant.NEW_BANLIST_WAS_ADDED_YGO,
                            fetchedUrl))
                    .queue();
        }
    }
}
