/** By YamiY Yaten */
package com.yatensoft.dcbot.service.skeleton;

import java.io.IOException;

/**
 * Class responsible for handling of parsing and processing of websites.
 */
public interface WebsiteParser {
    /**
     * Get latest article URL
     * @return url of the last article
     * @throws IOException if unable to parse website
     */
    public String getLatestArticleUrl() throws IOException;
}
