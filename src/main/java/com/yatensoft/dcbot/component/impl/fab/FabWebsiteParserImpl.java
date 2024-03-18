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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
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
    /** Articles Page */
    private static final String FABTCG_PAGES_LIST_BLOCK_ID = "pagesListblock";

    private static final String FABTCG_DATE_FORMAT = "dd MMM yyyy";
    private static final String FABTCG_ITEM_LINK_CLASS = "item-link";
    private static final String FABTCG_URL_ELEMENT_CSS_QUERY = "a.item-link";
    private static final String FABTCG_TITLE_ELEMENT_CSS_QUERY = ".item-card div.item-card-inner h5";
    private static final String FABTCG_DATE_ELEMENT_CSS_QUERY = ".item-card div.item-card-inner p";

    /** Living Legend Page */
    /* Already obtained Living Legend status in Classic Constructed */
    private static final String FABTCG_CC_LL_BOARD_SELECTOR =
            "body > article > div > div.container.page-content.py-3.px-0 > div:nth-child(5) > table";
    /* Living Legend status not yet obtained in Classic Constructed */
    private static final String FABTCG_CC_LL_LEADERBOARD_SELECTOR =
            "body > article > div > div.container.page-content.py-3.px-0 > div:nth-child(6) > table";
    /* Already obtained Living Legend status in Blitz */
    private static final String FABTCG_BLITZ_LL_BOARD_SELECTOR =
            "body > article > div > div.container.page-content.py-3.px-0 > div:nth-child(8) > table";
    /* Living Legend status not yet obtained in Blitz */
    private static final String FABTCG_CC_BLITZ_LEADERBOARD_SELECTOR =
            "body > article > div > div.container.page-content.py-3.px-0 > div:nth-child(9) > table";

    /** Common */
    private static final String REGEX_FABTCG_DATE_EXTRACTION =
            "\\b(\\d{1,2})(?:st|nd|rd|th)?\\s+(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+(\\d{4})\\b";

    private static final String DATE_STRING_TEMPLATE = "%s %s %s";

    @Value("${fabtcg.url.articles}")
    private String FABTCG_ARTICLES_URL;

    /** See {@link FabWebsiteParser#getLatestArticles()} */
    @Override
    public List<FabArticleDTO> getLatestArticles() throws IOException {
        try {
            /* Get document */
            final Document doc = Jsoup.connect(FABTCG_ARTICLES_URL).get();
            /* Get pagesListBlock element by its ID */
            final Element pagesListBlock = doc.getElementById(FABTCG_PAGES_LIST_BLOCK_ID);
            /* Get all item elements */
            final Elements items = pagesListBlock.getElementsByClass(FABTCG_ITEM_LINK_CLASS);

            return items.stream().map(item -> parseHTMLToItem(item)).toList();

        } catch (IOException ex) {
            throw ex;
        }
    }

    /** See {@link FabWebsiteParser#getLivingLegendLeaderboard(FabGameFormatEnum)}*/
    @Override
    public List<FabLivingLegendElementDTO> getLivingLegendLeaderboard(final FabGameFormatEnum format)
            throws IOException {
        return null;
    }

    /** Parse the HTML element to DTO */
    private FabArticleDTO parseHTMLToItem(final Element element) {
        final FabArticleDTO item = new FabArticleDTO();
        final Element linkElement = element.selectFirst(FABTCG_URL_ELEMENT_CSS_QUERY);
        final Element titleElement = element.selectFirst(FABTCG_TITLE_ELEMENT_CSS_QUERY);
        final Element dateElement = element.selectFirst(FABTCG_DATE_ELEMENT_CSS_QUERY);

        if (linkElement != null) {
            item.setUrl(linkElement.attr(WebsiteParserConstant.HREF));
        }
        if (titleElement != null) {
            item.setTitle(titleElement.text());
        }
        if (dateElement != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FABTCG_DATE_FORMAT);
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
    private static List<FabLivingLegendElementDTO> mapTableData(Element tableElement) {
        List<FabLivingLegendElementDTO> dataList = new ArrayList<>();
        if (tableElement != null) {
            /* Get all rows */
            Elements rows = tableElement.select("tbody tr");
            for (Element row : rows) {
                /* Map the element */
                Elements cells = row.select("td");
                if (cells.size() == 4) {
                    FabLivingLegendElementDTO element = new FabLivingLegendElementDTO();
                    element.setRank(cells.get(0).text());
                    element.setHero(cells.get(1).text());
                    element.setSeasonPoints(Integer.parseInt(cells.get(2).text()));
                    element.setLivingLegendPoints(Integer.parseInt(cells.get(3).text()));
                    dataList.add(element);
                }
            }
        }
        return dataList;
    }
}
