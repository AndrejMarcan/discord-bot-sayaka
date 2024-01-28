/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl;

import com.yatensoft.dcbot.component.skeleton.ScheduledTask;
import com.yatensoft.dcbot.component.skeleton.WebsiteParser;
import com.yatensoft.dcbot.config.DiscordBotConfig;
import com.yatensoft.dcbot.constant.ChannelConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import java.io.IOException;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Implementation class of ScheduledTask interface responsible for handling of scheduled tasks
 * related to Flesh And Blood channels.
 */
@Service
public class FabScheduledTask implements ScheduledTask {
    final String lastArticle = "https://fabtcg.com/articles/calling-queenstown-recap/";
    private final WebsiteParser websiteParser;

    public FabScheduledTask(@Autowired FabWebsiteParser websiteParser) {
        super();
        this.websiteParser = websiteParser;
    }

    /** See {@link ScheduledTask#checkFabtcgArticles()} */
    @Override
    @Scheduled(cron = "0 0 */4 * * *")
    public void checkFabtcgArticles() throws IOException {
        /* Get the latest article URL */
        final String fetchedUrl = websiteParser.getLatestArticleUrl();
        /* Check if new article URL was fetched */
        if (!lastArticle.equalsIgnoreCase(fetchedUrl)) {
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
