/** By YamiY Yaten */
package com.yatensoft.dcbot.service.impl.fab;

import com.yatensoft.dcbot.component.impl.fab.FabWebsiteParserImpl;
import com.yatensoft.dcbot.constant.BotCommandConstant;
import com.yatensoft.dcbot.constant.MessageConstant;
import com.yatensoft.dcbot.dto.fab.FabArticleDTO;
import com.yatensoft.dcbot.dto.fab.FabLivingLegendElementDTO;
import com.yatensoft.dcbot.enumeration.fab.FabGameFormatEnum;
import com.yatensoft.dcbot.service.skeleton.fab.FabCommandService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Implementation class of CommandService interface responsible for handling of commands
 * related to Flesh And Blood channels.
 */
@Service
public class FabCommandServiceImpl implements FabCommandService {
    private final FabWebsiteParserImpl websiteParser;

    public FabCommandServiceImpl(@Autowired final FabWebsiteParserImpl websiteParser) {
        super();
        this.websiteParser = websiteParser;
    }

    // TODO pull article from DB and add posibility to get N number of articles
    /** See {@link FabCommandService#getLatestArticleURL(MessageReceivedEvent)} */
    @Override
    public void getLatestArticleURL(final MessageReceivedEvent event) throws IOException {
        /* Get the latest article URL */
        final List<FabArticleDTO> fetchedUrls = websiteParser.getLatestArticles().stream()
                .sorted(Comparator.comparing(FabArticleDTO::getDate).reversed())
                .toList();
        /* Send a message to the news channel */
        event.getChannel()
                .sendMessage(String.format(
                        MessageConstant.TWO_PARTS_MESSAGE_TEMPLATE,
                        MessageConstant.FAB_LATEST_ARTICLE_URL,
                        fetchedUrls.get(0).getUrl()))
                .queue();
    }

    /** See {@link FabCommandService#getLatestArticleURL(MessageReceivedEvent)} */
    @Override
    public void getLivingLegendData(final MessageReceivedEvent event) throws IOException {
        /* Get optional parameters if present */
        final FabGameFormatEnum gameFormat = getGameFormatParameter(event);
        final List<String> heroes = getHeroParameters(event);
        /* Get Living Legend Leaderboards */
        final Map<FabGameFormatEnum, List<FabLivingLegendElementDTO>> livingLegendLeaderboards =
                websiteParser.getLivingLegendLeaderboards(gameFormat);
        for (final FabGameFormatEnum gameFormatEnum : FabGameFormatEnum.values()) {
            /* Process list to table message and post it */
            generateDiscordMessageTable(livingLegendLeaderboards.get(gameFormatEnum), gameFormatEnum, heroes, event);
        }
    }

    /** Get game format optional command parameter */
    private FabGameFormatEnum getGameFormatParameter(final MessageReceivedEvent event) {
        final String[] substrings = {
            FabGameFormatEnum.BLITZ.getShortName(), FabGameFormatEnum.CLASSIC_CONSTRUCTED.getShortName()
        };
        final String message = event.getMessage().getContentRaw();

        for (String substring : substrings) {
            if (message.toLowerCase().contains(substring.toLowerCase())) {
                return FabGameFormatEnum.getFabGameFormatEnumByShortName(substring);
            }
        }

        return FabGameFormatEnum.UNDEFINED;
    }

    /** Get list of hero names from optional command parameter */
    private List<String> getHeroParameters(final MessageReceivedEvent event) {
        final String message = event.getMessage().getContentRaw();
        final int startIndex = message.indexOf(BotCommandConstant.COMMAND_LIST_PARAMETER_START_CHARACTER);
        final int endIndex = message.indexOf(BotCommandConstant.COMMAND_LIST_PARAMETER_END_CHARACTER, startIndex);
        List<String> listOfStrings = new ArrayList<>();

        if (startIndex != -1 && endIndex != -1) {
            final String listString = message.substring(startIndex, endIndex);
            if (listString.length() - 1 > 0) {
                String[] strings = listString
                        .substring(1, listString.length() - 1)
                        .split(BotCommandConstant.COMMAND_LIST_PARAMETER_VALUE_SPLIT_REGEX);
                for (String str : strings) {
                    listOfStrings.add(str.trim());
                }
            }
        }
        return listOfStrings;
    }

    /**
     * Generate and post message as table populated from list of living legend
     * @param livingLegendList list of values
     * @param event message event received
     */
    private void generateDiscordMessageTable(
            final List<FabLivingLegendElementDTO> livingLegendList,
            final FabGameFormatEnum gameFormat,
            final List<String> heroes,
            final MessageReceivedEvent event) {
        if (CollectionUtils.isEmpty(livingLegendList)) {
            return;
        }
        StringBuilder sb = new StringBuilder();

        /* Set up top of the table */
        sb.append(MessageConstant.CODE_BLOCK_START);
        sb.append(String.format(
                MessageConstant.LL_TABLE_ROW_TEMPLATE,
                MessageConstant.LL_TABLE_RANK_HEADER,
                MessageConstant.LL_TABLE_HERO_HEADER + " -- " + gameFormat.getFullName(),
                MessageConstant.LL_TABLE_SEASON_HEADER,
                MessageConstant.LL_TABLE_LIVING_LEGEND_HEADER));
        sb.append(MessageConstant.LL_TABLE_LINE);
        /* Process list of data */
        for (FabLivingLegendElementDTO livingLegendObject : livingLegendList) {
            /* If heroes are passed as optional parameter ignore all not requested */
            if (!CollectionUtils.isEmpty(heroes)) {
                if (isNotSearchedHero(livingLegendObject.getHero(), heroes)) {
                    continue;
                }
            }
            sb.append(String.format(
                    MessageConstant.LL_TABLE_ROW_TEMPLATE,
                    livingLegendObject.getRank(),
                    livingLegendObject.getHero(),
                    livingLegendObject.getSeasonPoints() == null ? Strings.EMPTY : livingLegendObject.getSeasonPoints(),
                    livingLegendObject.getLivingLegendPoints() == null
                            ? Strings.EMPTY
                            : livingLegendObject.getLivingLegendPoints()));
            /* Discord message can not be longer than 2000 characters. Split the message on 1900 to keep a buffer */
            if (sb.length() > MessageConstant.DISCORD_MESSAGE_MAX_LENGTH_WITH_BUFFER_100) {
                sb.append(MessageConstant.CODE_BLOCK_END);
                event.getChannel().sendMessage(sb.toString()).queue();
                sb = new StringBuilder(MessageConstant.CODE_BLOCK_START);
            }
        }
        /* Finalize table and send message */
        sb.append(MessageConstant.LL_TABLE_LINE);
        sb.append(MessageConstant.CODE_BLOCK_END);
        event.getChannel().sendMessage(sb.toString()).queue();
    }

    /** Check if the heroName is NOT in list of heroes */
    private boolean isNotSearchedHero(final String heroName, final List<String> heroes) {
        return heroes.stream().noneMatch(hero -> heroName.toLowerCase().contains(hero.toLowerCase()));
    }
}
