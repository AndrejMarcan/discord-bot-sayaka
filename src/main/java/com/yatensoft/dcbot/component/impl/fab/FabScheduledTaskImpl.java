/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl.fab;

import com.yatensoft.dcbot.component.skeleton.fab.FabScheduledTask;
import com.yatensoft.dcbot.component.skeleton.fab.FabWebsiteParser;
import com.yatensoft.dcbot.config.SayakaConfig;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.dto.fab.FabArticleDTO;
import com.yatensoft.dcbot.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.enumeration.KitchenTableTCGsChannelEnum;
import com.yatensoft.dcbot.enumeration.SayakaManagedServerEnum;
import com.yatensoft.dcbot.enumeration.TopicEnum;
import com.yatensoft.dcbot.service.skeleton.DiscordService;
import com.yatensoft.dcbot.service.skeleton.UrlArchiveService;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Implementation class of ScheduledTask interface responsible for handling of scheduled tasks
 * related to Flesh And Blood channels.
 */
@Component
public class FabScheduledTaskImpl implements FabScheduledTask {
    private final FabWebsiteParser fabWebsiteParser;
    private final UrlArchiveService urlArchiveService;
    private final DiscordService discordService;

    public FabScheduledTaskImpl(
            @Autowired final FabWebsiteParser fabWebsiteParser,
            @Autowired final UrlArchiveService urlArchiveService,
            @Autowired final DiscordService discordService) {
        super();
        this.fabWebsiteParser = fabWebsiteParser;
        this.urlArchiveService = urlArchiveService;
        this.discordService = discordService;
    }

    /** See {@link FabScheduledTask#checkLatestArticles()} */
    @Override
    @Scheduled(cron = "0 0 */4 * * *")
    public void checkLatestArticles() throws IOException {
        /* Get the latest articles */
        final List<FabArticleDTO> newLatestArticles = fabWebsiteParser.getLatestArticles().parallelStream()
                .filter(article -> !urlArchiveService.checkIfUrlArchiveRecordExists(
                        article.getUrl(), TopicEnum.FLESH_AND_BLOOD, ArchiveTypeEnum.ARTICLE))
                .toList();
        /* Check if new articles were fetched */
        if (!CollectionUtils.isEmpty(newLatestArticles)) {
            /* Sort and map articles url archive DTO*/
            final List<UrlArchiveDTO> newUrls = getUrlArchiveRecordsToSave(newLatestArticles);
            /* Create new records in DB */
            urlArchiveService.storeUrlArchiveRecords(newUrls);
            /* Post message to news channel */
            SayakaConfig.getSayaka()
                    .getTextChannelById(discordService.getChannelIdByServerAndChannelKey(
                            SayakaManagedServerEnum.KITCHEN_TABLE_TCGS,
                            KitchenTableTCGsChannelEnum.FLESH_AND_BLOOD_NEWS.getChannelKey()))
                    .sendMessage(createMessage(newLatestArticles, newUrls.size()))
                    .queue();
        }
    }

    /** Map articles to list of url archive objects. */
    private List<UrlArchiveDTO> getUrlArchiveRecordsToSave(final List<FabArticleDTO> articles) {
        return articles.stream()
                .sorted(Comparator.comparing(FabArticleDTO::getDate).reversed())
                .map(article -> new UrlArchiveDTO.Builder()
                        .url(article.getUrl())
                        .type(ArchiveTypeEnum.ARTICLE)
                        .topic(TopicEnum.FLESH_AND_BLOOD)
                        .build())
                .toList();
    }

    /** Build a message for news channel. */
    private String createMessage(final List<FabArticleDTO> articles, final int numberOfNewArticles) {
        StringBuilder stringbuilder = new StringBuilder(
                numberOfNewArticles == 1
                        ? MessageConstant.NEW_ARTICLE_WAS_ADDED_FAB
                        : String.format(MessageConstant.NEW_ARTICLES_WERE_ADDED_FAB, numberOfNewArticles));
        for (FabArticleDTO article : articles) {
            stringbuilder.append(article.getTitle() + ": " + article.getUrl());
            stringbuilder.append('\n');
        }
        return stringbuilder.toString();
    }
}
