/** By YamiY Yaten */
package com.yatensoft.dcbot.component.impl.ygo;

import com.yatensoft.dcbot.constant.WebsiteParserConstant;
import com.yatensoft.dcbot.component.skeleton.ygo.YgoWebsiteParser;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation class of WebsiteParser interface responsible for handling of parsing and processing operations
 * of yugioh-card website related to Yu-Gi-Oh! channels.
 */
@Component
public class YgoWebsiteParserImpl implements YgoWebsiteParser {
    @Value("${ygocard.url.base}")
    private String YGO_BASE_URL;

    @Value("${ygocard.url.banlist}")
    private String YGO_BANLIST_URL;

    /** See {@link YgoWebsiteParser#getLatestBanListUrl()} */
    @Override
    public String getLatestBanListUrl() throws IOException {
        try {
            /* Get document */
            final Document doc = Jsoup.connect(YGO_BANLIST_URL).get();
            /* Get all item elements */
            final Elements items = doc.getElementsByClass(WebsiteParserConstant.YGOCARD_BLOCK_BUTTON_CLASS);
            return YGO_BASE_URL
                    + items.stream()
                            .map(item -> item.attr(WebsiteParserConstant.HREF))
                            .findFirst()
                            .orElse(null);
        } catch (IOException ex) {
            throw ex;
        }
    }
}
