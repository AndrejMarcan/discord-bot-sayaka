/** By YamiY Yaten */
package com.yatensoft.dcbot.plugin.fab.component.impl;

import com.yatensoft.dcbot.core.constant.WebsiteParserConstant;
import com.yatensoft.dcbot.plugin.fab.component.skeleton.FabWebsiteParser;
import com.yatensoft.dcbot.plugin.fab.dto.FabArticleDTO;
import com.yatensoft.dcbot.plugin.fab.dto.FabLivingLegendElementDTO;
import com.yatensoft.dcbot.plugin.fab.enumeration.FabGameFormatEnum;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Value("${fabtcg.url.base}")
    private String fabtcgBaseUrl;

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
            item.setUrl(getFullUrlAddress(linkElement.attr(WebsiteParserConstant.HREF)));
        }
        if (titleElement != null) {
            item.setTitle(titleElement.text());
        }
        if (dateElement != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(WebsiteParserConstant.FABTCG_DATE_FORMAT);
            try {
                item.setDate(dateFormat.parse(dateElement.text()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return item;
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

    /**
     * Enrich the url path by base url in case of omitted base URL.
     *
     * @param url provided link
     * @return full URL with base and path parts
     */
    private String getFullUrlAddress(final String url) {
        if (StringUtil.isBlank(url)) {
            return this.fabtcgBaseUrl;
        }
        return url.startsWith(this.fabtcgBaseUrl) ? url : this.fabtcgBaseUrl + url;
    }
}
