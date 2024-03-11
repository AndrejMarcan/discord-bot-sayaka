/** By YamiY Yaten */
package com.yatensoft.dcbot.fab.component.impl;

import com.yatensoft.dcbot.constant.WebsiteParserConstant;
import com.yatensoft.dcbot.fab.component.skeleton.FabWebsiteParser;
import com.yatensoft.dcbot.fab.dto.FabPagesListBlockDTO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Value("${fabtcg.url.articles}")
    private String FABTCG_ARTICLES_URL;

    /** See {@link FabWebsiteParser#getLatestArticleUrl()} */
    @Override
    public String getLatestArticleUrl() throws IOException {
        try {
            /* Get document */
            final Document doc = Jsoup.connect(FABTCG_ARTICLES_URL).get();
            /* Get pagesListBlock element by its ID */
            final Element pagesListBlock = doc.getElementById(WebsiteParserConstant.FABTCG_PAGES_LIST_BLOCK_ID);
            /* Get all item elements */
            final Elements items = pagesListBlock.getElementsByClass(WebsiteParserConstant.FABTCG_ITEM_LINK_CLASS);
            return items.stream()
                    .map(item -> item.attr(WebsiteParserConstant.HREF))
                    .findFirst()
                    .orElse(null);
        } catch (IOException ex) {
            throw ex;
        }
    }

    /** See {@link FabWebsiteParser#getLatestBanListUrl()} */
    @Override
    public String getLatestBanListUrl() throws IOException {
        return null;
    }

    private FabPagesListBlockDTO parseHTMLToItem(Element element) {
        FabPagesListBlockDTO item = new FabPagesListBlockDTO();
        Element linkElement = element.selectFirst(WebsiteParserConstant.FABTCG_URL_ELEMENT_CSS_QUERY);
        Element titleElement = element.selectFirst(WebsiteParserConstant.FABTCG_TITLE_ELEMENT_CSS_QUERY);
        Element dateElement = element.selectFirst(WebsiteParserConstant.FABTCG_DATE_ELEMENT_CSS_QUERY);

        if (linkElement != null) {
            item.setLink(linkElement.attr(WebsiteParserConstant.HREF));
        }
        if (titleElement != null) {
            item.setTitle(titleElement.text());
        }
        if (dateElement != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            try {
                item.setDate(dateFormat.parse(dateElement.text()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return item;
    }
}
