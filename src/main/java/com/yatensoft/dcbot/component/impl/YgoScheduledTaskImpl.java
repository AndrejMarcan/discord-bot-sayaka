/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl;

import com.yatensoft.dcbot.component.skeleton.WebsiteParser;
import com.yatensoft.dcbot.component.skeleton.YgoScheduledTask;
import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
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
    final String latestBanlistUrl = "https://www.yugioh-card.com/en/limited/list_2024-01-01/";
    private final WebsiteParser websiteParser;

    public YgoScheduledTaskImpl(@Autowired final YgoWebsiteParser websiteParser) {
        super();
        this.websiteParser = websiteParser;
    }

    /** See {@link YgoScheduledTask#checkBanlist()} */
    @Override
    @Scheduled(cron = "0 0 */4 * * *")
    public void checkBanlist() throws IOException {
        /* Get the latest banlist URL */
        final String fetchedUrl = websiteParser.getLatestArticleUrl();
        /* Check if new banlist URL was fetched */
        if (!latestBanlistUrl.equalsIgnoreCase(fetchedUrl)) {
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
