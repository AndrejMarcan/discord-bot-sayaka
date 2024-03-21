/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl.fab;

import com.yatensoft.dcbot.component.skeleton.fab.FabWebsiteParser;
import com.yatensoft.dcbot.constant.WebsiteParserConstant;
import com.yatensoft.dcbot.dto.fab.FabArticleDTO;
import com.yatensoft.dcbot.dto.fab.FabLivingLegendElementDTO;
import com.yatensoft.dcbot.enumeration.fab.FabGameFormatEnum;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation class of WebsiteParser interface responsible for handling of parsing and processing operations
 * of fabtcg website related to Flesh And Blood channels.
 */
@Component
public class FabWebsiteParserImpl implements FabWebsiteParser {
    /** Common */
    private static final String REGEX_FABTCG_DATE_EXTRACTION =
            "\\b(\\d{1,2})(?:st|nd|rd|th)?\\s+(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+(\\d{4})\\b";

    private static final String DATE_STRING_TEMPLATE = "%s %s %s";

    @Value("${fabtcg.url.articles}")
    private String fabtcgArticlesUrl;

    @Value("${fabtcg.url.livingLegend}")
    private String fabtcgLivingLegendUrl;

    /** See {@link FabWebsiteParser#getLatestArticles()} */
    @Override
    public List<FabArticleDTO> getLatestArticles() throws IOException {
        try {
            /* Get document */
            final Document doc = Jsoup.connect(fabtcgArticlesUrl).get();
            /* Get pagesListBlock element by its ID */
            final Element pagesListBlock = doc.getElementById(WebsiteParserConstant.FABTCG_PAGES_LIST_BLOCK_ID);
            /* Get all item elements */
            final Elements items = pagesListBlock.getElementsByClass(WebsiteParserConstant.FABTCG_ITEM_LINK_CLASS);

            return items.stream().map(item -> parseHTMLToItem(item)).toList();

        } catch (IOException ex) {
            throw ex;
        }
    }

    /** See {@link FabWebsiteParser#getLivingLegendLeaderboards(FabGameFormatEnum)}*/
    @Override
    public Map<FabGameFormatEnum, List<FabLivingLegendElementDTO>> getLivingLegendLeaderboards(
            final FabGameFormatEnum format) throws IOException {
        /* Load page */
        final Document doc = Jsoup.connect(fabtcgLivingLegendUrl).get();
        Map<FabGameFormatEnum, List<FabLivingLegendElementDTO>> leaderboards = new HashMap<>();
        /* Load Living Legend leaderboards to map */
        switch (format) {
            case CLASSIC_CONSTRUCTED -> leaderboards.put(
                    FabGameFormatEnum.CLASSIC_CONSTRUCTED,
                    getLivingLegendLeaderboard(
                            doc,
                            WebsiteParserConstant.FABTCG_CC_LL_BOARD_SELECTOR,
                            WebsiteParserConstant.FABTCG_CC_LL_LEADERBOARD_SELECTOR));
            case BLITZ -> leaderboards.put(
                    FabGameFormatEnum.BLITZ,
                    getLivingLegendLeaderboard(
                            doc,
                            WebsiteParserConstant.FABTCG_BLITZ_LL_BOARD_SELECTOR,
                            WebsiteParserConstant.FABTCG_BLITZ_LL_LEADERBOARD_SELECTOR));
            default -> {
                leaderboards.put(
                        FabGameFormatEnum.CLASSIC_CONSTRUCTED,
                        getLivingLegendLeaderboard(
                                doc,
                                WebsiteParserConstant.FABTCG_CC_LL_BOARD_SELECTOR,
                                WebsiteParserConstant.FABTCG_CC_LL_LEADERBOARD_SELECTOR));
                leaderboards.put(
                        FabGameFormatEnum.BLITZ,
                        getLivingLegendLeaderboard(
                                doc,
                                WebsiteParserConstant.FABTCG_BLITZ_LL_BOARD_SELECTOR,
                                WebsiteParserConstant.FABTCG_BLITZ_LL_LEADERBOARD_SELECTOR));
            }
        }
        /* Return */
        return leaderboards;
    }

    /** Collect Living Legend Leaderboard data */
    private List<FabLivingLegendElementDTO> getLivingLegendLeaderboard(
            final Document doc, final String boardSelector, final String leaderboardSelector) {
        List<FabLivingLegendElementDTO> results = new ArrayList<>();
        /* Collect living legend heroes */
        results.addAll(mapTableData(doc.select(boardSelector).first()));
        /* Collect heroes not yet of living legend status */
        results.addAll(mapTableData(doc.select(leaderboardSelector).first()));
        return results;
    }

    /** Parse the HTML element to DTO */
    private FabArticleDTO parseHTMLToItem(final Element element) {
        final FabArticleDTO item = new FabArticleDTO();
        final Element linkElement = element.selectFirst(WebsiteParserConstant.FABTCG_URL_ELEMENT_CSS_QUERY);
        final Element titleElement = element.selectFirst(WebsiteParserConstant.FABTCG_TITLE_ELEMENT_CSS_QUERY);
        final Element dateElement = element.selectFirst(WebsiteParserConstant.FABTCG_DATE_ELEMENT_CSS_QUERY);

        if (linkElement != null) {
            item.setUrl(linkElement.attr(WebsiteParserConstant.HREF));
        }
        if (titleElement != null) {
            item.setTitle(titleElement.text());
        }
        if (dateElement != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(WebsiteParserConstant.FABTCG_DATE_FORMAT);
            try {
                item.setDate(dateFormat.parse(parseDateString(dateElement.text())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return item;
    }

    /** Extract date from string containing the date */
    private String parseDateString(final String input) {
        final Pattern pattern = Pattern.compile(REGEX_FABTCG_DATE_EXTRACTION);
        final Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            final int day = Integer.parseInt(matcher.group(1));
            final String monthString = matcher.group(2);
            final int year = Integer.parseInt(matcher.group(3));

            return String.format(DATE_STRING_TEMPLATE, day, monthString, year);
        } else {
            return null;
        }
    }

    /** Parse HTML Table element to FabLivingLegendElementDTO */
    private static List<FabLivingLegendElementDTO> mapTableData(final Element tableElement) {
        List<FabLivingLegendElementDTO> elementDTOs = new ArrayList<>();
        if (tableElement != null) {
            /* Get all rows and process them */
            for (Element row : tableElement.select(WebsiteParserConstant.FABTCG_TABLE_BODY)) {
                /* Map the element */
                Elements cells = row.select(WebsiteParserConstant.FABTCG_TD);
                /* All relevant tables should have 4 rows */
                if (cells.size() == 4) {
                    FabLivingLegendElementDTO element = new FabLivingLegendElementDTO();
                    element.setRank(cells.get(0).text());
                    element.setHero(cells.get(1).text());
                    /* Prevent empty string or null to get into Integer parsers */
                    if (!StringUtil.isBlank(cells.get(2).text())) {
                        element.setSeasonPoints(Integer.valueOf(cells.get(2).text()));
                    }
                    if (!StringUtil.isBlank(cells.get(3).text())) {
                        element.setLivingLegendPoints(
                                Integer.valueOf(cells.get(3).text()));
                    }
                    elementDTOs.add(element);
                }
            }
        }
        return elementDTOs;
    }
}
