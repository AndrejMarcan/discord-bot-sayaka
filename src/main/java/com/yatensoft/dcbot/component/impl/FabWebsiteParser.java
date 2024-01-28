/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl;

import com.yatensoft.dcbot.component.skeleton.WebsiteParser;
import com.yatensoft.dcbot.constant.WebsiteParserConstant;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementation class of WebsiteParser interface responsible for handling of parsing and processing operations
 * of fabtcg website related to Flesh And Blood channels.
 */
@Service
public class FabWebsiteParser implements WebsiteParser {
    @Value("${fabtcg.url.articles}")
    private String FABTCG_ARTICLES_URL;

    /** See {@link WebsiteParser#getLatestArticleUrl()} */
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
}
