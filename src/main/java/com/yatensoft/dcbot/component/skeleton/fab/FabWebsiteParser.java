/** By YamiY Yaten */
package com.yatensoft.dcbot.component.skeleton.fab;

import java.io.IOException;

/**
 * Class responsible for handling of parsing and processing of websites.
 */
public interface FabWebsiteParser {
    /**
     * Get latest article URL
     * @return url of the last article
     * @throws IOException if unable to parse website
     */
    String getLatestArticleUrl() throws IOException;
}
