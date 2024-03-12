/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl.fab;

import com.yatensoft.dcbot.component.skeleton.fab.FabWebsiteParser;
import com.yatensoft.dcbot.constant.WebsiteParserConstant;
import com.yatensoft.dcbot.dto.fab.FabPagesListBlockDTO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
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
public class FabFabWebsiteParserImpl implements FabWebsiteParser {

    /** fabtcg.com Constants */
    private static final String FABTCG_PAGES_LIST_BLOCK_ID = "pagesListblock";

    private static final String FABTCG_DATE_FORMAT = "dd MMM yyyy";
    private static final String FABTCG_ITEM_LINK_CLASS = "item-link";
    private static final String FABTCG_URL_ELEMENT_CSS_QUERY = "a.item-link";
    private static final String FABTCG_TITLE_ELEMENT_CSS_QUERY = ".item-card div.item-card-inner h5";
    private static final String FABTCG_DATE_ELEMENT_CSS_QUERY = ".item-card div.item-card-inner p";
    public static final String REGEX_FABTCG_DATE_EXTRACTION =
            "\\b(\\d{1,2})(?:st|nd|rd|th)?\\s+(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+(\\d{4})\\b";
    private static final String DATE_STRING_TEMPLATE = "%s %s %s";

    @Value("${fabtcg.url.articles}")
    private String FABTCG_ARTICLES_URL;

    /** See {@link FabWebsiteParser#getLatestArticlesSorted()} */
    @Override
    public List<FabPagesListBlockDTO> getLatestArticlesSorted() throws IOException {
        try {
            /* Get document */
            final Document doc = Jsoup.connect(FABTCG_ARTICLES_URL).get();
            /* Get pagesListBlock element by its ID */
            final Element pagesListBlock = doc.getElementById(FABTCG_PAGES_LIST_BLOCK_ID);
            /* Get all item elements */
            final Elements items = pagesListBlock.getElementsByClass(FABTCG_ITEM_LINK_CLASS);

            return items.stream()
                    .map(item -> parseHTMLToItem(item))
                    .sorted(Comparator.comparing(FabPagesListBlockDTO::getDate).reversed())
                    .toList();
        } catch (IOException ex) {
            throw ex;
        }
    }

    /** Parse the HTML element to DTO */
    private FabPagesListBlockDTO parseHTMLToItem(final Element element) {
        final FabPagesListBlockDTO item = new FabPagesListBlockDTO();
        final Element linkElement = element.selectFirst(FABTCG_URL_ELEMENT_CSS_QUERY);
        final Element titleElement = element.selectFirst(FABTCG_TITLE_ELEMENT_CSS_QUERY);
        final Element dateElement = element.selectFirst(FABTCG_DATE_ELEMENT_CSS_QUERY);

        if (linkElement != null) {
            item.setLink(linkElement.attr(WebsiteParserConstant.HREF));
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

    /** extract date from string containing the date */
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
}
